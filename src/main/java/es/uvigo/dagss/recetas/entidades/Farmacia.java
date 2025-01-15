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
@DiscriminatorValue(value = "FARMACIA")
public class Farmacia extends Usuario {

    private String nombreEstablecimiento;
    
    private String nombreFarmaceutico;

    private String apellidosFarmaceutico;

    private String dniOrNif;

    private String numeroColegiado;

	
    public Farmacia() {
        super(TipoUsuario.FARMACIA);
    }
    
    //Consideramos password el numero de colegiado como se indica
    public Farmacia(String login, String nombreEstablecimiento, String nombreFarmaceutico, String apellidosFarmaceutico, String dniOrNif, String numeroColegiado, String email) {
        super(TipoUsuario.FARMACIA, login, numeroColegiado, email);
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.nombreFarmaceutico = nombreFarmaceutico;
        this.apellidosFarmaceutico = apellidosFarmaceutico;
        this.dniOrNif = dniOrNif;
        this.numeroColegiado = numeroColegiado;
    }
}
