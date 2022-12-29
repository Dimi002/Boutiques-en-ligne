package com.ibrasoft.storeStackProd.service.impl;

import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.InvalidInputException;
import com.ibrasoft.storeStackProd.service.DBInitializationService;
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
    public void run(String... args) throws ClinicException, InvalidInputException {
        dbInitService.initSettings();
        dbInitService.initPermissions();
        dbInitService.initRoles();
        dbInitService.initUsers();
    }
}
