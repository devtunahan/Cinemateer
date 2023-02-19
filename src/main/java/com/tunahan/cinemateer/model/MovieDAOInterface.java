package com.tunahan.cinemateer.model;


import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface MovieDAOInterface {

    int insertMovie(Movie m) throws SQLException, FileNotFoundException;

}
