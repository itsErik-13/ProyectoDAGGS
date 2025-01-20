package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Prescripcion;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescripcionDAO extends JpaRepository<Prescripcion, Long> {

    public Optional<Prescripcion> findById(Long id);

    public List<Prescripcion> findByPacienteId(Long id);

    public List<Prescripcion> findByPacienteIdAndFechaFinGreaterThanEqualOrderByFechaFinAsc(Long pacienteId, Date fechaActual);
    
}
