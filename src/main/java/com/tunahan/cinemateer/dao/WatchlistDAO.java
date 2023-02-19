package com.tunahan.cinemateer.dao;

import com.tunahan.cinemateer.model.Movie;
import com.tunahan.cinemateer.model.Watchlist;
import com.tunahan.cinemateer.model.WatchlistDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WatchlistDAO implements WatchlistDAOInterface, AutoCloseable {

    private PreparedStatement psInsertWatchlist;
    private PreparedStatement psInsertMovieToWatchlist;
    private PreparedStatement psGetWatchlist;
    private PreparedStatement psRemoveMovie;
    private PreparedStatement psGetMoviesFromWatchlist;
    private PreparedStatement selectAllWatchlists;
    private boolean batch;


    private Connection c;
    private MovieDAO movieDAO;

    public WatchlistDAO(Connection c) throws SQLException {
        this.c = c;
        movieDAO = new MovieDAO(this.c);
        psInsertWatchlist = c.prepareStatement("INSERT INTO WATCHLIST(playlisttitle,genre,description) VALUES(?,?,?)");
        psInsertMovieToWatchlist = c.prepareStatement("INSERT INTO MOVIE_WATCHLIST(MID,WID) VALUES (?,?)");
        psRemoveMovie = c.prepareStatement("DELETE FROM MOVIE_WATCHLIST WHERE MID = ? AND WID = ?");
        psGetMoviesFromWatchlist = c.prepareStatement("SELECT MID FROM MOVIE_WATCHLIST WHERE WID = ? ");
        psGetWatchlist = c.prepareStatement("SELECT * FROM WATCHLIST WHERE PLAYLISTTITLE = ?");
        selectAllWatchlists = c.prepareStatement("SELECT * FROM WATCHLIST");
        batch = c.getMetaData().supportsBatchUpdates();
    }

    public int insertWatchlist(Watchlist w) throws SQLException {

        int returnCode = 0;

        try {
            c.setAutoCommit(false);

            if (w != null) {
                psInsertWatchlist.setString(1, w.getPlaylistTitle());
                psInsertWatchlist.setString(2, w.getGenre());
                psInsertWatchlist.setString(3, w.getDescription());
                returnCode = 0;
            } else {
                System.out.println("Watchlist " + w.getPlaylistTitle() + "existiert bereits!");
                returnCode = 1;
            }

            if (batch) {
                psInsertWatchlist.addBatch();
            } else {
                psInsertWatchlist.executeUpdate();
            }

            if (batch) {
                psInsertWatchlist.executeBatch();
            }
            c.commit();
        } catch (SQLException e) {
            c.rollback();
            returnCode = 2;
        } finally {
            c.setAutoCommit(true);
        }
        return returnCode;
    }

    public int addMovieToWatchlist(Movie m, Watchlist wl) throws SQLException {

        try {

            c.setAutoCommit(false);

            psInsertMovieToWatchlist.setInt(1, getMIDMethod(m));
            psInsertMovieToWatchlist.setInt(2, getWIDMethod(wl));

            if (batch) {
                psInsertMovieToWatchlist.addBatch();
            } else {
                psInsertMovieToWatchlist.executeUpdate();
            }

            if (batch) {
                psInsertMovieToWatchlist.executeBatch();
            }
            c.commit();
        } catch (SQLException e) {
            c.rollback();
        } finally {
            c.setAutoCommit(true);
        }
        return 0;
    }

    @Override
    public List<Watchlist> getAllWatchlists() {
        List<Watchlist> watchlists = new ArrayList<>();

        try {
            ResultSet rs = selectAllWatchlists.executeQuery();

            while (rs.next()) {
                Watchlist wl = new Watchlist(rs.getString(2), rs.getString(3), rs.getString(4));
                psGetMoviesFromWatchlist.setInt(1, getWIDMethod(wl));
                watchlists.add(wl);

                ResultSet rs2 = psGetMoviesFromWatchlist.executeQuery();

                while (rs2.next()) {
                    Movie movie = new Movie(rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getInt(5), rs2.getString(6), rs2.getString(7), rs2.getString(8));
                    wl.getMovies().put(getWIDMethod(wl), movie);
                }
            }
            c.commit();
        } catch (SQLException throwables) {
            try {
                c.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return watchlists;
    }

    @Override
    public boolean removeMovieFromWatchlist(Movie m, Watchlist w) throws SQLException {

        boolean isSuccessful = true;

        try {
            c.setAutoCommit(false);

            psRemoveMovie.setInt(1, getMIDMethod(m));
            psRemoveMovie.setInt(2, getWIDMethod(w));

            if (batch) {
                psRemoveMovie.addBatch();
            } else {
                psRemoveMovie.executeUpdate();
            }

            if (batch) {
                psRemoveMovie.executeBatch();
            }
            c.commit();

        } catch (SQLException throwables) {
            c.rollback();
            isSuccessful = false;
        }
        return isSuccessful;
    }

    public int getMIDMethod(Movie m) throws SQLException {
        int mID = 0;
        Statement selectMovie = c.createStatement();
        ResultSet rs = selectMovie.executeQuery("SELECT MID FROM Movie WHERE title ='" + m.getTitle() + "';");

        while (rs.next()) {
            mID = rs.getInt(1);
        }
        return mID;
    }

    public int getWIDMethod(Watchlist wl) throws SQLException {
        int wID = 0;
        Statement selectWatchlist = c.createStatement();
        ResultSet rs = selectWatchlist.executeQuery("SELECT WID FROM Watchlist WHERE playlisttitle='" + wl.getPlaylistTitle() + "';");

        while (rs.next()) {
            wID = rs.getInt(1);
        }
        return wID;
    }

    public Watchlist getWatchlist(String watchlistName) throws SQLException {
        Watchlist w = null;

        if (watchlistName != null) {
            psGetWatchlist.setString(1, watchlistName);
            ResultSet rs = psGetWatchlist.executeQuery();

            if (rs.next()) {
                w = new Watchlist(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        }
        return w;
    }

    @Override
    public void close() throws Exception {
        psInsertWatchlist.close();
        psInsertMovieToWatchlist.close();
        psGetWatchlist.close();
        psRemoveMovie.close();
        psGetMoviesFromWatchlist.close();
        selectAllWatchlists.close();
        movieDAO.close();
    }
}
