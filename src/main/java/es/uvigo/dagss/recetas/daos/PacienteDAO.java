package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Paciente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteDAO extends JpaRepository<Paciente, Long> {

    public List<Paciente> findByNombreContaining(String nombre);

    public List<Paciente> findByDireccionLocalidadContaining(String localidad);

    public List<Paciente> findByCentroSaludId(Long idCentro);

    public List<Paciente> findByMedicoId(Long idMedico);
}
