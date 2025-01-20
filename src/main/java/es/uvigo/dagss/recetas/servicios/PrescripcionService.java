package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Prescripcion;

public interface PrescripcionService { 
    public Prescripcion crear(Prescripcion prescripcion);
	
    public Prescripcion modificar(Prescripcion prescripcion);
	
    public void eliminar(Prescripcion prescripcion);

    public Optional<Prescripcion> buscarPorId(Long id);

    public List<Prescripcion> buscarPorPacienteId(Long id);

    public List<Prescripcion> buscarPrescripcionesEnVigor(Long paciente);
}
