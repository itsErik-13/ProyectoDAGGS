package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import es.uvigo.dagss.recetas.entidades.Medicamento;

public interface MedicamentoService {
    public Medicamento crear(Medicamento medicamento);

    public Medicamento modificar(Medicamento medicamento);

    public void eliminar(Long id);

    public Medicamento buscarPorId(Long id);

    public List<Medicamento> listarMedicamentos();

    public List<Medicamento> buscarPorNombreComercial();

    public List<Medicamento> buscarPorPrincipioActivo();

    public List<Medicamento> buscarPorFabricante();

    public List<Medicamento> buscarPorFamilia();
}
