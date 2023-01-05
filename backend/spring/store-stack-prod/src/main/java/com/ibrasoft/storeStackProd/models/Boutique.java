package com.ibrasoft.storeStackProd.models;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class Boutique {
    private String boutiqueName;
    private MultipartFile boutiqueImage;
    private String boutiqueQuater;
    private String boutiqueDescription;
    private String boutiqueUser;

    public String getBoutiqueUser() {
        return boutiqueUser;
    }

    public void setBoutiqueUser(String boutiqueUser) {
        this.boutiqueUser = boutiqueUser;
    }

    public Boutique() {
    }

    public String getBoutiqueName() {
        return boutiqueName;
    }

    public void setBoutiqueName(String boutiqueName) {
        this.boutiqueName = boutiqueName;
    }

    public MultipartFile getBoutiqueImage() {
        return boutiqueImage;
    }

    public void setBoutiqueImage(MultipartFile boutiqueImage) {
        this.boutiqueImage = boutiqueImage;
    }

    public String getBoutiqueQuater() {
        return boutiqueQuater;
    }

    public void setBoutiqueQuater(String boutiqueQuater) {
        this.boutiqueQuater = boutiqueQuater;
    }

    public String getBoutiqueDescription() {
        return boutiqueDescription;
    }

    public void setBoutiqueDescription(String boutiqueDescription) {
        this.boutiqueDescription = boutiqueDescription;
    }
}
