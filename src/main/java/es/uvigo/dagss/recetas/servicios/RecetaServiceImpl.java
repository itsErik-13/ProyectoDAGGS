package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;

@Service
public class RecetaServiceImpl implements RecetaService {
    
    @Autowired
    private RecetaDAO recetaDAO;

    @Override
    @Transactional
    public Receta crear(Receta receta) {
        return recetaDAO.save(receta);
    }

    @Override
    @Transactional
    public Receta modificar(Receta receta) {
        return recetaDAO.save(receta);
    }

    @Override
    @Transactional
    public void anular(Receta receta) {
        receta.anular();
        recetaDAO.save(receta);
    }

    @Override
    @Transactional
    public void servir(Receta receta, Farmacia farmacia) {
        receta.servir();
        receta.setFarmacia(farmacia);
        recetaDAO.save(receta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receta> buscarPorPacienteId(Long idPaciente) {
        return recetaDAO.findByPrescripcionPacienteIdAndEstado(idPaciente, EstadoReceta.PLANIFICADA);
    }

    @Override
    public List<Receta> buscarPorPrescripcionId(Long id) {
        return recetaDAO.findByPrescripcionId(id);
    }

    @Override
    public Optional<Receta> buscarPorId(Long id) {
        return recetaDAO.findById(id);
    }

    
}
