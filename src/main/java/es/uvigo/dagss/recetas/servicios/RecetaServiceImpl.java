package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.RecetaDAO;
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
    public void servir(Receta receta) {
        receta.servir();
        recetaDAO.save(receta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receta> buscarPorPacienteId(Long idPaciente) {
        return recetaDAO.findByPacienteId(idPaciente);
    }

    
}
