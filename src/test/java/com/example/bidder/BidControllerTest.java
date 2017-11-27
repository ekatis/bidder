package com.example.bidder;

import com.example.bidder.bid.*;
import com.example.bidder.campaign.Campaign;
import com.example.bidder.campaign.CampaignNotFoundException;
import com.example.bidder.campaign.CampaignPicker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ekatis on 11/27/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BidController.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CampaignPicker campaignPicker;

    @Test
    public void askForBidWhenCampaignFoundShouldReturnBidResponse() throws Exception {
        given(this.campaignPicker.pickFor(anyString())).willReturn(createCampaign());

        this.mvc.perform(post("/bid").content(bidRequestAsJson()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("e7fe51ce4f6376876353ff0961c2cb0d")))
                .andExpect(jsonPath("$.bid.campaignId", is("5a3dce46")))
                .andExpect(jsonPath("$.bid.price", is(1.23)));
    }

    @Test
    public void askForBidWhenCampaignNotFoundShouldReturnNoContent() throws Exception {
        given(this.campaignPicker.pickFor(anyString())).willThrow(CampaignNotFoundException.class);

        this.mvc.perform(post("/bid").content(bidRequestAsJson()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Campaign createCampaign() {
        return new Campaign("5a3dce46", "Test Campaign 1", 1.23,
                "<a href=\\\"http://example.com/click/qbFCjzXR9rkf8qa4\\\"><img src=\\\"http://assets.example.com/ad_assets/files/000/000/002/original/banner_300_250.png\\\" height=\\\"250\\\" width=\\\"300\\\" alt=\\\"\\\"/></a><img src=\\\"http://example.com/win/qbFCjzXR9rkf8qa4\\\" height=\\\"1\\\" width=\\\"1\\\" alt=\\\"\\\"/>\\r\\n",
                Arrays.asList("USA", "GBR", "GRC"));
    }

    private String bidRequestAsJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this.createBidRequest());
    }

    private BidRequest createBidRequest() {
        App app = new App("e7fe51ce-4f63-7687-6353-ff0961c2cb0d", "Morecast Weather");

        Geo geo = new Geo("USA", 0, 0);
        Device device = new Device("Android", geo);

        return new BidRequest("e7fe51ce4f6376876353ff0961c2cb0d", app, device);
    }
}
