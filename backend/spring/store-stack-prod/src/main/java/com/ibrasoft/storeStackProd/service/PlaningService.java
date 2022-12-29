package com.ibrasoft.storeStackProd.service;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Planing;
import com.ibrasoft.storeStackProd.response.PlaningDTO;

public interface PlaningService {
    public Boolean create(List<PlaningDTO> planingDTOs);

    public Boolean update(List<PlaningDTO> planingDTOs);

    public Boolean delete(PlaningDTO planingDTO);

    public Boolean delete(List<PlaningDTO> planingDTOs);

    public List<Planing> records(Integer specialistID, Integer planDay);
}
