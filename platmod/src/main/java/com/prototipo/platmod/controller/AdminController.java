package com.prototipo.platmod.controller;

import com.prototipo.platmod.dto.DocenteAsignacionDTO;
import com.prototipo.platmod.entity.AsignacionDocente;
import com.prototipo.platmod.entity.Curso;
import com.prototipo.platmod.entity.Usuario;
import com.prototipo.platmod.repository.AsignacionDocenteRepository;
import com.prototipo.platmod.repository.CursoRepository;
import com.prototipo.platmod.repository.UsuarioRepository;
import com.prototipo.platmod.repository.DocenteRepository;
import com.prototipo.platmod.entity.Docente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;
    private final AsignacionDocenteRepository asignacionRepository;

    // Obtener lista simple de docentes para el dropdown/modal
    @GetMapping("/docentes-list")
    public ResponseEntity<List<Usuario>> getAllDocentes() {
        return ResponseEntity.ok(usuarioRepository.findByRol(Usuario.Rol.DOCENTE));
    }

    // Obtener docentes con estado de asignación para un curso
    @GetMapping("/cursos/{idCurso}/docentes-asignacion")
    public ResponseEntity<List<DocenteAsignacionDTO>> getDocentesParaAsignacion(@PathVariable Long idCurso) {
        List<Usuario> todosDocentes = usuarioRepository.findByRol(Usuario.Rol.DOCENTE);
        List<AsignacionDocente> asignaciones = asignacionRepository.findByCurso_IdCurso(idCurso);

        List<Long> asignadosIds = asignaciones.stream()
                .map(a -> a.getUsuario().getIdUsuario())
                .toList();

        List<DocenteAsignacionDTO> dtos = todosDocentes.stream().map(docente -> {
            String especialidad = "General";
            Docente d = docenteRepository.findByUsuario(docente).orElse(null);
            if (d != null)
                especialidad = d.getEspecialidad();

            return new DocenteAsignacionDTO(
                    docente.getIdUsuario(),
                    docente.getNombre(),
                    especialidad,
                    asignadosIds.contains(docente.getIdUsuario()));
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // Actualizar asignaciones masivamente
    @PutMapping("/cursos/{idCurso}/asignaciones")
    public ResponseEntity<?> updateDocentesPorCurso(@PathVariable Long idCurso, @RequestBody List<Long> docentesIds) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Borrar anteriores
        asignacionRepository.deleteByCurso_IdCurso(idCurso);

        // Crear nuevas
        List<Usuario> docentes = usuarioRepository.findAllById(docentesIds);

        for (Usuario docente : docentes) {
            AsignacionDocente asignacion = new AsignacionDocente();
            asignacion.setCurso(curso);
            asignacion.setUsuario(docente);
            asignacionRepository.save(asignacion);
        }

        return ResponseEntity.ok().build();
    }

    // Endpoints básicos para cursos (que también faltaban si se borró todo)
    @PostMapping("/cursos")
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso cursoDetails) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        curso.setTitulo(cursoDetails.getTitulo());
        curso.setDescripcion(cursoDetails.getDescripcion());
        // ... otros campos
        return ResponseEntity.ok(cursoRepository.save(curso));
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
        cursoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
