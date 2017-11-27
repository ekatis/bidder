package com.example.bidder.bid;

/**
 * Created by ekatis on 11/25/17.
 */
public class Bid {

    private String campaignId;

    private double price;

    private String adm;

    public Bid() {
    }

    public Bid(String campaignId, double price, String adm) {
        this.campaignId = campaignId;
        this.price = price;
        this.adm = adm;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

}
