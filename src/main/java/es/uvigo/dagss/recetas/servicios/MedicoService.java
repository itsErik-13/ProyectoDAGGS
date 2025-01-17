package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import es.uvigo.dagss.recetas.entidades.Medico;

public interface MedicoService {
    public Medico crear(Medico medico);

    public Medico modificar(Medico medico);

    public void eliminar(Medico medico);

    public List<Medico> listarMedicos();

    public Medico buscarPorId(Long id);

    public List<Medico> buscarPorNombre(String nombre);

    public List<Medico> buscarPorCentroDeSaludLocalidad(String localidad);
    
    public List<Medico> buscarPorCentroDeSaludId(Long idCentro);
}
