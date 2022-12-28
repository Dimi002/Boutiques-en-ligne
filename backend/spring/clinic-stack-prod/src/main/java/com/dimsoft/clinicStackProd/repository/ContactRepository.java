package com.dimsoft.clinicStackProd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dimsoft.clinicStackProd.beans.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
