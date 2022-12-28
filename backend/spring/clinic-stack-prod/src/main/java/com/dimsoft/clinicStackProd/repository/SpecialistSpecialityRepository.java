package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.SpecialistSpeciality;
import com.dimsoft.clinicStackProd.beans.SpecialistSpecialityId;
import com.dimsoft.clinicStackProd.beans.Speciality;

public interface SpecialistSpecialityRepository extends JpaRepository<SpecialistSpeciality, SpecialistSpecialityId> {

    @Query("SELECT ss FROM SpecialistSpeciality ss WHERE ss.status = :activatedStatus OR ss.status = :deActivatedStatus")
    List<SpecialistSpeciality> getAllSpecialistSpecialities(@Param("activatedStatus") short activatedStatus,
            @Param("deActivatedStatus") short deActivatedStatus);

    SpecialistSpeciality findBySpecialistSpecialityId(SpecialistSpecialityId specialistSpecialityId);

    List<SpecialistSpeciality> findBySpecialistSpecialityIdSpecialist(Specialist specialist);

    List<SpecialistSpeciality> findBySpecialistSpecialityIdSpeciality(Speciality speciality);

    List<SpecialistSpeciality> findBySpecialistSpecialityIdSpecialistSpecialistId(Integer specialistId);

}
