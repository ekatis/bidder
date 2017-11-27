package com.example.bidder.bid;

import com.example.bidder.campaign.Campaign;
import com.example.bidder.campaign.CampaignNotFoundException;
import com.example.bidder.campaign.CampaignPicker;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ekatis on 11/24/17.
 */
@RestController
public class BidController {

    private final CampaignPicker campaignPicker;

    public BidController(CampaignPicker campaignPicker) {
        this.campaignPicker = campaignPicker;
    }

    @PostMapping(value = "bid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BidResponse> askForBid(@RequestBody BidRequest bidRequest) {

        String country = bidRequest.getDevice().getGeo().getCountry();

        Campaign campaign = this.campaignPicker.pickFor(country);

        BidResponse bid = new BidResponse(bidRequest.getId(),
                                          campaign.getId(),
                                          campaign.getPrice(),
                                          campaign.getAdm());

        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(CampaignNotFoundException.class)
    public void handleException() {
    }
}
