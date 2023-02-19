package com.tunahan.cinemateer.model;

import com.tunahan.cinemateer.dao.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class MovieManagement {

   public static void main(String[] args) throws SQLException, IOException {

       try(Connection c = new ConnectionManager("src/main/java/com/tunahan/cinemateer/mw.properties").getCon()){
           MovieDAO mDAO = new MovieDAO(c);
           WatchlistDAO wDAO = new WatchlistDAO(c);


           //Movies erstellen und inserten
           Movie m1 = new Movie("Fast and Furious 9", "Justin Lin", "Lin GmbH", 2021, "90 min", "Cast 1","E:\\4BHIT\\SEW\\moview_JavaFX\\src\\img\\faf9.jpg");
           Movie m2 = new Movie("Scary Movie 1", "Emir Meftar", "Meftar GmbH", 2022, "120 min", "Cast 2","E:\\4BHIT\\SEW\\moview_JavaFX\\src\\img\\sm_poster.jpg");


           mDAO.insertMovie(m1);
           mDAO.insertMovie(m2);

           //Watchlist erstellen und inserten
           Watchlist w1 = new Watchlist("Playlist 1","Action","Coole Filme");

           wDAO.insertWatchlist(w1);


           //Movies zu Watchlist hinzufügen
           w1.getMovies().put(wDAO.getWIDMethod(w1),m1);
           w1.getMovies().put(wDAO.getWIDMethod(w1),m2);

          wDAO.addMovieToWatchlist(m1,w1);
          wDAO.addMovieToWatchlist(m2,w1);
       }
   }
}