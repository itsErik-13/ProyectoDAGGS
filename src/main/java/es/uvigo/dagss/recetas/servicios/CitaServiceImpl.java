package es.uvigo.dagss.recetas.servicios;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.CitaDAO;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.EstadoCita;
import es.uvigo.dagss.recetas.entidades.Medico;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaDAO citaDAO;

    @Override
    @Transactional
    public Cita crear(Cita cita) {
        return citaDAO.save(cita);
    }

    @Override
    @Transactional
    public Cita modificar(Cita cita) {
        return citaDAO.save(cita);
    }

    @Override
    @Transactional
    public void eliminar(Cita cita) {
        citaDAO.delete(cita);
    }

    @Override
    @Transactional
    public void anular(Cita cita) {
        cita.anular();
        citaDAO.save(cita);
    }

    @Override
    @Transactional
    public void marcarAusente(Cita cita) {
        cita.ausente();
        citaDAO.save(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(Long id) {
        return citaDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> listarCitas() {
        return citaDAO.findAllByOrderByFechaAscHoraAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorFecha(Date fecha) {
        return citaDAO.findByFecha(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorMedicoId(Long idMedico) {
        return citaDAO.findByMedicoId(idMedico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorPacienteId(Long idPaciente) {
        return citaDAO.findByPacienteId(idPaciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorFechaAndPacienteIdOrderByFechaAscHoraAsc(Date fecha, Long idPaciente) {
        return citaDAO.findByFechaAndPacienteIdOrderByFechaAscHoraAsc(fecha, idPaciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorFechaAndMedicoIdOrderByFechaAscHoraAsc(Date fecha, Long idMedico) {
        return citaDAO.findByFechaAndMedicoIdOrderByFechaAscHoraAsc(fecha, idMedico);
    }

    @Override
    public List<LocalTime> obtenerHuecosDisponibles(Date fecha, Long idMedico) {
        List<Cita> citasPlanificadas = citaDAO.findByMedicoIdAndFechaAndEstado(idMedico, fecha, EstadoCita.PLANIFICADA);
        Set<LocalTime> horasOcupadas = new HashSet<>();

        for (Cita cita : citasPlanificadas) {
            horasOcupadas.add(LocalTime.of(cita.getHora().getHours(), cita.getHora().getMinutes(), cita.getHora().getSeconds()));
        }

        List<LocalTime> horasDisponibles = new ArrayList<>();
        LocalTime horaInicio = LocalTime.of(8, 30, 0);
        LocalTime horaFin = LocalTime.of(15, 30, 0);
        LocalTime horaActual = horaInicio;

        while (horaActual.isBefore(horaFin)) {
            if (!horasOcupadas.contains(horaActual)) {
                horasDisponibles.add(horaActual);
            }
            horaActual = horaActual.plusMinutes(15);
        }

        horasDisponibles.removeAll(horasOcupadas);

        return horasDisponibles;
    }
}
