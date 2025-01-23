package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Receta  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Prescripcion prescripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaValidezInicial;

    @Temporal(TemporalType.DATE)
    private Date fechaValidezFinal;


    private int numCajas;

    private EstadoReceta estado = EstadoReceta.PLANIFICADA;

    @ManyToOne
    private Farmacia farmacia = null;

    public Receta(Prescripcion prescripcion, Date fechaValidezInicial, Date fechaValidezFinal, int numCajas) {
        this.prescripcion = prescripcion;
        this.fechaValidezInicial = fechaValidezInicial;
        this.fechaValidezFinal = fechaValidezFinal;
        this.numCajas = numCajas;
    }

    
    public void planificar() {
        this.estado = EstadoReceta.PLANIFICADA;
    }

    public void anular() {
        this.estado = EstadoReceta.ANULADA;
    }

    public void servir() {
        this.estado = EstadoReceta.SERVIDA;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }
}
