package com.example.vrkapireader.repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestRepository {

    @Value("${VRK_URL}")
    private String url;

    public JsonNode getInfoFromURL() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(url, JsonNode.class).getBody();
    }
}
