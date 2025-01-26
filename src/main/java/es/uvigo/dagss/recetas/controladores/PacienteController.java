package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
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
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/pacientes", produces = MediaType.APPLICATION_JSON_VALUE)
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
	public ResponseEntity<Paciente> buscarPorId(@PathVariable("id") Long id) {
		Optional<Paciente> paciente = pacienteService.buscarPorId(id);

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
     * Sólo se updatea correctamente si se añade un id en el body y todos los datos necesarios, en otro caso se crea una nueva entidad
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
		List<Paciente> resultado = new ArrayList<>();
		resultado = pacienteService.buscarPorLocalidad(localidad);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

     /**
     * HU-A5
     * CHECKED!!
     * @param idCentro aproximada del medico/s de salud a buscar
     * @return Medicos con la localidad aproximada dada
     */
    @GetMapping(params = "centroSalud")
	public ResponseEntity<List<Paciente>> buscarPorCentroSaludId(
			@RequestParam(name = "centroSalud", required = true) Long idCentro) {
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
    @GetMapping(params = "medico")
	public ResponseEntity<List<Paciente>> buscarPorMedicoId(
			@RequestParam(name = "medico", required = true) Long idMedico) {
		List<Paciente> resultado = new ArrayList<>();
		resultado = pacienteService.buscarPorMedicoId(idMedico);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A5
     * CHECKED!! POST BODY: { "login": "paciente REST", "password": "73423455L", "email": "pacienteREST@recetas.com", "nombre": "REST", "apellidos": "REST REST", "dni": "REST", "numeroTarjetaSanitaria": "123456690AS", "numeroSeguridadSocial": "1234145235SDF", "direccion": { "domicilio": "Eulogio Gómez Franqueira", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 678134084, "fechaNacimiento": "1995-01-17", "centroSalud": { "id": 201, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": false }, "medico": { "id": 32, "tipo": "MEDICO", "login": "medico1", "password": "12345678", "fechaAlta": "2025-01-18T22:39:52.117+00:00", "ultimoAcceso": "2025-01-18T22:39:52.117+00:00", "activo": true, "email": "medico1@recetas.com", "nombre": "Jose", "apellidos": "Fernández González", "dni": "76735654H", "numeroColegiado": "12345678", "telefono": 619845763, "centroSalud": { "id": 201, "nombre": "Hospital Amanecer", "direccion": { "domicilio": "Calle del Amanecer 45", "localidad": "Ourense", "codigoPostal": "32002", "provincia": "Ourense" }, "telefono": 988888888, "email": "hospitalamanecer@recetas.com", "activo": false } } }
     * @param medico
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Paciente> crear(@Valid @RequestBody Paciente paciente) {
        String nombre = paciente.getNombre();
        String apellidos = paciente.getApellidos();
        String dni = paciente.getDni();
        String numeroTarjetaSanitaria = paciente.getNumeroTarjetaSanitaria();
        String numeroSeguridadSocial = paciente.getNumeroSeguridadSocial();
        Direccion direccion = paciente.getDireccion();
        Integer telefono = paciente.getTelefono();
        Date fechaNacimiento = paciente.getFechaNacimiento();
        CentroSalud centroSalud = paciente.getCentroSalud();
        Medico medico = paciente.getMedico();
        String email = paciente.getEmail();
        String login = paciente.getLogin();

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

    /**
     * HU-P5
     * 
     * @param id del paciente a modificar las credenciales o datos básicos (considero contraseña, dirección, nombre establecimiento, telefono y email)
     * @param medico el medico modificado
     * @return la instancia modificada
     */
    @PatchMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Paciente> modificarPerfil(@PathVariable("id") Long id, @Valid @RequestBody Paciente paciente) {
		Optional<Paciente> pacienteOpcional = pacienteService.buscarPorId(id);

		if (pacienteOpcional.isEmpty()) {
			throw new ResourceNotFoundException("Paciente no encontrado");
		} else {
            pacienteOpcional.get().setPassword(paciente.getPassword() == null ? pacienteOpcional.get().getPassword() : paciente.getPassword());
            pacienteOpcional.get().setDireccion(paciente.getDireccion() == null ? pacienteOpcional.get().getDireccion() : paciente.getDireccion());
            pacienteOpcional.get().setNombre(paciente.getNombre() == null ? pacienteOpcional.get().getNombre() : paciente.getNombre());
            pacienteOpcional.get().setTelefono(paciente.getTelefono() == null ? pacienteOpcional.get().getTelefono() : paciente.getTelefono());
            pacienteOpcional.get().setEmail(paciente.getEmail() == null ? pacienteOpcional.get().getEmail() : paciente.getEmail());
            
			Paciente nuevoPaciente = pacienteService.modificar(pacienteOpcional.get());
			return new ResponseEntity<>(nuevoPaciente, HttpStatus.OK);
		}
	}


    private URI crearURIPaciente(Paciente paciente) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(paciente.getId())
				.toUri();
	}
    
}


