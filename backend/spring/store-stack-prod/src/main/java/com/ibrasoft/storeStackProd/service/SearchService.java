package com.ibrasoft.storeStackProd.service;

import java.util.List;

import com.ibrasoft.storeStackProd.util.SearchResponse;

public interface SearchService {
    public List<SearchResponse> search(String keyword);
}
