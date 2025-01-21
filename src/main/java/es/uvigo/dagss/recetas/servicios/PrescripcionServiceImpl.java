package es.uvigo.dagss.recetas.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.dagss.recetas.daos.PrescripcionDAO;
import es.uvigo.dagss.recetas.daos.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;

@Service
public class PrescripcionServiceImpl implements PrescripcionService {
    
    @Autowired
    private PrescripcionDAO prescripcionDAO;

    @Autowired
    private RecetaDAO recetaDAO;

    @Override
    @Transactional
    public Prescripcion crear(Prescripcion prescripcion) {
        return prescripcionDAO.save(prescripcion);
    }

    @Override
    @Transactional
    public Prescripcion modificar(Prescripcion prescripcion) {
        return prescripcionDAO.save(prescripcion);
    }

    @Override
    @Transactional
    public void eliminar(Prescripcion prescripcion) {
        prescripcion.desactivar();
        prescripcionDAO.save(prescripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> buscarPorPacienteId(Long id) {
        return prescripcionDAO.findByPacienteId(id);
    }

    @Override
    public List<Prescripcion> buscarPrescripcionesEnVigor(Long paciente) {
        Date dateNow = new Date();
        return prescripcionDAO.findByPacienteIdAndFechaFinGreaterThanEqualOrderByFechaFinAsc(paciente, dateNow);
    }

    @Override
    public Optional<Prescripcion> buscarPorId(Long id) {
        return prescripcionDAO.findById(id);
    }


    /**
     * Automaticamente cuando se crea una prescripcion se crean los planes de recetas, se añade una receta calculando el numero de dosis de cada medicamento en función de la prescripción y ajustando las fechas de validez
     * con una semana de margen salvo en la primera receta y cuando la fecha inicial es anterior a la actual.
     */
    @Override
    public void generarPlanRecetas(Prescripcion prescripcion) {
        Medicamento medicamento = prescripcion.getMedicamento();
        Double dosisDiaria = prescripcion.getDosisDiaria();

        int diasPorCaja = medicamento.getNumDosis() / dosisDiaria.intValue();

        Date fechaInicio = prescripcion.getFechaInicio();
        Date fechaFin = prescripcion.getFechaFin();

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicio);

        boolean primeraReceta = true;

        while (calendario.getTime().before(fechaFin) || calendario.getTime().equals(fechaFin)) {
            // Fecha exacta para recoger la receta
            Date fechaExacta = calendario.getTime();

            // Fechas de validez inicio y fin
            Date fechaValidezInicio;
            Date fechaValidezFin;

            if (primeraReceta) {
                fechaValidezInicio = fechaInicio;
                calendario.add(Calendar.DATE, 7); // Una semana después
                fechaValidezFin = calendario.getTime();
                primeraReceta = false;
            } else {
                // Recetas subsiguientes: Margen semanal antes y después
                calendario.add(Calendar.DATE, -7); // Una semana antes
                fechaValidezInicio = calendario.getTime();
                if(fechaValidezInicio.before(fechaInicio)) {
                    fechaValidezInicio = fechaInicio;
                }

                calendario.setTime(fechaExacta);
                calendario.add(Calendar.DATE, 7); // Una semana después
                fechaValidezFin = calendario.getTime();
            }

            // Crear la receta
            Receta receta = new Receta(prescripcion, fechaValidezInicio, fechaValidezFin, 1);

            // Guardar la receta
            recetaDAO.save(receta);

            // Avanzar a la próxima fecha exacta
            calendario.setTime(fechaExacta);
            calendario.add(Calendar.DATE, diasPorCaja); // Avanzar días por caja
        }
    }
    
}
