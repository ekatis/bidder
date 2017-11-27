package com.example.bidder;

import com.example.bidder.campaign.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Created by ekatis on 11/27/17.
 */
public class CampaignPickerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private CampaignBids campaignBids;

    @Mock
    private RemoteCampaignRepository remoteCampaignRepository;

    private CampaignPicker campaignPicker;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        this.campaignPicker = new CampaignPicker(1, this.remoteCampaignRepository,
                this.campaignBids);
        given(this.remoteCampaignRepository.findAll()).willReturn(getCampaignsAsList());
    }

    @Test
    public void pickForWhenCountryIsCypShouldThrowException() {
        given(this.campaignBids.getBids(anyString())).willReturn(0);
        this.thrown.expect(CampaignNotFoundException.class);
        this.campaignPicker.pickFor("CYP");
    }

    @Test
    public void pickForWhenCountryIsMexShouldReturnHighestPrice() {
        given(this.campaignBids.getBids(anyString())).willReturn(0);
        Campaign actual = this.campaignPicker.pickFor("MEX");
        assertThat(actual.getPrice()).isEqualTo(2.21);
    }

    @Test
    public void pickForWhenCountryIsMexAndBidsGreaterThanZeroShouldThrowException() {
        given(this.campaignBids.getBids(anyString())).willReturn(1);
        this.thrown.expect(CampaignNotFoundException.class);
        this.campaignPicker.pickFor("MEX");
    }

    @Test
    public void pickForWhenCountryIsUsaAndBidsGreaterThanZeroShouldFilterOutHighest() {
        given(this.campaignBids.getBids("5a3dce46")).willReturn(1);
        Campaign actual = this.campaignPicker.pickFor("USA");
        assertThat(actual.getPrice()).isEqualTo(0.39);
    }

    private List<Campaign> getCampaignsAsList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Campaign> campaigns =  mapper.readValue(new ClassPathResource("__files/campaigns.json").getFile(), new TypeReference<List<Campaign>>() {});
        return campaigns;
    }

}
