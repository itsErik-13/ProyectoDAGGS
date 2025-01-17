package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import es.uvigo.dagss.recetas.entidades.Farmacia;

public interface FarmaciaService {
    public Farmacia crear(Farmacia farmacia);

    public Farmacia modificar(Farmacia farmacia);

    public void eliminar(Farmacia farmacia);

    public Farmacia buscarPorId(Long id);

    public List<Farmacia> listarFarmacias();

    public List<Farmacia> buscarPorLocalidad(String localidad);

    public List<Farmacia> buscarPorNombreEstablecimiento(String nombreEstablecimiento);
}
