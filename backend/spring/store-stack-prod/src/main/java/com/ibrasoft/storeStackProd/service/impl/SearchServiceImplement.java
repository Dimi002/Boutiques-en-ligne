package com.ibrasoft.storeStackProd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.Speciality;
import com.ibrasoft.storeStackProd.repository.SpecialistRepository;
import com.ibrasoft.storeStackProd.repository.SpecialityRepository;
import com.ibrasoft.storeStackProd.service.SearchService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.SearchResponse;

@Service
public class SearchServiceImplement implements SearchService {
    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public List<SearchResponse> search(String keyword) {
        List<SearchResponse> response = new ArrayList<SearchResponse>();
        List<Specialist> listTmp = this.specialistRepository.findAll();
        List<Speciality> list = this.specialityRepository.search(keyword);
        list.forEach((s) -> {
            if (s.getStatus() != Constants.STATE_DELETED) {
                SearchResponse search = new SearchResponse();
                search.setId(s.getId());
                search.setLibelle(s.getSpecialityName());
                search.setType(1);
                search.setImage(s.getSpecialityImagePath());
                response.add(search);
            }
        });
        listTmp.forEach(s -> {
            if (null != s.getUserId() && (s.getUserId().getFirstName().contains(keyword) || s.getUserId()
                    .getLastName().contains(keyword))) {
                SearchResponse search = new SearchResponse();
                search.setId(s.getSpecialistId());
                search.setLibelle(s.getUserId().getFirstName() + " " + s.getUserId().getLastName());
                search.setType(0);
                search.setImage(s.getUserId().getUserImagePath());
                response.add(search);
            }
        });
        return response;
    }

}
