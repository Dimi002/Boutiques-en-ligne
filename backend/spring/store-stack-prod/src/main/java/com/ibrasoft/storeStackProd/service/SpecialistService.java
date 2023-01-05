package com.ibrasoft.storeStackProd.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibrasoft.storeStackProd.beans.SocialMediaLinks;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.models.Boutique;

@Service
public interface SpecialistService {
	public Specialist createOrUpdateSpecialist(Specialist specialist) throws ClinicException;

	public List<Specialist> getAllSpecialist();

	public List<Specialist> getActiveSpecialist();

	public Specialist deleteSpecialist(int specialistId) throws ClinicException;

	public Specialist findBySpecialistId(Integer specialistId);

	public Specialist findByUserId(User userId);

	public Specialist findByUserId(Integer userId);

	public Specialist getSpecialistBeforeAuth(Integer id);

	public Specialist updateSocialMediaById(SocialMediaLinks socialMediaLinks, Specialist specialistFound);

	public SocialMediaLinks getSocialMediaById(Integer specialistId);

	public List<Specialist> getAllSpecialistPlaning();

	public Specialist createBoutique(Boutique boutique) throws ClinicException, IOException;

	public Specialist updateBoutique(Specialist boutique);

	public Specialist deleteBoutique(Integer boutiqueId);

	public List<Specialist> getAllBoutique();
}
