package com.ibrasoft.storeStackProd.response;

import java.util.List;

public class SpecialitiesIds {
    private List<Integer> specialitiesIdsList;

    public SpecialitiesIds(List<Integer> specialitiesIdsList) {
        this.specialitiesIdsList = specialitiesIdsList;
    }

    public List<Integer> getSpecialitiesIdsList() {
        return specialitiesIdsList;
    }

    public void setSpecialitiesIdsList(List<Integer> specialitiesIdsList) {
        this.specialitiesIdsList = specialitiesIdsList;
    }

}