package com.example.bidder.bid;

/**
 * Created by ekatis on 11/25/17.
 */
public class Device {

    private String os;

    private Geo geo;

    public Device() {
    }

    public Device(String os, Geo geo) {
        this.os = os;
        this.geo = geo;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
