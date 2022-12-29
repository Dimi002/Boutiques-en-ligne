package com.ibrasoft.storeStackProd.beans;

public class SocialMediaLinks {
    private String facebook;
    private String instagram;
    private String twitter;
    private String linkedin;
    private String pinterest;
    private String youtube;

    public String getFacebook() {
        return facebook;
    }

    public SocialMediaLinks() {
    }

    public SocialMediaLinks(String facebook, String instagram, String twitter, String linkedin, String pinterest,
            String youtube) {
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.pinterest = pinterest;
        this.youtube = youtube;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getPinterest() {
        return pinterest;
    }

    public void setPinterest(String pinterest) {
        this.pinterest = pinterest;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

}
