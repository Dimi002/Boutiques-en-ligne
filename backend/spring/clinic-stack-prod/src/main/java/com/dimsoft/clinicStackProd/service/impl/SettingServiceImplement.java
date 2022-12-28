package com.dimsoft.clinicStackProd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.beans.Setting;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.SettingRepository;
import com.dimsoft.clinicStackProd.response.SettingDTO;
import com.dimsoft.clinicStackProd.service.SettingService;
import com.dimsoft.clinicStackProd.util.Constants;

@Service
public class SettingServiceImplement implements SettingService {
    @Autowired
    private SettingRepository settingRepository;

    /**
     * 
     * @param settingDTO
     * @return
     * @throws ClinicException
     */
    @Override
    public Setting createOrUpdate(SettingDTO settingDTO) throws ClinicException {
        if (!settingDTO.isValid())
            throw new ClinicException(Constants.INVALID_INPUT, Constants.SETTING_MUST_NOT_BE_NULL);
        if (null == settingDTO.getId()) {
            List<Setting> list = settingRepository.findAll();
            for (Setting s : list) {
                settingRepository.delete(s);
            }
        }
        Setting s = new Setting(settingDTO);
        settingRepository.save(s);
        return s;
    }

    /**
     * 
     * @param setting
     * @return
     * @throws ClinicException
     */
    @Override
    public Setting createOrUpdate(Setting setting) throws ClinicException {
        if (!setting.isValid())
            throw new ClinicException(Constants.INVALID_INPUT, Constants.SETTING_MUST_NOT_BE_NULL);
        if (null == setting.getId()) {
            List<Setting> list = settingRepository.findAll();
            for (Setting s : list) {
                settingRepository.delete(s);
            }
        }
        settingRepository.save(setting);
        return setting;
    }

    /**
     * 
     * @param id
     * @return
     * @throws ClinicException
     */
    @Override
    public Setting delete(Long id) throws ClinicException {
        if (this.settingRepository.getReferenceById(id) == null)
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SETTING_NOT_FOUND);
        Setting s = new Setting(id);
        settingRepository.delete(s);
        return s;
    }

    /**
     * 
     * @return
     * @throws ClinicException
     */
    @Override
    public Setting getSetting() throws ClinicException {
        List<Setting> list = settingRepository.findAll();
        if (list == null)
            throw new ClinicException(Constants.INVALID_INPUT, Constants.SETTING_IS_NULL);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
