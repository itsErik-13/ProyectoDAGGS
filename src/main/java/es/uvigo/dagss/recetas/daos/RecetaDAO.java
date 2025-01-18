package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Receta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaDAO extends JpaRepository<Receta, Long> {
    public List<Receta> findByPrescripcionPacienteId(Long idPaciente);
}
