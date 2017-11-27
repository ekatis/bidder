package com.example.bidder;

import com.example.bidder.bid.*;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @Before
    public void setup() throws IOException {
        stubFor(get(urlEqualTo("/campaigns"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("campaigns.json")));
    }

    @Test
    public void askForBidWhenCountryIsUsaShouldReturnBid() throws IOException {

        ResponseEntity<BidResponse> response = this.restTemplate
                .postForEntity("/bid", this.createBidRequestFor("USA"), BidResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBid().getCampaignId()).isEqualTo("5a3dce46");
        assertThat(response.getBody().getBid().getPrice()).isEqualTo(1.23);
    }

    @Test
    public void askForBidWhenCountryIsCypShouldReturnNoContent() throws IOException {

        ResponseEntity<BidResponse> response = this.restTemplate
                .postForEntity("/bid", this.createBidRequestFor("CYP"), BidResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void askForBidWhenCampaignHasReachedPacingLimitShouldFilterOut() throws IOException {

        ResponseEntity<BidResponse> response = this.restTemplate
                .postForEntity("/bid", this.createBidRequestFor("USA"), BidResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBid().getCampaignId()).isEqualTo("e919799e");
        assertThat(response.getBody().getBid().getPrice()).isEqualTo(0.39);
    }

    private BidRequest createBidRequestFor(String countryCode) {
        App app = new App("e7fe51ce-4f63-7687-6353-ff0961c2cb0d", "Morecast Weather");

        Geo geo = new Geo(countryCode, 0, 0);
        Device device = new Device("Android", geo);

        return new BidRequest("e7fe51ce4f6376876353ff0961c2cb0d", app, device);
    }


}
