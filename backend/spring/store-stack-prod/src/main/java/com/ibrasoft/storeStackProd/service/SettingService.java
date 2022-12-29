package com.ibrasoft.storeStackProd.service;

import com.ibrasoft.storeStackProd.beans.Setting;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.response.SettingDTO;

public interface SettingService {
    public Setting createOrUpdate(SettingDTO settingDTO) throws ClinicException;

    public Setting createOrUpdate(Setting setting) throws ClinicException;

    public Setting delete(Long id) throws ClinicException;

    public Setting getSetting() throws ClinicException;
}
