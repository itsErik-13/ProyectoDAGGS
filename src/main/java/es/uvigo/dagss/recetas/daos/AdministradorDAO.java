package es.uvigo.dagss.recetas.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uvigo.dagss.recetas.entidades.Administrador;

public interface AdministradorDAO extends JpaRepository<Administrador, Long> { 
    
}
