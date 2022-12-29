package com.ibrasoft.storeStackProd.controller;

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

import com.ibrasoft.storeStackProd.beans.Setting;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.InvalidInputException;
import com.ibrasoft.storeStackProd.response.SettingDTO;
import com.ibrasoft.storeStackProd.service.SettingService;
import com.ibrasoft.storeStackProd.util.Constants;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * @throws ClinicException
     * 
     */
    @RequestMapping(method = RequestMethod.GET, value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Setting> records() throws InvalidInputException, ClinicException {
        Setting setting = settingService.getSetting();
        if (setting == null)
            throw new ClinicException(Constants.INVALID_INPUT, Constants.SETTING_IS_NULL);
        return new ResponseEntity<Setting>(setting, HttpStatus.OK);
    }

    /**
     * 
     * @param settingDTO
     * @return
     * @throws ClinicException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/createOrUpdate")
    public ResponseEntity<Setting> create(@RequestBody SettingDTO settingDTO)
            throws InvalidInputException, ClinicException {
        if (!settingDTO.isValid())
            throw new ClinicException(Constants.INVALID_INPUT, Constants.SETTING_MUST_NOT_BE_NULL);
        Setting Setting = settingService.createOrUpdate(settingDTO);
        return new ResponseEntity<Setting>(Setting, HttpStatus.OK);
    }

    /**
     * @throws ClinicException
     * 
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<Setting> delete(@PathVariable Long id) throws InvalidInputException, ClinicException {
        Setting Setting = settingService.delete(id);
        if (Setting != null) {
            return new ResponseEntity<Setting>(Setting, HttpStatus.OK);
        } else {
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SETTING_NOT_FOUND);
        }
    }
}
