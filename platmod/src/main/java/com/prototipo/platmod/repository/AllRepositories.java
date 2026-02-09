package com.prototipo.platmod.repository;
import com.prototipo.platmod.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// PerfilDetalle Repository
@Repository
interface PerfilDetalleRepository extends JpaRepository<PerfilDetalle, Long> {
    Optional<PerfilDetalle> findByUsuario(Usuario usuario);
}

// PlanSuscripcion Repository
@Repository
interface PlanSuscripcionRepository extends JpaRepository<PlanSuscripcion, Long> {
    List<PlanSuscripcion> findByOrderByPrecioAsc();
}

// Estudiante Repository
@Repository
interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByUsuario(Usuario usuario);
}

// Suscripcion Repository
@Repository
interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByEstudiante(Estudiante estudiante);
    List<Suscripcion> findByEstado(Boolean estado);
}

// Docente Repository
@Repository
interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByUsuario(Usuario usuario);
    List<Docente> findByEstadoDocente(Boolean estadoDocente);
}

// Curso Repository
@Repository
interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByAdministrador(Usuario administrador);
    List<Curso> findByTituloContainingIgnoreCase(String titulo);
}

// AsignacionDocente Repository
@Repository
interface AsignacionDocenteRepository extends JpaRepository<AsignacionDocente, Long> {
    List<AsignacionDocente> findByCurso(Curso curso);
    List<AsignacionDocente> findByUsuario(Usuario usuario);
}

// Leccion Repository
@Repository
interface LeccionRepository extends JpaRepository<Leccion, Long> {
    List<Leccion> findByCurso(Curso curso);
    List<Leccion> findByDocente(Docente docente);
}

// Modulo Repository
@Repository
interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findByLeccionOrderByOrdenAsc(Leccion leccion);
}

// ProgresoEstudiante Repository
@Repository
interface ProgresoEstudianteRepository extends JpaRepository<ProgresoEstudiante, Long> {
    List<ProgresoEstudiante> findByEstudiante(Estudiante estudiante);
    List<ProgresoEstudiante> findByEstudianteAndCompletado(Estudiante estudiante, Boolean completado);
}

// Certificado Repository
@Repository
interface CertificadoRepository extends JpaRepository<Certificado, Long> {
    List<Certificado> findByEstudiante(Estudiante estudiante);
    Optional<Certificado> findByCodigoVerificacion(String codigoVerificacion);
}

// Comentario Repository
@Repository
interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByLeccion(Leccion leccion);
    List<Comentario> findByUsuario(Usuario usuario);
}

// ChatGrupal Repository
@Repository
interface ChatGrupalRepository extends JpaRepository<ChatGrupal, Long> {
    List<ChatGrupal> findByLeccionOrderByIdChatMsgDesc(Leccion leccion);
}

// MensajePrivado Repository
@Repository
interface MensajePrivadoRepository extends JpaRepository<MensajePrivado, Long> {
    List<MensajePrivado> findByEmisorOrReceptorOrderByFechaEnvioDesc(Usuario emisor, Usuario receptor);
}

// ForoPregunta Repository
@Repository
interface ForoPreguntaRepository extends JpaRepository<ForoPregunta, Long> {
    List<ForoPregunta> findByCategoria(String categoria);
    List<ForoPregunta> findByUsuario(Usuario usuario);
}

// ForoRespuesta Repository
@Repository
interface ForoRespuestaRepository extends JpaRepository<ForoRespuesta, Long> {
    List<ForoRespuesta> findByPregunta(ForoPregunta pregunta);
    List<ForoRespuesta> findByPreguntaAndEsVerificada(ForoPregunta pregunta, Boolean esVerificada);
}
