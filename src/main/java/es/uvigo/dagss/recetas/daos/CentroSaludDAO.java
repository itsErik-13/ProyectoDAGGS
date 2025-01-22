package es.uvigo.dagss.recetas.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uvigo.dagss.recetas.entidades.CentroSalud;

public interface CentroSaludDAO extends JpaRepository<CentroSalud, Long> {
    public List<CentroSalud> findByNombreContaining(String nombre);

    public List<CentroSalud> findByDireccionLocalidadContaining(String localidad);

    public List<CentroSalud> findByDireccionProvinciaContaining(String provincia);
}
