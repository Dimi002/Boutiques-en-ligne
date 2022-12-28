package com.dimsoft.clinicStackProd.service;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Contact;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.response.ContactDTO;

public interface ContactService {
    public Contact create(ContactDTO contactDTO) throws ClinicException;

    public List<Contact> recods() throws ClinicException;

    public Contact delete(Long id) throws ClinicException;
}
