package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.PacienteDAO;
import es.uvigo.dagss.recetas.entidades.Paciente;

@Service
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private PacienteDAO pacienteDAO;

    @Override
    @Transactional
    public Paciente crear(Paciente paciente) {
        return pacienteDAO.save(paciente);
    }

    @Override
    @Transactional
    public Paciente modificar(Paciente paciente) {
        return pacienteDAO.save(paciente);
    }

    @Override
    @Transactional
    public void eliminar(Paciente paciente) {
        paciente.desactivar();
        pacienteDAO.save(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarPacientes() {
        return pacienteDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteDAO.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorLocalidad(String localidad) {
        return pacienteDAO.findByDireccionLocalidadContaining(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorCentroSaludId(Long idCentro) {
        return pacienteDAO.findByCentroSaludId(idCentro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorMedicoId(Long idMedico) {
        return pacienteDAO.findByMedicoId(idMedico);
    }
}
