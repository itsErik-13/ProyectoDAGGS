package es.uvigo.dagss.recetas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.MedicamentoDAO;
import es.uvigo.dagss.recetas.entidades.Medicamento;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {
    @Autowired
    private MedicamentoDAO medicamentoDAO;

    @Override
    @Transactional
    public Medicamento crear(Medicamento medicamento) {
        return medicamentoDAO.save(medicamento);
    }

    @Override
    @Transactional
    public Medicamento modificar(Medicamento medicamento) {
        return medicamentoDAO.save(medicamento);
    }

    @Override
    @Transactional
    public void eliminar(Medicamento medicamento) {
        medicamento.desactivar();
        medicamentoDAO.save(medicamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> listarMedicamentos() {
        return medicamentoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorNombreComercial(String nombreComercial) {
        return medicamentoDAO.findByNombreComercialContaining(nombreComercial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorPrincipioActivo(String principioActivo) {
        return medicamentoDAO.findByPrincipioActivoContaining(principioActivo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorFabricante(String fabricante) {
        return medicamentoDAO.findByFabricanteContaining(fabricante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorFamilia(String familia) {
        return medicamentoDAO.findByFamiliaContaining(familia);
    }
}
