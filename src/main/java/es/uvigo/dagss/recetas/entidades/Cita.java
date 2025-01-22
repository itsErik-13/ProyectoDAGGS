package es.uvigo.dagss.recetas.entidades;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Cita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Medico medico;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date hora;


    private Integer duracion = 15; // en minutos

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PLANIFICADA; // Considero que cuando se crea una cita se marca como
                                                        // planificada

    public Cita(Paciente paciente, Medico medico, Date fecha, Date hora) {
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cita(Paciente paciente, Medico medico, Date fecha, Date hora, Integer duracionMin) {
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracionMin;
    }

    public void planificar() {
        this.estado = EstadoCita.PLANIFICADA;
    }

    public void anular() {
        this.estado = EstadoCita.ANULADA;
    }

    public void completar() {
        this.estado = EstadoCita.COMPLETADA;
    }

    public void ausente() {
        this.estado = EstadoCita.AUSENTE;
    }

    @Override
    public String toString() {
        return "Cita{" + "paciente=" + paciente + ", medico=" + medico + ", centro de salud=" + medico.getCentroSalud()
                + ", fecha y hora=" + fecha + " " + hora + ", estado=" + estado + '}';
    }
}
