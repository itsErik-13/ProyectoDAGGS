package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.CentroSaludDAO;
import es.uvigo.dagss.recetas.entidades.CentroSalud;

@Service
public class CentroSaludServiceImpl implements CentroSaludService {

    @Autowired
    private CentroSaludDAO centroSaludDAO;

    @Override
    @Transactional
    public CentroSalud crear(CentroSalud centro) {
        return centroSaludDAO.save(centro);
    }

    @Override
    @Transactional
    public CentroSalud modificar(CentroSalud centro) {
        return centroSaludDAO.save(centro);
    }

    @Override
    @Transactional
    public void eliminar(CentroSalud centro) {
        centro.desactivar();
        centroSaludDAO.save(centro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSalud> listarCentrosSalud() {
        return centroSaludDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSalud> buscarPorNombre(String nombre) {
        return centroSaludDAO.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSalud> buscarPorLocalidad(String localidad) {
        return centroSaludDAO.findByDireccionLocalidadContaining(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentroSalud> buscarPorId(Long id) {
        return centroSaludDAO.findById(id);
    }

    @Override
    public List<CentroSalud> buscarPorProvincia(String provincia) {
        return centroSaludDAO.findByDireccionProvinciaContaining(provincia);
    }
    
}
