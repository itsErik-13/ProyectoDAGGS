package es.uvigo.dagss.recetas.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.CitaDAO;
import es.uvigo.dagss.recetas.entidades.Cita;

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
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(Long id) {
        return citaDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarTodos() {
        return citaDAO.findAll();
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
    
}
