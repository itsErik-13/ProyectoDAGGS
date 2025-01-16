package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "MEDICO")
public class Medico extends Usuario {


    private String nombre;

    private String apellidos;

    private String dni;

    private String numeroColegiado;

    private int telefono;

    @ManyToOne
    private CentroSalud centroSalud;

    
    public Medico() {
        super(TipoUsuario.MEDICO);
    }


    public Medico(String login, String nombre, String apellidos, String dni, String numeroColegiado, int telefono, CentroSalud centroSalud, String email) {
        super(TipoUsuario.MEDICO, login, numeroColegiado, email);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.numeroColegiado = numeroColegiado;
        this.telefono = telefono;
        this.centroSalud = centroSalud;
    }

    @Override
    public String toString() {
        return "Medico{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", centroSalud=" + centroSalud.getNombre() + ", localidad=" + centroSalud.getDireccion().getLocalidad() + ", provincia=" + centroSalud.getDireccion().getProvincia() +", activo=" + super.getActivo() + '}';
    }

}
