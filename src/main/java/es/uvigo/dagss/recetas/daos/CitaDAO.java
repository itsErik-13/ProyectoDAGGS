package es.uvigo.dagss.recetas.daos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uvigo.dagss.recetas.entidades.Cita;

public interface CitaDAO extends JpaRepository<Cita, Long> {
    public List<Cita> findByPacienteId(Long idPaciente);
    
    public List<Cita> findByMedicoId(Long idMedico);

    public List<Cita> findByFecha(Date fecha);
}
