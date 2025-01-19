package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.controladores.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/paciente", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    /**
     * HU-A5
     * CHECKED!!
     * @return Lista los pacientes
     */
    @GetMapping()
	public ResponseEntity<List<Paciente>> listarPacientes() {
		List<Paciente> resultado = new ArrayList<>();
		resultado = pacienteService.listarPacientes();
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A5
     * CHECHKED!!
     * @param id del paciente a buscar
     * @return Paciente con el id dado
     */
    @GetMapping(path = "{id}")
	public ResponseEntity<Medico> buscarPorId(@PathVariable("id") Long id) {
		Optional<Medico> paciente = pacienteService.buscarPorId(id);

		if (paciente.isEmpty()) {
			throw new ResourceNotFoundException("Paciente no encontrado");
		} else {
			return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
		}

	}

    /**
     * HU-A5
     * CHECKED!!
     * @param id del paciente a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
		Optional<Paciente> paciente = pacienteService.buscarPorId(id);

		if (paciente.isEmpty()) {
			throw new ResourceNotFoundException("Paciente no encontrado");
		} else {
			pacienteService.eliminar(paciente.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    /**
     * HU-A5
     * Sólo se updatea correctamente si se añade un id en el body y todos los datos necesarios, en otro caso se crea una nueva entidad, PREGUNTAR
     * @param id del paciente a modificar
     * @param administrador el conjunto de pacientes modificado
     * @return la instancia modificada
     */
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Paciente> modificar(@PathVariable("id") Long id, @Valid @RequestBody Paciente paciente) {
		Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(id);

		if (pacienteOptional.isEmpty()) {
			throw new ResourceNotFoundException("Paciente no encontrado");
		} else {
            paciente.setId(pacienteOptional.get().getId());
			Paciente nuevoPaciente = pacienteService.modificar(paciente);
			return new ResponseEntity<>(nuevoPaciente, HttpStatus.OK);
		}
	}

    /**
     * HU-A4
     * CHECKED!!
     * @param nombre aproximado del paciente/s de salud a buscar
     * @return Pacientes con el nombre aproxiamdo dado
     */
    @GetMapping(params = "nombre")
	public ResponseEntity<List<Paciente>> buscarPorNombre(
			@RequestParam(name = "nombre", required = true) String nombre) {
		List<Paciente> resultado = new ArrayList<>();
		resultado = pacienteService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A5
     * CHECKED!!
     * @param localidad aproximada del medico/s de salud a buscar
     * @return Medicos con la localidad aproximada dada
     */
    @GetMapping(params = "localidad")
	public ResponseEntity<List<Paciente>> buscarPorLocalidad(
			@RequestParam(name = "localidad", required = true) String localidad) {
		List<Medico> resultado = new ArrayList<>();
		resultado = pacienteService.buscarPorLocalidad(localidad);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

     /**
     * HU-A5
     * CHECKED!!
     * @param idCentro aproximada del medico/s de salud a buscar
     * @return Medicos con la localidad aproximada dada
     */
    @GetMapping(params = "idCentro")
	public ResponseEntity<List<Paciente>> buscarPorCentroSaludId(
			@RequestParam(name = "idCentro", required = true) Long idCentro) {
		List<Paciente> resultado = new ArrayList<>();
		resultado = pacienteService.buscarPorCentroSaludId(idCentro);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A5
     * CHECKED!!
     * @param idMedico aproximada del medico/s de salud a buscar
     * @return Medicos con la localidad aproximada dada
     */
    @GetMapping(params = "idMedico")
	public ResponseEntity<List<Paciente>> buscarPorMedicoId(
			@RequestParam(name = "idMedico", required = true) Long idMedico) {
		List<Paciente> resultado = new ArrayList<>();
		resultado = pacienteService.buscarPorCentroSaludId(idMedico);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A5
     * CHECKED!! POST BODY: { "nombre": "Hospital REST", "direccion": { "domicilio": "Calle del REST 13", "localidad": "REST", "codigoPostal": "13013", "provincia": "REST" }, "telefono": 131313131, "email": "hospitalrest@recetas.com" }
     * @param medico
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medico> crear(@Valid @RequestBody Medico medico) {
        String nombre = medico.getNombre();
        String apellidos = medico.getApellidos();
        String dni = medico.getDni();
        String numeroTarjetaSanitaria = medico.getNumeroTarjetaSanitaria();
        String numeroSeguridadSocial = medico.getnumeroSeguridadSocial();
        Direccion direccion = centroSalud.getDireccion();
        Integer telefono = medico.getTelefono();
        Date fechaNacimiento = medico.getFechaNacimiento();
        CentroSalud centroSalud = medico.getCentroSalud();
        Medico medico = medico.getMedico();
        String email = medico.getEmail();
        String login = medico.getLogin();

        if(nombre == null || nombre.isBlank()) {
            throw new WrongParameterException("Falta indicar nombre");
        }
        if(apellidos == null || apellidos.isBlank()) {
            throw new WrongParameterException("Falta indicar apellidos");
        }
        if(dni == null || dni.isBlank()) {
            throw new WrongParameterException("Falta indicar dni");
        }
        if(numeroTarjetaSanitaria == null || numeroTarjetaSanitaria.isBlank()) {
            throw new WrongParameterException("Falta indicar número de tarjeta sanitaria");
        }
        if(numeroSeguridadSocial == null || numeroSeguridadSocial.isBlank()) {
            throw new WrongParameterException("Falta indicar número de seguridad social");
        }
        if(direccion == null) {
            throw new WrongParameterException("Falta indicar dirección");
        }
        if(telefono == null) {
            throw new WrongParameterException("Falta indicar teléfono");
        }
        if(fechaNacimiento == null) {
            throw new WrongParameterException("Falta indicar fecha de nacimiento");
        }
        if(centroSalud == null) {
            throw new WrongParameterException("Falta indicar centro de salud");
        }
        if(medico == null) {
            throw new WrongParameterException("Falta indicar médico");
        }
        if(email == null || email.isBlank()) {
            throw new WrongParameterException("Falta indicar email");
        }
        if(login == null || login.isBlank()) {
            throw new WrongParameterException("Falta indicar login");
        }
 
        Paciente nuevoPaciente = new Paciente(login, nombre, apellidos, dni, numeroTarjetaSanitaria, numeroSeguridadSocial, direccion, telefono, fechaNacimiento, centroSalud, medico, email);

        nuevoPaciente = pacienteService.crear(nuevoPaciente);
        URI uri = crearURIPaciente(nuevoPaciente);
        return ResponseEntity.created(uri).body(nuevoPaciente);
	}


    private URI crearURIPaciente(Paciente paciente) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(paciente.getId())
				.toUri();
	}
    
}


