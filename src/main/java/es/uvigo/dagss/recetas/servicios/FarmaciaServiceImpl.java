package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.FarmaciaDAO;
import es.uvigo.dagss.recetas.entidades.Farmacia;

@Service
public class FarmaciaServiceImpl implements FarmaciaService {
    
    @Autowired
    private FarmaciaDAO farmaciaDAO;

    @Override
    @Transactional
    public Farmacia crear(Farmacia farmacia) {
        return farmaciaDAO.save(farmacia);
    }

    @Override
    @Transactional
    public Farmacia modificar(Farmacia farmacia) {
        return farmaciaDAO.save(farmacia);
    }

    @Override
    @Transactional
    public void eliminar(Farmacia farmacia) {
        farmacia.desactivar();
        farmaciaDAO.save(farmacia);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Farmacia> buscarPorId(Long id) {
        return farmaciaDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> listarFarmacias() {
        return farmaciaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> buscarPorLocalidad(String localidad) {
        return farmaciaDAO.findByDireccionLocalidadContaining(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> buscarPorNombreEstablecimiento(String nombreEstablecimiento) {
        return farmaciaDAO.findByNombreEstablecimientoContaining(nombreEstablecimiento);
    }
}
