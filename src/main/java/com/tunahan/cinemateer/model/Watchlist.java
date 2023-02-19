package com.tunahan.cinemateer.model;

import java.util.Map;
import java.util.TreeMap;

public class Watchlist {

    private String playlistTitle;
    private String genre;
    private String description;
    private Map<Integer,Movie> movies;

    public Watchlist(String playlistTitle, String genre, String description) {
        this.playlistTitle = playlistTitle;
        this.genre = genre;
        this.description = description;
        this.movies = new TreeMap<>();
    }


    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Integer, Movie> getMovies() {
        return movies;
    }

    public void setMovies(Map<Integer, Movie> movies) {
        this.movies = movies;
    }
}

