package com.dimsoft.clinicStackProd.service;

import java.util.List;

import com.dimsoft.clinicStackProd.util.SearchResponse;

public interface SearchService {
    public List<SearchResponse> search(String keyword);
}
