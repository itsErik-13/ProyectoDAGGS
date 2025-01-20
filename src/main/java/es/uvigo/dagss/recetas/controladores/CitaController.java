package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
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
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.servicios.CitaService;

@RestController
@RequestMapping(path = "/api/citas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CitaController {
    @Autowired
    private CitaService citaService;

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

    private URI crearURICita(Cita cita) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(cita.getId())
                .toUri();
    }

}
