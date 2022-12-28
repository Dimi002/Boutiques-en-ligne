package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dimsoft.clinicStackProd.beans.Planing;
import com.dimsoft.clinicStackProd.beans.Specialist;

public interface PlaningRepository extends JpaRepository<Planing, Long> {
    @Query("SELECT p FROM Planing p WHERE p.specialist = :specialist AND p.planDay = :planDay")
    public List<Planing> recordPlanings(@Param("specialist") Specialist specialist, @Param("planDay") Integer planDay);

    @Query("SELECT p FROM Planing p WHERE p.specialist = :specialist")
    public List<Planing> recordSpecialistPlanings(@Param("specialist") Specialist specialist);

    public List<Planing> findBySpecialistSpecialistIdAndPlanDay(Integer specialistId, Integer planDay);

}
