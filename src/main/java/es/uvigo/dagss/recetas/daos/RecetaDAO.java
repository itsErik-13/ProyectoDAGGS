package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaDAO extends JpaRepository<Receta, Long> {

}
