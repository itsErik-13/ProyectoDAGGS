package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.CentroSalud;

public interface CentroSaludService {
    public CentroSalud crear(CentroSalud centro);

    public CentroSalud modificar(CentroSalud centro);

    public void eliminar(CentroSalud centro);

    public List<CentroSalud> listarCentrosSalud();

    public List<CentroSalud> buscarPorNombre(String nombre);

    public List<CentroSalud> buscarPorLocalidad(String localidad);

    public List<CentroSalud> buscarPorProvincia(String provincia);

    public Optional<CentroSalud> buscarPorId(Long id);
}
