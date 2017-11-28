package com.example.bidder.campaign;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ekatis on 11/26/17.
 */
@Component
public class CampaignBids {

    private ConcurrentMap<String, Integer> bidsPerCampaign = new ConcurrentHashMap<>();

    @Scheduled(fixedRateString = "${pacing.limit.in.milliseconds}")
    public void resetBidsPerCampaign() {
        bidsPerCampaign = new ConcurrentHashMap<>();
    }

    public Integer getBids(String campaignId) {
        return bidsPerCampaign.containsKey(campaignId) ? bidsPerCampaign.get(campaignId) : 0;
    }

    public void increment(String campaignId) {
        Integer bids = this.getBids(campaignId);
        bidsPerCampaign.put(campaignId, bids + 1);
    }
}
