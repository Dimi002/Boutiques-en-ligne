package com.ibrasoft.storeStackProd.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ibrasoft.storeStackProd.response.SettingDTO;

@Entity
@Table(name = "setting")
public class Setting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "logo", nullable = true)
    private String logo;

    @Column(name = "planing_start_at")
    private Integer planingStartAt;

    @Column(name = "planing_end_at")
    private Integer planingEndAt;

    @Column(name = "email")
    private String email;

    @Column(name = "email2", nullable = true)
    private String email2;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private String tel;

    @Column(name = "tel2", nullable = true)
    private String tel2;

    @Column(name = "ln", nullable = true)
    private String ln;

    @Column(name = "fb", nullable = true)
    private String fb;

    @Column(name = "twitter", nullable = true)
    private String twitter;

    @Column(name = "insta", nullable = true)
    private String insta;

    @Column(name = "video", nullable = true)
    private String video;

    @Column(name = "video_cover", nullable = true)
    private String videoCover;

    public boolean isValid() {
        return email != null && adresse != null && tel != null && planingEndAt != null && planingStartAt != null;
    }

    public Setting(String logo, Integer planingStartAt, Integer planingEndAt, String email, String email2,
            String adresse, String tel, String tel2, String ln, String fb, String twitter, String insta, String video,
            String videoCover) {
        this.logo = logo;
        this.planingStartAt = planingStartAt;
        this.planingEndAt = planingEndAt;
        this.email = email;
        this.email2 = email2;
        this.adresse = adresse;
        this.tel = tel;
        this.tel2 = tel2;
        this.ln = ln;
        this.fb = fb;
        this.twitter = twitter;
        this.insta = insta;
        this.video = video;
        this.videoCover = videoCover;
    }

    /**
     * 
     */
    public Setting(SettingDTO settingDTO) {
        this.id = settingDTO.getId();
        this.logo = settingDTO.getLogo();
        this.email = settingDTO.getEmail();
        this.email2 = settingDTO.getEmail2();
        this.adresse = settingDTO.getAdresse();
        this.tel = settingDTO.getTel();
        this.ln = settingDTO.getLn();
        this.fb = settingDTO.getFb();
        this.twitter = settingDTO.getTwitter();
        this.insta = settingDTO.getInsta();
        this.tel2 = settingDTO.getTel2();
        this.planingStartAt = settingDTO.getPlaningStartAt();
        this.planingEndAt = settingDTO.getPlaningEndAt();
        this.video = settingDTO.getVideo();
        this.videoCover = settingDTO.getVideoCover();
    }

    /**
     * 
     */
    public Setting(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public Setting() {
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

}
