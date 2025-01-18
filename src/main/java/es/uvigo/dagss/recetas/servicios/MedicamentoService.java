package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.dagss.recetas.entidades.Medicamento;

public interface MedicamentoService {
    public Medicamento crear(Medicamento medicamento);

    public Medicamento modificar(Medicamento medicamento);

    public void eliminar(Medicamento medicamento);

    public Optional<Medicamento> buscarPorId(Long id);

    public List<Medicamento> listarMedicamentos();

    public List<Medicamento> buscarPorNombreComercial(String nombreComercial);

    public List<Medicamento> buscarPorPrincipioActivo(String principioActivo);

    public List<Medicamento> buscarPorFabricante(String fabricante);

    public List<Medicamento> buscarPorFamilia(String familia);
}
