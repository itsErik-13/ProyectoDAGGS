package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class CentroSalud implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Embedded
    private Direccion direccion;

    private int telefono;

    private String email;

    private Boolean activo = true;

    public CentroSalud(String nombre, Direccion direccion, int telefono, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }


    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public String toString() {
        return "CentroSalud{" + "nombre=" + nombre + ", localidad=" + direccion.getLocalidad() + ", provincia=" + direccion.getProvincia() + ", activo=" + activo + '}';
    }
}