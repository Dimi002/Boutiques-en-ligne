package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dimsoft.clinicStackProd.beans.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer>, JpaSpecificationExecutor<Speciality> {
    boolean existsBySpecialityName(String specialityName);

    @Query("SELECT s FROM Speciality s WHERE s.status = :activatedStatus OR s.status = :deActivatedStatus")
    List<Speciality> getAllSpecialities(@Param("activatedStatus") short activatedStatus,
            @Param("deActivatedStatus") short deActivatedStatus);

    @Query("SELECT s FROM Speciality s WHERE s.status = :activatedStatus")
    List<Speciality> getActivatedSpecialities(@Param("activatedStatus") short activatedStatus);

    Speciality findBySpecialityName(String specialityName);

    @Query("SELECT s FROM Speciality s WHERE s.specialityName LIKE CONCAT('%',:keyword,'%')")
    List<Speciality> search(@Param("keyword") String keyword);
}
