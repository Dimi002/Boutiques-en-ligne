package com.ibrasoft.storeStackProd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibrasoft.storeStackProd.service.SearchService;
import com.ibrasoft.storeStackProd.util.SearchResponse;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.GET, value = "/records/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchResponse>> records(@PathVariable String keyword) {
        System.out.println(keyword);
        List<SearchResponse> result = searchService.search(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
