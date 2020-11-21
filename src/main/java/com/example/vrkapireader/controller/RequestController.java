package com.example.vrkapireader.controller;

import com.example.vrkapireader.model.Election;
import com.example.vrkapireader.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    @GetMapping("/results")
    public ResponseEntity<ArrayList<Election>> getFilteredElectionData(@RequestParam(defaultValue = "0") String sorted) {

        ArrayList<Election> formattedNode = requestService.getFilteredData();
        ResponseEntity<ArrayList<Election>> response;

        if(sorted.equals("0")) {
            response = new ResponseEntity<>(formattedNode, HttpStatus.OK);
        } else if (sorted.equals("1")) {
            response = new ResponseEntity<>(requestService.sortData(formattedNode), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    };

}
