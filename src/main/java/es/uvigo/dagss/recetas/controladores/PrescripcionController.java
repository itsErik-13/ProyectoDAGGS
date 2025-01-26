package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.controladores.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.servicios.PrescripcionService;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api/prescripciones", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class PrescripcionController {
    
    @Autowired
    private PrescripcionService prescripcionService;

    @Autowired
    private RecetaService recetaService;

    /**
     * HU-M3
     * CHECKED!!!
     * 
     * 
     * @param paciente paciente a buscar las prescripciones
     * @return prescripciones en vigor para el paciente pasado como parametro
     */
    @GetMapping(params = "paciente")
    public ResponseEntity<List<Prescripcion>> mostrarPrescripcionesEnVigor(@RequestParam("paciente") Long paciente) {

        List<Prescripcion> prescripciones = prescripcionService.buscarPrescripcionesEnVigor(paciente);

        if (prescripciones == null) {
            throw new ResourceNotFoundException("Cita no encontrada");
        }

        return new ResponseEntity<>(prescripciones, HttpStatus.OK);
    }


    /**
     * HU-A2
     * CHECKED!!
     * @param id de la prescripcion a elminiar
     * @return
     */
    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> anular(@PathVariable("id") Long id) {
		Optional<Prescripcion> prescripcion = prescripcionService.buscarPorId(id);

		if (prescripcion.isEmpty()) {
			throw new ResourceNotFoundException("Prescripcion no encontrado");
		} else {
			prescripcionService.eliminar(prescripcion.get());
            List<Receta> recetas =recetaService.buscarPorPrescripcionId(id);
            for (Receta receta : recetas) {
                recetaService.anular(receta);
            }
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    /**
     * HU-M4
     * CHECKED!! POST BODY: { "medicamento": { "id": 632, "nombreComercial": "Nolotil", "principioActivo": "Metamizol", "fabricante": "Boehringer", "familia": "Analgésico", "numDosis": 10, "activo": true }, "paciente": { "id": 725, "tipo": "PACIENTE", "login": "paciente1", "password": "73423455L", "fechaAlta": "2025-01-21T17:48:56.310+00:00", "ultimoAcceso": "2025-01-21T17:48:56.310+00:00", "activo": true, "email": "paciente1@recetas.com", "nombre": "Pepe", "apellidos": "Gómez Rodríguez", "dni": "73423455L", "numeroTarjetaSanitaria": "123456690AS", "numeroSeguridadSocial": "1234145235SDF", "direccion": { "domicilio": "Eulogio Gómez Franqueira", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 678134084, "fechaNacimiento": "1995-01-17", "centroSalud": { "id": 431, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": true }, "medico": { "id": 723, "tipo": "MEDICO", "login": "medico1", "password": "12345678", "fechaAlta": "2025-01-21T17:48:56.274+00:00", "ultimoAcceso": "2025-01-21T17:48:56.274+00:00", "activo": true, "email": "medico1@recetas.com", "nombre": "Jose", "apellidos": "Fernández González", "dni": "76735654H", "numeroColegiado": "12345678", "telefono": 619845763, "centroSalud": { "id": 431, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": true } } }, "medico": { "id": 723, "tipo": "MEDICO", "login": "medico1", "password": "12345678", "fechaAlta": "2025-01-21T17:48:56.274+00:00", "ultimoAcceso": "2025-01-21T17:48:56.274+00:00", "activo": true, "email": "medico1@recetas.com", "nombre": "Jose", "apellidos": "Fernández González", "dni": "76735654H", "numeroColegiado": "12345678", "telefono": 619845763, "centroSalud": { "id": 431, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": true } }, "dosisDiaria": 3.0, "indicaciones": "Prescripcion REST", "fechaFin": "2025-04-15" }
     * @param prescripcion
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prescripcion> crear(@Valid @RequestBody Prescripcion prescripcion) {
        Medicamento medicamento = prescripcion.getMedicamento();
        Paciente paciente = prescripcion.getPaciente();
        Medico medico = prescripcion.getMedico();
        Double dosisDiaria = prescripcion.getDosisDiaria();
        String indicaciones = prescripcion.getIndicaciones();
        Date fechaFin = prescripcion.getFechaFin();

        if(medicamento == null) {
            throw new WrongParameterException("Falta indicar medicamento");
        }
        if(paciente == null) {
            throw new WrongParameterException("Falta indicar paciente");
        }
        if(medico == null) {
            throw new WrongParameterException("Falta indicar medico");
        }
        if(dosisDiaria == null) {
            throw new WrongParameterException("Falta indicar dosis diaria");
        }
        if(indicaciones == null) {
            throw new WrongParameterException("Falta indicar indicaciones");
        }

        
        Prescripcion nuevaPrescripcion = new Prescripcion(medicamento, paciente, medico, dosisDiaria, indicaciones, fechaFin);
        nuevaPrescripcion = prescripcionService.crear(nuevaPrescripcion);
        URI uri = crearURIPrescripcion(nuevaPrescripcion);

        prescripcionService.generarPlanRecetas(nuevaPrescripcion);
        return ResponseEntity.created(uri).body(nuevaPrescripcion);
	}


    private URI crearURIPrescripcion(Prescripcion prescripcion) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(prescripcion.getId())
				.toUri();
	}

    
}
