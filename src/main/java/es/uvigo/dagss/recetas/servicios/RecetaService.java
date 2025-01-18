package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import es.uvigo.dagss.recetas.entidades.Receta;

public interface RecetaService {
    public Receta crear(Receta receta);

	public Receta modificar(Receta receta);
    
	public void anular(Receta receta);

	public void servir(Receta receta);

	public List<Receta> buscarPorPacienteId(Long idPaciente);
}