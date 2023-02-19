package com.tunahan.cinemateer.model;

public class Movie {

    private String title;
    private String director;
    private String company;
    private int year;
    private String runningTime;
    private String movieCast;
    private String titleImage;

    public Movie(String title, String director, String company, int year, String runningTime, String movieCast,String titleImage) {

        this.title = title;
        this.director = director;
        this.company = company;
        this.year = year;
        this.runningTime = runningTime;
        this.movieCast = movieCast;
        this.titleImage = titleImage;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(String movieCast) {
        this.movieCast = movieCast;
    }



}
