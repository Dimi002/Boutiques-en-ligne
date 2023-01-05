package com.ibrasoft.storeStackProd.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;

@Service
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

	@Query("SELECT s FROM Specialist s WHERE s.status = :activatedStatus OR s.status = :deActivatedStatus")
	List<Specialist> getAllSpecialists(@Param("activatedStatus") short activatedStatus,
			@Param("deActivatedStatus") short deActivatedStatus);

	@Query("SELECT s FROM Specialist s WHERE s.status = :activatedStatus")
	List<Specialist> getActivatedSpecialists(@Param("activatedStatus") short activatedStatus);

	@Transactional
	@Modifying
	@Query("UPDATE Specialist u SET u.yearOfExperience = :yearOfExperience, u.biography = :biography, u.city = :city, u.lastUpdateOn = :lastUpdateOn, u.gender = :gender WHERE u.specialistId = :specialistId")
	void updateSpecialistByAdmin(@Param("yearOfExperience") Integer yearOfExperience,
			@Param("biography") String biography,
			@Param("city") String city,
			@Param("lastUpdateOn") Date lastUpdateOn, @Param("gender") String gender,
			@Param("specialistId") Integer specialistId);

	Specialist findByYearOfExperience(Integer yearOfExperience);

	Specialist findBySpecialistId(Integer specialistId);

	Specialist findByUserId(User userId);

	Specialist findByUserIdId(Integer userId);

	Specialist findByBoutiqueName(String name);

	@Transactional
	@Modifying
	@Query("UPDATE Specialist s SET s.socialMediaLinks = :socialMediaLinks WHERE s.specialistId = :specialistId")
	void updateSocialMediaById(
			@Param("socialMediaLinks") String socialMediaLinks,
			@Param("specialistId") Integer specialistId);

}
