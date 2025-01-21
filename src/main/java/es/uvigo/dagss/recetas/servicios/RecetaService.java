package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;

public interface RecetaService {
    public Receta crear(Receta receta);

	public Receta modificar(Receta receta);
    
	public void anular(Receta receta);

	public void servir(Receta receta, Farmacia farmacia);

	public Optional<Receta> buscarPorId(Long id);

	public List<Receta> buscarPorPacienteId(Long idPaciente);

    public List<Receta> buscarPorPrescripcionId(Long id);
}