package com.ibrasoft.storeStackProd.service;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Contact;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.response.ContactDTO;

public interface ContactService {
    public Contact create(ContactDTO contactDTO) throws ClinicException;

    public List<Contact> recods() throws ClinicException;

    public Contact delete(Long id) throws ClinicException;
}
