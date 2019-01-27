package com.sofisoftware.imdbbrowser.api;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Single entry in list of responses
 */
public class ImdbEntry {
    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPoster() {
        return poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImdbEntry that = (ImdbEntry) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(year, that.year) &&
                Objects.equals(poster, that.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, poster);
    }
}
