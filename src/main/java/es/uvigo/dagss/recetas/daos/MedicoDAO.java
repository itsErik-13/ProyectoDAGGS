package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Medico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoDAO extends JpaRepository<Medico, Long> {

    public List<Medico> findByNombreContaining(String nombre);

    public List<Medico> findByCentroSaludDireccionLocalidadContaining(String localidad);

    public List<Medico> findByCentroSaludId(Long idCentro);
}
