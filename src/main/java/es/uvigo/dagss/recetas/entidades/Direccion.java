package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
@Embeddable
public class Direccion implements Serializable {

    private String domicilio;

    private String localidad;

    private String codigoPostal;

    private String provincia;

}
