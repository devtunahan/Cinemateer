package com.tunahan.cinemateer.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager implements AutoCloseable {

    private Connection con;

    public ConnectionManager(String propertiesPath) throws IOException, SQLException {
        Properties p = new Properties();

        try(FileInputStream fis = new FileInputStream(propertiesPath)) {
            p.load(fis);
        }
        con = DriverManager.getConnection(p.getProperty("url"),p.getProperty("user"),p.getProperty("password"));
    }

    public Connection getCon() {
        return con;
    }

    @Override
    public void close() throws Exception {
        con.close();
    }
}
