package com.dimsoft.clinicStackProd.models;

public enum EnumStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    CANCELED("CANCELED");

    private String enumStatut;

    private EnumStatus(String enumStatut) {
        this.enumStatut = enumStatut;
    }

    public String getStatus() {
        return this.enumStatut;
    }

}
