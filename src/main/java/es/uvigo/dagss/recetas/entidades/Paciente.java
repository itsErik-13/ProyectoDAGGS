package es.uvigo.dagss.recetas.entidades;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

    private String nombre;

    private String apellidos;

    private String dni;

    private String numeroTarjetaSanitaria;

    private String numeroSeguridadSocial;

    @Embedded
    private Direccion direccion;

    private Integer telefono;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @ManyToOne
    private CentroSalud centroSalud;

    @ManyToOne
    private Medico medico;
   

    public Paciente() {
        super(TipoUsuario.PACIENTE);        
    }

    public Paciente(String login, String nombre, String apellidos, String dni, String numeroTarjetaSanitaria, String numeroSeguridadSocial, Direccion direccion, Integer telefono, Date fechaNacimiento, CentroSalud centroSalud, Medico medico, String email) {
        super(TipoUsuario.PACIENTE, login, dni, email);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.medico = medico;
        this.centroSalud = centroSalud;
    }

    @Override
    public String toString() {
        // (nombre y apelidos, centro de salud, localidad, provincia, activo [true|false]).
        return "Paciente{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", centroSalud=" + centroSalud + ", localidad=" + direccion.getLocalidad() + ", provincia=" + direccion.getProvincia() +", activo=" + super.getActivo() + '}';
    }

}
