package com.example.bidder.bid;

/**
 * Created by ekatis on 11/25/17.
 */
public class App {

    private String id;

    private String name;

    public App() {
    }

    public App(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
