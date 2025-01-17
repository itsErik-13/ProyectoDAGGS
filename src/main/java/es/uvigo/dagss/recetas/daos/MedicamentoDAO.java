package es.uvigo.dagss.recetas.daos;

import es.uvigo.dagss.recetas.entidades.Medicamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoDAO extends JpaRepository<Medicamento, Long> {
    public List<Medicamento> findByNombreComercialContaining(String nombreComercial);

    public List<Medicamento> findByPrincipioActivoContaining(String principioActivo);
    
    public List<Medicamento> findByFabricanteContaining(String fabricante);

    public List<Medicamento> findByFamiliaContaining(String familia);
}
