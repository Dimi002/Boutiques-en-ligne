package com.ibrasoft.storeStackProd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Planing;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.repository.PlaningRepository;
import com.ibrasoft.storeStackProd.response.PlaningDTO;
import com.ibrasoft.storeStackProd.service.PlaningService;

@Service
public class PlaningServiceImplement implements PlaningService {
    @Autowired
    PlaningRepository planingRepository;

    @Override
    public Boolean create(List<PlaningDTO> planingDTOs) {
        planingDTOs.forEach(p -> {
            Planing planing = new Planing(p);
            planingRepository.save(planing);
        });
        return true;
    }

    @Override
    public Boolean update(List<PlaningDTO> planingDTOs) {
        if (planingDTOs.size() > 0) {
            Planing p = new Planing(planingDTOs.get(0));
            List<Planing> oldPlaning = planingRepository.recordPlanings(p.getSpecialist(), p.getPlanDay());
            for (Planing planing : oldPlaning) {
                planingRepository.delete(planing);
            }
        }
        this.create(planingDTOs);
        return true;
    }

    @Override
    public Boolean delete(PlaningDTO planingDTO) {
        Planing p = new Planing(planingDTO);
        planingRepository.delete(p);
        return true;
    }

    @Override
    public Boolean delete(List<PlaningDTO> planingDTOs) {
        for (PlaningDTO planingDTO : planingDTOs) {
            Planing p = new Planing(planingDTO);
            System.out.println();
            System.out.println(p.getId());
            System.out.println();
            planingRepository.delete(p);
        }
        return true;
    }

    @Override
    public List<Planing> records(Integer specialistID, Integer planDay) {
        Specialist s = new Specialist();
        s.setSpecialistId(specialistID);
        List<Planing> list = planingRepository.recordPlanings(s, planDay);
        return list;
    }

}
