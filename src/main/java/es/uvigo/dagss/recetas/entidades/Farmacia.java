package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "FARMACIA")
public class Farmacia extends Usuario {

    private String nombreEstablecimiento;
    
    private String nombreFarmaceutico;

    private String apellidosFarmaceutico;

    private String dniOrNif;

    private String numeroColegiado;

    @Embedded
    private Direccion direccion;

    private int telefono;

    private String email;

    private Boolean activo = true;

	
    public Farmacia() {
        super(TipoUsuario.FARMACIA);
    }
    
    //Consideramos password el numero de colegiado como se indica
    public Farmacia(String login, String nombreEstablecimiento, String nombreFarmaceutico, String apellidosFarmaceutico, String dniOrNif, String numeroColegiado, Direccion direccion, int telefono, String email) {
        super(TipoUsuario.FARMACIA, login, numeroColegiado, email);
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.nombreFarmaceutico = nombreFarmaceutico;
        this.apellidosFarmaceutico = apellidosFarmaceutico;
        this.dniOrNif = dniOrNif;
        this.numeroColegiado = numeroColegiado;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Farmacia{" + "nombreEstablecimiento=" + nombreEstablecimiento + ", localidad=" + direccion.getLocalidad() + ", provincia=" + direccion.getProvincia() + ", activo=" + super.getActivo() + '}';    
    }
}
