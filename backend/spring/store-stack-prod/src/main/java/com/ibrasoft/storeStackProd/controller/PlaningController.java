package com.ibrasoft.storeStackProd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibrasoft.storeStackProd.beans.Planing;
import com.ibrasoft.storeStackProd.response.PlaningDTO;
import com.ibrasoft.storeStackProd.service.PlaningService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/planing")
public class PlaningController {
    @Autowired
    private PlaningService planingService;

    @RequestMapping(method = RequestMethod.GET, value = "/records/{specialistID}/{planDay}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Planing>> records(@PathVariable("specialistID") Integer specialistID,
            @PathVariable("planDay") Integer planDay) {
        List<Planing> list = this.planingService.records(specialistID, planDay);
        return new ResponseEntity<List<Planing>>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> create(@RequestBody List<PlaningDTO> planingDTOs) {
        Boolean result = this.planingService.create(planingDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> update(@RequestBody List<PlaningDTO> planingDTOs) {
        Boolean result = this.planingService.update(planingDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteOne", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteOne(@RequestBody PlaningDTO planingDTO) {
        Boolean result = this.planingService.delete(planingDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteMany", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteMany(@RequestBody List<PlaningDTO> planingDTOs) {
        Boolean result = this.planingService.delete(planingDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
