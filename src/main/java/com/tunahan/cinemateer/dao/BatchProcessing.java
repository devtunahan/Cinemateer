package com.tunahan.cinemateer.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessing {
    public static void main(String[] args) throws SQLException, IOException {
        String createMovieStatement = "CREATE TABLE IF NOT EXISTS  movie (mID INT IDENTITY PRIMARY KEY, title VARCHAR(30), director VARCHAR(30), company VARCHAR(20), year INT, runningTime VARCHAR(10),movieCast VARCHAR(20), image BLOB);";
        String createWatchlistStatement = "CREATE TABLE IF NOT EXISTS  watchlist (wID INT IDENTITY PRIMARY KEY ,playlistTitle VARCHAR(20), genre VARCHAR(20),description VARCHAR(20));";
        String createRelatMovieWatchlistStatement="CREATE TABLE IF NOT EXISTS movie_watchlist (mID INT, wID INT, CONSTRAINT FK_movie FOREIGN KEY (mID) REFERENCES movie (mID), CONSTRAINT FK_watchlist FOREIGN KEY (wID) REFERENCES watchlist (wID));";

        Connection c = new ConnectionManager("src/main/java/com/tunahan/cinemateer/mw.properties").getCon();
        boolean canBatch = c.getMetaData().supportsBatchUpdates();

        try (Statement createTransaction = c.createStatement();
        ) {
            if (canBatch) {
                createTransaction.clearBatch();
            }

            if (canBatch) {
                createTransaction.addBatch(createMovieStatement);
                createTransaction.addBatch(createWatchlistStatement);
                createTransaction.addBatch(createRelatMovieWatchlistStatement);
            }

            createTransaction.executeBatch();
            c.commit();


        } catch (Exception ex) {
            c.rollback();
            throw ex;
        } finally {
            c.close();
        }

    }
}