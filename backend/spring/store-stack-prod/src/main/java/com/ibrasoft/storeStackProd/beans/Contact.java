package com.ibrasoft.storeStackProd.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.ibrasoft.storeStackProd.response.ContactDTO;

import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "contact")
@XmlRootElement
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "sujet", nullable = false)
    private String sujet;
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Column(name = "CreatedOn", nullable = false)
    private Date CreatedOn;

    public Contact(ContactDTO contactDTO) {
        this.email = contactDTO.getEmail();
        this.nom = contactDTO.getNom();
        this.sujet = contactDTO.getSujet();
        this.message = contactDTO.getMessage();
        this.CreatedOn = new Date();
        if (null != contactDTO.getId())
            this.id = contactDTO.getId();
    }

    @Override
	public String toString() {
		return "\n Contact { \n id : " + id 
                + ",\n email : " + email 
                + ", \n nom : " + nom 
                + ", \n sujet : " + sujet 
                + ", \n message : " + message 
                + ", \n Create at : " + CreatedOn 
                + "\n}\n";
	}

    /**
     * 
     */
    public Contact() {
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the sujet
     */
    public String getSujet() {
        return sujet;
    }

    /**
     * @param sujet the sujet to set
     */
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return CreatedOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }

}
