package es.uvigo.dagss.recetas.daos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.EstadoCita;

public interface CitaDAO extends JpaRepository<Cita, Long> {
    public List<Cita> findAllByOrderByFechaAscHoraAsc();

    public List<Cita> findByPacienteId(Long idPaciente);
    
    public List<Cita> findByMedicoId(Long idMedico);

    public List<Cita> findByFechaAndMedicoIdOrderByFechaAscHoraAsc(Date fecha, Long idMedico);

    public List<Cita> findByFechaAndPacienteIdOrderByFechaAscHoraAsc(Date fecha, Long idPaciente);

    public List<Cita> findByFecha(Date fecha);

    public List<Cita> findByMedicoIdAndFechaAndEstado(Long medicoId, Date fecha, EstadoCita estado);
}
