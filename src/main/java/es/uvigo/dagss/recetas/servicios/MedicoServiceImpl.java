package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.MedicoDAO;
import es.uvigo.dagss.recetas.entidades.Medico;

@Service
public class MedicoServiceImpl implements MedicoService {
    
    @Autowired
    private MedicoDAO medicoDAO;

    @Override
    @Transactional
    public Medico crear(Medico medico) {
        return medicoDAO.save(medico);
    }

    @Override
    @Transactional
    public Medico modificar(Medico medico) {
        return medicoDAO.save(medico);
    }

    @Override
    @Transactional
    public void eliminar(Medico medico) {
        medico.desactivar();
        medicoDAO.save(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> listarMedicos() {
        return medicoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> buscarPorId(Long id) {
        return medicoDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorNombre(String nombre) {
        return medicoDAO.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorCentroSaludLocalidad(String localidad) {
        return medicoDAO.findByCentroSaludDireccionLocalidadContaining(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorCentroSaludId(Long idCentro) {
        return medicoDAO.findByCentroSaludId(idCentro);
    }   
}
