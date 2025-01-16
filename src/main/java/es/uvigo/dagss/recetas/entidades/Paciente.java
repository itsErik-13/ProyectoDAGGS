package es.uvigo.dagss.recetas.entidades;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

	/*
     * 
     * nombre
apellidos
dni
número de tarjeta sanitaria (como String)
número de la Seguridad Social (como String)
dirección (domicilio, localidad, código postal, provincia)
teléfono
e-mail
fecha de naciemiento
centro de salud que tiene asignado
médico que tiene asignado (que a su vez debe estar vinculado al centro de salud anterior)
activo [true|false]
     */

    private String nombre;

    private String apellidos;

    private String dni;

    private String numeroTarjetaSanitaria;

    private String numeroSeguridadSocial;

    @Embedded
    private Direccion direccion;

    private int telefono;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @ManyToOne
    private CentroSalud centroSalud;

    @ManyToOne
    private Medico medico;
   

    public Paciente() {
        super(TipoUsuario.PACIENTE);        
    }

    public Paciente(String login, String nombre, String apellidos, String dni, String numeroTarjetaSanitaria, String numeroSeguridadSocial, Direccion direccion, int telefono, Date fechaNacimiento, CentroSalud centroSalud, String email) {
        super(TipoUsuario.PACIENTE, login, dni, email);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.centroSalud = centroSalud;
    }

}
