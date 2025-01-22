package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.controladores.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.EstadoCita;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/citas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CitaController {
    @Autowired
    private CitaService citaService;

    @Autowired 
    private MedicoService medicoService;

    /**
     * HU-A7
     * CHECKED!!!
     *
     * @return Lista de citas
     */
    @GetMapping()
    public ResponseEntity<List<Cita>> listarCitasOrdenadasPorFechaYHora() {
        List<Cita> citas = citaService.listarCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    /**
     * HU-A7 HU-M2
     * CHECKED!!!
     * 
     * 
     * @param fecha de la cita a buscar
     * @return cita con la fecha dada
     */
    @GetMapping(params = "fecha")
    public ResponseEntity<List<Cita>> buscarCitasPorFecha(
            @RequestParam(name = "fecha", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha,
            @RequestParam(name = "medico", required = false) Long medico,
            @RequestParam(name = "paciente", required = false) Long paciente) {

        List<Cita> citas = citaService.buscarPorFecha(fecha);

        if (medico != null) {
            citas = citaService.buscarPorFechaAndMedicoIdOrderByFechaAscHoraAsc(fecha, medico);
        }

        if (paciente != null) {
            citas = citaService.buscarPorFechaAndPacienteIdOrderByFechaAscHoraAsc(fecha, paciente);
        }

        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    /**
     * HU-M3
     * CHECKED!!!
     * 
     * 
     * @param id de la cita a buscar
     * @return paciente de la cita con el id dado, en front se seleccionarán los datos
     */
    @GetMapping(path = "{id}/paciente")
    public ResponseEntity<Paciente> mostrarInformacionPacienteCita(@PathVariable("id") Long id) {

        Optional<Cita> cita = citaService.buscarPorId(id);

        if (cita == null) {
            throw new ResourceNotFoundException("Cita no encontrada");
        }

        return new ResponseEntity<>(cita.get().getPaciente(), HttpStatus.OK);
    }

    /**
     * HU-P3
     * CHECKED!!!
     * 
     * 
     * 
     * @param fecha en la que se quiere buscar la disponibilidad
     * @param medico con el que se quiere buscar la disponibilidad
     * @return lista de horarios disponibles
     */
    @GetMapping(path ="huecos")
    public ResponseEntity<List<LocalTime>> obtenerHuecosDisponibles(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha,
            @RequestParam("medico") Long medico) {

        if(medicoService.buscarPorId(medico).isEmpty()) {
            throw new ResourceNotFoundException("Medico no encontrado");
        }

        List<LocalTime> huecosDisponibles = citaService.obtenerHuecosDisponibles(fecha, medico);
        return new ResponseEntity<>(huecosDisponibles, HttpStatus.OK);
    }

    /**
     * HU-A7, HU-M2
     * CHECKED!!!
     * 
     * 
     * PARA ADMINISTRADOR SE HARA UNA LLAMADA TAL QUE DELETE /api/citas/{id}?rol=admin que pondrá la cita en anulada
     * PARA MEDICO SE HARA UNA LLAMADA TAL QUE DELETE /api/citas/{id}?rol=medico que pondra la cita en ausente
     * 
     * @param id de la cita a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}", params = "rol")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id,
            @RequestParam(name = "rol") String rol) {
        Optional<Cita> cita = citaService.buscarPorId(id);

        if (cita.isEmpty()) {
            throw new ResourceNotFoundException("Cita no encontrada");
        }
        if (rol.equals("admin")) {
            citaService.anular(cita.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (rol.equals("medico")) {
            if (cita.get().getEstado() == EstadoCita.PLANIFICADA) {
                citaService.marcarAusente(cita.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                throw new WrongParameterException("Cita no planificada");
            }
        } else {
            throw new WrongParameterException("Rol incorrecto");
        }
    }


    /**
     * HU-P3
     * CHECKED!!! con post body: { "paciente": { "id": 844, "tipo": "PACIENTE", "login": "paciente3", "password": "73423457L", "fechaAlta": "2025-01-22T17:53:13.602+00:00", "ultimoAcceso": "2025-01-22T17:53:13.602+00:00", "activo": true, "email": "paciente3@recetas.com", "nombre": "Luis", "apellidos": "Fernández Díaz", "dni": "73423457L", "numeroTarjetaSanitaria": "123456790AS", "numeroSeguridadSocial": "1234145237SDF", "direccion": { "domicilio": "San Rosendo", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 678134086, "fechaNacimiento": "1995-03-25", "centroSalud": { "id": 470, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": true }, "medico": { "id": 840, "tipo": "MEDICO", "login": "medico1", "password": "12345678", "fechaAlta": "2025-01-22T17:53:13.571+00:00", "ultimoAcceso": "2025-01-22T17:53:13.572+00:00", "activo": true, "email": "medico1@recetas.com", "nombre": "Jose", "apellidos": "Fernández González", "dni": "76735654H", "numeroColegiado": "12345678", "telefono": 619845763, "centroSalud": { "id": 470, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": true } } }, "medico": { "id": 840, "tipo": "MEDICO", "login": "medico1", "password": "12345678", "fechaAlta": "2025-01-22T17:53:13.571+00:00", "ultimoAcceso": "2025-01-22T17:53:13.572+00:00", "activo": true, "email": "medico1@recetas.com", "nombre": "Jose", "apellidos": "Fernández González", "dni": "76735654H", "numeroColegiado": "12345678", "telefono": 619845763, "centroSalud": { "id": 470, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": true } }, "fecha": "2025-01-18", "hora": "2025-01-18T13:15:00+01:00", "estado": "PLANIFICADA" }
     * 
     * 
     * IMPORTANTE QUE LA HORA SE CREE DE FORMA YYYY-MM-DD'T'HH:mm:ss+01:00
     * 
     * @param cita a crear
     * @return medicamento creado
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cita> crear(@Valid @RequestBody Cita cita) {
        Paciente paciente = cita.getPaciente();
        Medico medico = cita.getMedico();
        Date fecha = cita.getFecha();
        Date hora = cita.getHora();


        if (paciente == null) {
            throw new WrongParameterException("Falta indicar paciente de la cita");
        }
        if (medico == null) {
            throw new WrongParameterException("Falta indicar medico de la cita");
        }
        if (fecha == null) {
            throw new WrongParameterException("Falta indicar fecha de la cita");
        }
        if (hora == null) {
            throw new WrongParameterException("Falta indicar hora de la cita");
        }
        Cita nuevaCita = new Cita();
        if(cita.getDuracion() != null) {
            nuevaCita = new Cita(paciente, medico, fecha, hora, cita.getDuracion());
        } else {    
            nuevaCita = new Cita(paciente, medico, fecha, hora);
        }
        
        nuevaCita = citaService.crear(nuevaCita);
        URI uri = crearURICita(nuevaCita);
        return ResponseEntity.created(uri).body(nuevaCita);
    }


    private URI crearURICita(Cita cita) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cita.getId())
                .toUri();
    }

}
