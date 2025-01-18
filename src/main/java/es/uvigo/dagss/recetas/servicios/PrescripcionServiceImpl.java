package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.PrescripcionDAO;
import es.uvigo.dagss.recetas.entidades.Prescripcion;

@Service
public class PrescripcionServiceImpl implements PrescripcionService {
    
    @Autowired
    private PrescripcionDAO prescripcionDAO;

    @Override
    @Transactional
    public Prescripcion crear(Prescripcion prescripcion) {
        return prescripcionDAO.save(prescripcion);
    }

    @Override
    @Transactional
    public Prescripcion modificar(Prescripcion prescripcion) {
        return prescripcionDAO.save(prescripcion);
    }

    @Override
    @Transactional
    public void eliminar(Prescripcion prescripcion) {
        prescripcionDAO.delete(prescripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> buscarPorPacienteId(Long id) {
        return prescripcionDAO.findByPacienteId(id);
    }
    
}
