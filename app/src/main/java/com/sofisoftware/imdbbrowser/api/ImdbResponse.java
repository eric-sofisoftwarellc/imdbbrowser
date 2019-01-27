package com.sofisoftware.imdbbrowser.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Response from http://www.omdbapi.com/?s=<title>
 */
public class ImdbResponse {
    @SerializedName("Search")
    private ArrayList<ImdbEntry> entries;

    @SerializedName("Response")
    private String response;

    public ArrayList<ImdbEntry> getEntries() {
        return entries;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
