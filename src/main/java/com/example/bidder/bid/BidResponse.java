package com.example.bidder.bid;

/**
 * Created by ekatis on 11/24/17.
 */
public class BidResponse {

    private String id;

    private Bid bid;

    public BidResponse() {
    }

    public BidResponse(String id, String campaignId, double campaignPrice, String campaignAdm) {
        this.id = id;
        this.bid = new Bid(campaignId, campaignPrice, campaignAdm);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

}
