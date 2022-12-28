package com.dimsoft.clinicStackProd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.beans.Contact;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.exceptions.InvalidInputException;
import com.dimsoft.clinicStackProd.mail.MailMail;
import com.dimsoft.clinicStackProd.repository.ContactRepository;
import com.dimsoft.clinicStackProd.response.ContactDTO;
import com.dimsoft.clinicStackProd.service.ContactService;
import com.dimsoft.clinicStackProd.service.SettingService;
import com.dimsoft.clinicStackProd.util.Constants;

@Service
public class ContactServiceImplement implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SettingService settingService;
    @Value("${app.default_clinic_email}")
    public static String defaultClinicEmail;

    /**
     * 
     * @param contactDTO
     * @return
     * @throws ClinicException
     */
    @Override
    public Contact create(ContactDTO contactDTO) throws ClinicException {
        if (!contactDTO.isValid())
            throw new ClinicException(Constants.INVALID_INPUT, Constants.CONTACT_MUST_BE_NOT_NULL);
        Contact contact = new Contact(contactDTO);
        contact = this.contactRepository.save(contact);
        MailMail mail = new MailMail(mailSender);
        String message = "Email: " + contact.getEmail() + ";\n" + "Nom: " + contact.getNom() + "; \n\n"
                + contact.getMessage();
        String sendTo = settingService.getSetting() != null ? settingService.getSetting().getEmail()
                : defaultClinicEmail;
        if (sendTo == null)
            sendTo = "arleonzemtsop@gmail.com";
        mail.sendMailAlert(contact.getSujet(), message, sendTo);
        return contact;
    }

    /**
     * 
     * @return
     * @throws ClinicException
     */
    @Override
    public List<Contact> recods() throws ClinicException {
        List<Contact> contacts = this.contactRepository.findAll();
        if (contacts == null)
            throw new ClinicException(Constants.INVALID_INPUT, Constants.CONTACT_IS_NULL);
        return contacts;
    }

    /**
     * 
     * @param id
     * @return
     * @throws ClinicException
     */
    @Override
    public Contact delete(Long id) throws ClinicException {
        if (this.contactRepository.getReferenceById(id) == null)
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.CONTACT_NOT_FOUND);
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }

}
