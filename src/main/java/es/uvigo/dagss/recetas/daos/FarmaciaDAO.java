package es.uvigo.dagss.recetas.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uvigo.dagss.recetas.entidades.Farmacia;

public interface FarmaciaDAO extends JpaRepository<Farmacia, Long> {
    
    public List<Farmacia> findByNombreEstablecimientoContaining(String nombreEstablecimiento);

    public List<Farmacia> findByDireccionLocalidadContaining(String localidad);
    
}
