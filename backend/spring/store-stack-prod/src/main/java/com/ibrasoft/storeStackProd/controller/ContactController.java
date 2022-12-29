package com.ibrasoft.storeStackProd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibrasoft.storeStackProd.beans.Contact;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.InvalidInputException;
import com.ibrasoft.storeStackProd.response.ContactDTO;
import com.ibrasoft.storeStackProd.service.ContactService;
import com.ibrasoft.storeStackProd.util.Constants;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    /**
     * 
     * @return
     * @throws ClinicException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contact>> records() throws ClinicException {
        List<Contact> contacts = contactService.recods();
        if (contacts == null)
            throw new ClinicException(Constants.INVALID_INPUT, Constants.CONTACT_IS_NULL);
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }

    /**
     * 
     * @param contactDTO
     * @return
     * @throws ClinicException
     * @throws InvalidInputException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Contact> create(@RequestBody ContactDTO contactDTO) throws ClinicException {
        if (!contactDTO.isValid())
            throw new ClinicException(Constants.INVALID_INPUT, Constants.CONTACT_MUST_BE_NOT_NULL);
        Contact contact = contactService.create(contactDTO);
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }

    /**
     * 
     * @param id
     * @return
     * @throws ClinicException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<Contact> delete(@PathVariable Long id) throws ClinicException {
        Contact contact = contactService.delete(id);
        if (contact != null) {
            return new ResponseEntity<Contact>(contact, HttpStatus.OK);
        } else {
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.CONTACT_NOT_FOUND);
        }
    }

}
