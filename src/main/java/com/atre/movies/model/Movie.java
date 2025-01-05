package com.atre.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Movie implements Comparable<Movie> {
    @SerializedName("Title")
    private final String title;
    @SerializedName("Year")
    private final String year;
    @SerializedName("Released")
    private final String releaseDate;
    @SerializedName("Genre")
    private final String genre;
    @SerializedName("Actors")
    private final String actors;
    @SerializedName("Plot")
    private final String plot;
    @SerializedName("Poster")
    private final String posterUrl;
    private final String rtRating;
    @SerializedName("imdbID")
    private final String imdbId;

    public Movie(String title, String year, String releaseDate, String genre, String actors, String plot,
                 String posterUrl, String imdbId, String rtRating) {
        this.title = title;
        this.year = year;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        this.posterUrl = posterUrl;
        this.imdbId = imdbId;
        this.rtRating = rtRating;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getRtRating() {
        return rtRating;
    }

    @Override
    public String toString() {
        return String.format("%s (%s), %s", title, year, rtRating);
    }

    @Override
    public int compareTo(Movie other) {
        return Comparator.comparing(Movie::getYear).reversed()
                .thenComparing(Movie::getTitle)
                .compare(this, other);
    }
}
