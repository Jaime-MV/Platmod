package com.prototipo.platmod.controller;

import com.prototipo.platmod.entity.PlanBeneficio;
import com.prototipo.platmod.entity.PlanSuscripcion;
import com.prototipo.platmod.repository.PlanBeneficioRepository;
import com.prototipo.platmod.repository.PlanSuscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlanSuscripcionController {

    private final PlanSuscripcionRepository planRepository;
    private final PlanBeneficioRepository beneficioRepository;

    @GetMapping("/planes")
    public List<Map<String, Object>> getAllPlanes() {
        // En un caso real usaría DTOs, aquí lo haré rápido con Maps par incluir
        // beneficios
        List<PlanSuscripcion> planes = planRepository.findAll();

        return planes.stream().map(plan -> {
            Map<String, Object> map = new HashMap<>();
            map.put("idPlan", plan.getIdPlan());
            map.put("nombre", plan.getNombre());
            map.put("precio", plan.getPrecio());
            map.put("duracionDias", plan.getDuracionDias());
            map.put("descuento", plan.getDescuento());
            map.put("ofertaActiva", plan.getOfertaActiva());

            // Fetch benefits
            List<PlanBeneficio> beneficios = beneficioRepository.findByPlan_IdPlan(plan.getIdPlan());
            map.put("beneficios", beneficios);

            return map;
        }).toList();
    }

    // --- ADMIN ENDPOINTS ---

    @PutMapping("/admin/planes/{id}")
    public ResponseEntity<PlanSuscripcion> updatePlan(@PathVariable Long id, @RequestBody PlanSuscripcion planDetails) {
        PlanSuscripcion plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        plan.setNombre(planDetails.getNombre());
        plan.setPrecio(planDetails.getPrecio());
        plan.setDuracionDias(planDetails.getDuracionDias());
        plan.setDescuento(planDetails.getDescuento());
        plan.setOfertaActiva(planDetails.getOfertaActiva());

        PlanSuscripcion updatedPlan = planRepository.save(plan);
        return ResponseEntity.ok(updatedPlan);
    }

    @PostMapping("/admin/planes/{id}/beneficios")
    public PlanBeneficio addBeneficio(@PathVariable Long id, @RequestBody PlanBeneficio beneficio) {
        PlanSuscripcion plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        beneficio.setPlan(plan);
        return beneficioRepository.save(beneficio);
    }

    @DeleteMapping("/admin/planes/beneficios/{idBeneficio}")
    public ResponseEntity<?> deleteBeneficio(@PathVariable Long idBeneficio) {
        beneficioRepository.deleteById(idBeneficio);
        return ResponseEntity.ok().build();
    }
}
