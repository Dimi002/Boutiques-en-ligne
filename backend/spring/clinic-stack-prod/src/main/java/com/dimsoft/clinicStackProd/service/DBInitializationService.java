package com.dimsoft.clinicStackProd.service;

import com.dimsoft.clinicStackProd.exceptions.ClinicException;

public interface DBInitializationService {

	public void initPermissions();

	public void initRoles();

	public void initUsers();

	public void initSpecialities();

	public void initSettings() throws ClinicException;
}
