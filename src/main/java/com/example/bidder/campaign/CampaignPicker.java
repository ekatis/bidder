package com.example.bidder.campaign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Created by ekatis on 11/25/17.
 */
@Service
public class CampaignPicker {

    private final Integer pacingLimitBids;

    private final RemoteCampaignRepository remoteCampaignRepository;

    private final CampaignBids campaignBids;

    public CampaignPicker(@Value("${pacing.limit.bids}") Integer pacingLimitBids, RemoteCampaignRepository remoteCampaignRepository, CampaignBids campaignBids) {
        this.pacingLimitBids = pacingLimitBids;
        this.remoteCampaignRepository = remoteCampaignRepository;
        this.campaignBids = campaignBids;
    }

    public Campaign pickFor(String country) throws CampaignNotFoundException {

        List<Campaign> availableCampaigns = this.remoteCampaignRepository.findAll();

        Optional<Campaign> campaign = availableCampaigns.stream()
                .filter(c -> c.getTargetedCountries().contains(country))
                .sorted((c1, c2) -> Double.compare(c2.getPrice(), c1.getPrice()))
                .filter(c -> this.campaignBids.getBids(c.getId()) < pacingLimitBids)
                .findFirst();

        campaign.ifPresent((c) -> this.campaignBids.increment(c.getId()));

        return campaign
                .orElseThrow(() -> new CampaignNotFoundException());

    }

}
