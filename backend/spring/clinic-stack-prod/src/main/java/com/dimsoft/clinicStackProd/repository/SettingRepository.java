package com.dimsoft.clinicStackProd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dimsoft.clinicStackProd.beans.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long> {

}
