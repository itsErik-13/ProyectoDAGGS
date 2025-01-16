package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;


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
public class Prescripcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Medicamento medicamento;
    
    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Medico medico;

    private Double dosisDiaria;

    private String indicaciones;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio = new Date(Calendar.getInstance().getTime().getTime());

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    private Boolean activa = true;

    public Prescripcion(Medicamento medicamento, Paciente paciente, Medico medico, double dosisDiaria, String indicaciones, Date fechaFin) {
        this.medicamento = medicamento;
        this.paciente = paciente;
        this.medico = medico;
        this.dosisDiaria = dosisDiaria;
        this.indicaciones = indicaciones;
        this.fechaFin = fechaFin;
    }


    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }

}
