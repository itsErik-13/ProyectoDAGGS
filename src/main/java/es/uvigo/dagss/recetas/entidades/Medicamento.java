package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Medicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreComercial;

    private String principioActivo;

    private String fabricante;

    private String familia;

    private int numDosis;

    private Boolean activo = true;

    public Medicamento(String nombreComercial, String principioActivo, String fabricante, String familia, int numDosis) {
        this.nombreComercial = nombreComercial;
        this.principioActivo = principioActivo;
        this.fabricante = fabricante;
        this.familia = familia;
        this.numDosis = numDosis;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    @Override
    public String toString() {
        return "Medicamento{" + "nombreComercial=" + nombreComercial + ", principioActivo=" + principioActivo + ", fabricante=" + fabricante + ", familia=" + familia + '}';
    }
}
