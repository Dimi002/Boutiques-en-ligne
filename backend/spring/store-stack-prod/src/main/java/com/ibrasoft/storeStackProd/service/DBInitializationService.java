package com.ibrasoft.storeStackProd.service;

import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.InvalidInputException;

public interface DBInitializationService {

	public void initPermissions();

	public void initRoles();

	public void initUsers();

	public void initSpecialities();

	public void initSettings() throws ClinicException, InvalidInputException;
}
