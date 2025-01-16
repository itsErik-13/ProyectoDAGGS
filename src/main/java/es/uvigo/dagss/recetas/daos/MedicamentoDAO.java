package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoDAO extends JpaRepository<Medicamento, Long> {

}
