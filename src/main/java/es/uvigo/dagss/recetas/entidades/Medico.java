package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "MEDICO")
public class Medico extends Usuario {


    private String nombre;

    private String apellidos;

    private String dni;

    private String numeroColegiado;

    private int telefono;

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

}
