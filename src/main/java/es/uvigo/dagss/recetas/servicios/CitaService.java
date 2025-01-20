package es.uvigo.dagss.recetas.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Cita;

public interface CitaService {
    public Cita crear(Cita cita);
    
    public Cita modificar(Cita cita);

    public void eliminar(Cita cita);

    public void anular(Cita cita);

    public Optional<Cita> buscarPorId(Long id);

    public List<Cita> listarCitas();

    public List<Cita> buscarPorFecha(Date fecha);

    public List<Cita> buscarPorMedicoId(Long idMedico);

    public List<Cita> buscarPorPacienteId(Long idPaciente);

    public List<Cita> buscarPorFechaAndPacienteIdOrderByFechaAscHoraAsc(Date fecha, Long idPaciente);

    public List<Cita> buscarPorFechaAndMedicoIdOrderByFechaAscHoraAsc(Date fecha, Long idMedico);
}
