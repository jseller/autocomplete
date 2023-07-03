package com.coveo.challenge.search;

public class SearcherParams {
    
    public SearcherParams(String q, Double latitude, Double longitude, Integer page) {
        this.q = q;
        this.latitude = latitude;
        this.longitude = longitude;
        this.page = page;
    }

    public String q;

    public Double latitude;

    public Double longitude;

    public Integer page;

}