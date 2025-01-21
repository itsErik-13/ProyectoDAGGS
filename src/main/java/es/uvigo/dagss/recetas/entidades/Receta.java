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
    /*
     * prescripción a la que pertenece la receta
     * fecha de validez inicial, a partir de la cúal el paciente puede pasar por una
     * farmacia a recojer "cajas" del medicamento
     * fecha de validez final, pasada la cuál no será posible recoger "cajas" del
     * medicamento
     * número de unidades del medicamento (= "cajas") a servir por parte de la
     * farmacia donde se presente la receta
     * estado de la receta [PLANIFICADA, SERVIDA, ANULADA], inicialmente con valor
     * PLANIFICADA
     * farmacia que sirvió la receta, inicialmente tendrá valor NULL (se establecerá
     * la relación cuando se se haga efectiva la entrega de las "cajas")
     */
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
