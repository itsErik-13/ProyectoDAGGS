package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import es.uvigo.dagss.recetas.entidades.Paciente;

public interface PacienteService {
    public Paciente crear(Paciente paciente);

    public Paciente modificar(Paciente paciente);

    public void eliminar(Paciente paciente);

    public Paciente buscarPorId(Long id);

    public List<Paciente> listarPacientes();

    public List<Paciente> buscarPorNombre(String nombre);

    public List<Paciente> buscarPorLocalidad(String localidad);

    public List<Paciente> buscarPorCentroDeSaludId(Long idCentro);

    public List<Paciente> buscarPorMedicoId(Long idMedico);
}
