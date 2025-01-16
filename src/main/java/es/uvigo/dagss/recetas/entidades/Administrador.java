package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "ADMINISTRADOR")
public class Administrador extends Usuario {

    private String nombre;
	
    public Administrador() {
        super(TipoUsuario.ADMINISTRADOR); 
    }

    public Administrador(String login, String password, String nombre, String email) {
        super(TipoUsuario.ADMINISTRADOR, login, password, email);
        this.nombre = nombre;
    }

    //Se mostrará una lista con los administradores actualmente registrados, indicando su datos esenciales (login, nombre del usuario, email, fecha de registro/creación, fecha de último acceso, activo [true|false])
    @Override
    public String toString() {
        return "Administrador{" + "login=" + super.getLogin() + ", nombre=" + nombre + ", email=" + email + ", fechaAlta=" + super.getFechaAlta() + ", activo=" + super.getActivo() + '}';
    }

}
