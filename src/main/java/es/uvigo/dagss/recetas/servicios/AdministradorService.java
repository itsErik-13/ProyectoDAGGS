package es.uvigo.dagss.recetas.servicios;

import java.util.List;

import es.uvigo.dagss.recetas.entidades.Administrador;

public interface AdministradorService {

    public Administrador crear(Administrador administrador);

    public Administrador modificar(Administrador administrador);

    public void eliminar(Administrador administrador);

    public Administrador buscarPorId(Long id);

    public List<Administrador> listarAdministradores();
}
