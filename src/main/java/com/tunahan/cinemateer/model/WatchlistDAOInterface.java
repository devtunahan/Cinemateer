package com.tunahan.cinemateer.model;

import java.sql.SQLException;
import java.util.List;

public interface WatchlistDAOInterface
{

    int insertWatchlist(Watchlist w) throws SQLException;
    int addMovieToWatchlist(Movie m, Watchlist wl) throws SQLException;
    Watchlist getWatchlist(String watchlistName) throws SQLException;
    List<Watchlist> getAllWatchlists();
    boolean removeMovieFromWatchlist(Movie m,Watchlist w) throws SQLException;
}
