package com.example.bidder.campaign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by ekatis on 11/25/17.
 */
@Component
public class RemoteCampaignRepository {

    @Value("${campaigns.api.url}")
    private String campaignsApiUrl;

    public List<Campaign> findAll() {

        RestTemplate restTemplate = new RestTemplate();

        ParameterizedTypeReference<List<Campaign>> campaignsListType =
                new ParameterizedTypeReference<List<Campaign>>() {};

        ResponseEntity<List<Campaign>> campaignsResponse =
                restTemplate.exchange(campaignsApiUrl , HttpMethod.GET, null, campaignsListType);

        return campaignsResponse.getBody();
    }

}
