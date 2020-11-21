package com.example.vrkapireader.service;

import com.example.vrkapireader.model.Election;
import com.example.vrkapireader.repository.RequestRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    public ArrayList<Election> getFilteredData() {

        JsonNode responseData = requestRepository.getInfoFromURL().path("data");

        ArrayList<Election> filteredResult = new ArrayList<>();

        responseData.forEach(node -> filteredResult.add(new Election(
                Integer.parseInt(node.path("vrt_id").toString().replace("\"", "")),
                node.path("vrt_pav").toString().replace("\"", ""),
                node.path("rink_data").toString().replace("\"", "")
        )));

        return filteredResult;
    };

    public ArrayList<Election> sortData(ArrayList<Election> data) {
        Collections.sort(data, (a, b) -> {
            return a.getDate().compareTo(b.getDate());
        });
        return data;
    };
}
