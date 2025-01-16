package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescripcionDAO extends JpaRepository<Prescripcion, Long> {

}
