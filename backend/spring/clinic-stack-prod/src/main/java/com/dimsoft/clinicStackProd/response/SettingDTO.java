package com.dimsoft.clinicStackProd.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SettingDTO {
    private Long id;

    private String logo;

    private String email;

    private String email2;

    private String adresse;

    private String tel;

    private String tel2;

    private String ln;

    private String fb;

    private String twitter;

    private String insta;

    private Integer planingStartAt;

    private Integer planingEndAt;

    private String video;

    private String videoCover;

    public boolean isValid() {
        return  email != null && adresse != null && tel != null  && planingEndAt !=null && planingStartAt != null;
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
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return the video
     */
    public String getVideo() {
        return video;
    }

    /**
     * @param video the video to set
     */
    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * @return the videoCover
     */
    public String getVideoCover() {
        return videoCover;
    }

    /**
     * @param videoCover the videoCover to set
     */
    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
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
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the ln
     */
    public String getLn() {
        return ln;
    }

    /**
     * @param ln the ln to set
     */
    public void setLn(String ln) {
        this.ln = ln;
    }

    /**
     * @return the fb
     */
    public String getFb() {
        return fb;
    }

    /**
     * @param fb the fb to set
     */
    public void setFb(String fb) {
        this.fb = fb;
    }

    /**
     * @return the twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * @param twitter the twitter to set
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    /**
     * @return the insta
     */
    public String getInsta() {
        return insta;
    }

    /**
     * @param insta the insta to set
     */
    public void setInsta(String insta) {
        this.insta = insta;
    }

    /**
     * @return the email2
     */
    public String getEmail2() {
        return email2;
    }

    /**
     * @param email2 the email2 to set
     */
    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    /**
     * @return the tel2
     */
    public String getTel2() {
        return tel2;
    }

    /**
     * @param tel2 the tel2 to set
     */
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    /**
     * @return the planingStartAt
     */
    public Integer getPlaningStartAt() {
        return planingStartAt;
    }

    /**
     * @param planingStartAt the planingStartAt to set
     */
    public void setPlaningStartAt(Integer planingStartAt) {
        this.planingStartAt = planingStartAt;
    }

    /**
     * @return the planingEndAt
     */
    public Integer getPlaningEndAt() {
        return planingEndAt;
    }

    /**
     * @param planingEndAt the planingEndAt to set
     */
    public void setPlaningEndAt(Integer planingEndAt) {
        this.planingEndAt = planingEndAt;
    }

}
