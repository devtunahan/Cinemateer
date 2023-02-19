package com.tunahan.cinemateer.dao;


import com.tunahan.cinemateer.model.Movie;
import com.tunahan.cinemateer.model.MovieDAOInterface;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieDAO implements MovieDAOInterface, AutoCloseable{


    private Connection c;
    private PreparedStatement psInsertMovie;
    private PreparedStatement psGetImageFromMovie;
    private boolean batch;
    private InputStream photoStream = null;


    public MovieDAO(Connection c) throws SQLException {
        psInsertMovie = c.prepareStatement("INSERT INTO MOVIE(TITLE,DIRECTOR,COMPANY,YEAR,RUNNINGTIME,MOVIECAST,IMAGE) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        this.c = c;
        batch = c.getMetaData().supportsBatchUpdates();
    }

    public int insertMovie(Movie m) throws SQLException, FileNotFoundException {
        int returnCode = 0;

        try {
            photoStream = new BufferedInputStream(new FileInputStream(m.getTitleImage()));
            c.setAutoCommit(false);

            if(m != null) {
                psInsertMovie.setString(1, m.getTitle());
                psInsertMovie.setString(2, m.getDirector());
                psInsertMovie.setString(3, m.getCompany());
                psInsertMovie.setInt(4, m.getYear());
                psInsertMovie.setString(5, m.getRunningTime());
                psInsertMovie.setString(6, m.getMovieCast());
                psInsertMovie.setBlob(7,photoStream);

                returnCode = 0;
            }
            else {
                System.out.println("Der Film "+m.getTitle()+" existiert bereits!");
                returnCode = 1;
            }

            if(batch) {
                psInsertMovie.addBatch();
            }
            else {
                psInsertMovie.executeUpdate();
            }

            if(batch) {
                psInsertMovie.executeBatch();
            }
            c.commit();

        } catch (SQLException e) {
            c.rollback();
        }
        finally {
            c.setAutoCommit(true);
            returnCode = 2;
        }
      return returnCode;
    }

    @Override
    public void close() throws Exception {
        psInsertMovie.close();
        c.close();
        photoStream.close();
    }


}