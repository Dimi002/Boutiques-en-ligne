package com.dimsoft.clinicStackProd.service.impl;

import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.service.DBInitializationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!test")
@Service
public class DbInit implements CommandLineRunner {
    private DBInitializationService dbInitService;

    public DbInit(DBInitializationService dbInitService) {
        this.dbInitService = dbInitService;
    }

    @Override
    public void run(String... args) throws ClinicException {
        dbInitService.initSettings();
        dbInitService.initPermissions();
        dbInitService.initSpecialities();
        dbInitService.initRoles();
        dbInitService.initUsers();
    }
}
