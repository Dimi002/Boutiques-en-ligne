package com.dimsoft.clinicStackProd.service;

import com.dimsoft.clinicStackProd.beans.Setting;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.response.SettingDTO;

public interface SettingService {
    public Setting createOrUpdate(SettingDTO settingDTO) throws ClinicException;

    public Setting createOrUpdate(Setting setting) throws ClinicException;

    public Setting delete(Long id) throws ClinicException;

    public Setting getSetting() throws ClinicException;
}
