package com.prototipo.platmod.repository;

import com.prototipo.platmod.entity.PlanBeneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanBeneficioRepository extends JpaRepository<PlanBeneficio, Long> {
    List<PlanBeneficio> findByPlan_IdPlan(Long idPlan);
}
