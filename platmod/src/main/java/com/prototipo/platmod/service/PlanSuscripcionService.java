package com.prototipo.platmod.service;

import com.prototipo.platmod.entity.PlanSuscripcion;

import java.util.List;

// ============================================
// PLAN SUSCRIPCION SERVICE
// ============================================
public interface PlanSuscripcionService {
    List<PlanSuscripcion> obtenerTodos();

    PlanSuscripcion obtenerPorId(Long id);

    List<PlanSuscripcion> obtenerOrdenadosPorPrecio();

    PlanSuscripcion crear(PlanSuscripcion plan);

    PlanSuscripcion actualizar(Long id, PlanSuscripcion plan);

    void eliminar(Long id);
}
