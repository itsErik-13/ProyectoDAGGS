package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Receta;

public interface RecetaService {
    public Receta crear(Receta receta);
	public Receta modificar(Receta receta);
	public void anular(Receta receta);
}