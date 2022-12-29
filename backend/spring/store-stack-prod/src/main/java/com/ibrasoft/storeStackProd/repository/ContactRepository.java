package com.ibrasoft.storeStackProd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibrasoft.storeStackProd.beans.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
