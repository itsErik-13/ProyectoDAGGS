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
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/medicos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    /**
     * HU-A4
     * CHECKED!!
     * @return Lista los medicos
     */
    @GetMapping()
	public ResponseEntity<List<Medico>> listarMedicos() {
		List<Medico> resultado = new ArrayList<>();
		resultado = medicoService.listarMedicos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A4
     * CHECHKED!!
     * @param id del Medico a buscar
     * @return Medico con el id dado
     */
    @GetMapping(path = "{id}")
	public ResponseEntity<Medico> buscarPorId(@PathVariable("id") Long id) {
		Optional<Medico> medico = medicoService.buscarPorId(id);

		if (medico.isEmpty()) {
			throw new ResourceNotFoundException("Medico no encontrado");
		} else {
			return new ResponseEntity<>(medico.get(), HttpStatus.OK);
		}

	}

    /**
     * HU-A4
     * CHECKED!!
     * @param id del medico a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
		Optional<Medico> medico = medicoService.buscarPorId(id);

		if (medico.isEmpty()) {
			throw new ResourceNotFoundException("Medico no encontrado");
		} else {
			medicoService.eliminar(medico.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    /**
     * HU-A4
     * Sólo se updatea correctamente si se añade un id en el body y todos los datos necesarios, en otro caso se crea una nueva entidad
     * @param id del medico a modificar
     * @param medico el conjunto de medicos modificado
     * @return la instancia modificada
     */
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medico> modificar(@PathVariable("id") Long id, @Valid @RequestBody Medico medico) {
		Optional<Medico> medicoOptional = medicoService.buscarPorId(id);

		if (medicoOptional.isEmpty()) {
			throw new ResourceNotFoundException("Medico no encontrado");
		} else {
            medico.setId(medicoOptional.get().getId());
			Medico nuevoMedico = medicoService.modificar(medico);
			return new ResponseEntity<>(nuevoMedico, HttpStatus.OK);
		}
	}

    /**
     * HU-M6
     * 
     * @param id del medico a modificar las credenciales o datos básicos
     * @param medico el medico modificado
     * @return la instancia modificada
     */
    @PatchMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medico> modificarPerfil(@PathVariable("id") Long id, @Valid @RequestBody Medico medico) {
		Optional<Medico> medicoOptional = medicoService.buscarPorId(id);

		if (medicoOptional.isEmpty()) {
			throw new ResourceNotFoundException("Medico no encontrado");
		} else {
            medicoOptional.get().setPassword(medico.getPassword() == null ? medicoOptional.get().getPassword() : medico.getPassword());
            medicoOptional.get().setNombre(medico.getNombre() == null ? medicoOptional.get().getNombre() : medico.getNombre());
            medicoOptional.get().setApellidos(medico.getApellidos() == null ? medicoOptional.get().getApellidos() : medico.getApellidos());
            medicoOptional.get().setDni(medico.getDni() == null ? medicoOptional.get().getDni() : medico.getDni());
            medicoOptional.get().setEmail(medico.getEmail() == null ? medicoOptional.get().getEmail() : medico.getEmail());
            medicoOptional.get().setTelefono(medico.getTelefono() == null ? medicoOptional.get().getTelefono() : medico.getTelefono());
            
			Medico nuevoMedico = medicoService.modificar(medicoOptional.get());
			return new ResponseEntity<>(nuevoMedico, HttpStatus.OK);
		}
	}

    /**
     * HU-A4
     * CHECKED!!
     * @param nombre aproximado del medico/s de salud a buscar
     * @return Medicos con el nombre aproxiamdo dado
     */
    @GetMapping(params = "nombre")
	public ResponseEntity<List<Medico>> buscarPorNombre(
			@RequestParam(name = "nombre", required = true) String nombre) {
		List<Medico> resultado = new ArrayList<>();
		resultado = medicoService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A4
     * CHECKED!!
     * @param localidad aproximada del medico/s de salud a buscar
     * @return Medicos con la localidad aproximada dada
     */
    @GetMapping(params = "localidad")
	public ResponseEntity<List<Medico>> buscarPorMedicoLocalidad(
			@RequestParam(name = "localidad", required = true) String localidad) {
		List<Medico> resultado = new ArrayList<>();
		resultado = medicoService.buscarPorMedicoLocalidad(localidad);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

     /**
     * HU-A4
     * CHECKED!!
     * @param idCentro aproximada del medico/s de salud a buscar
     * @return Medicos con la localidad aproximada dada
     */
    @GetMapping(params = "centroSalud")
	public ResponseEntity<List<Medico>> buscarPorMedicoId(
			@RequestParam(name = "centroSalud", required = true) Long idCentro) {
		List<Medico> resultado = new ArrayList<>();
		resultado = medicoService.buscarPorMedicoId(idCentro);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A3
     * CHECKED!! POST BODY: { "login": "medico REST", "password": "pass REST", "email": "medicoREST@recetas.com", "nombre": "REST", "apellidos": "REST REST", "dni": "REST", "numeroColegiado": "12345678", "telefono": 639043653, "centroSalud": { "id": 202, "nombre": "Punto de Atención Continuada de Vigo", "direccion": { "domicilio": "Avenida de la Avioneta 32", "localidad": "Vigo", "codigoPostal": "36201", "provincia": "Pontevedra" }, "telefono": 988777777, "email": "pacvigo@recetas.com", "activo": true } }
     * @param medico
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medico> crear(@Valid @RequestBody Medico medico) {
        String nombre = medico.getNombre();
        String apellidos = medico.getApellidos();
        String dni = medico.getDni();
        String numeroColegiado = medico.getNumeroColegiado();
        Integer telefono = medico.getTelefono();
        String email = medico.getEmail();
        String login = medico.getLogin();
        CentroSalud centroSalud = medico.getCentroSalud();

        if(nombre == null || nombre.isBlank()) {
            throw new WrongParameterException("Falta indicar nombre");
        }
        if(apellidos == null || apellidos.isBlank()) {
            throw new WrongParameterException("Falta indicar apellidos");
        }
        if(dni == null || dni.isBlank()) {
            throw new WrongParameterException("Falta indicar dni");
        }
        if(numeroColegiado == null || numeroColegiado.isBlank()) {
            throw new WrongParameterException("Falta indicar número de colegiado");
        }
        if(telefono == null) {
            throw new WrongParameterException("Falta indicar teléfono");
        }
        if(email == null || email.isBlank()) {
            throw new WrongParameterException("Falta indicar email");
        }
        if(login == null || login.isBlank()) {
            throw new WrongParameterException("Falta indicar login");
        }
        if(centroSalud == null) {
            throw new WrongParameterException("Falta indicar centro de salud");
        }

        
        Medico nuevoMedico = new Medico(login, nombre, apellidos, dni, numeroColegiado, telefono, centroSalud, email);
        nuevoMedico = medicoService.crear(nuevoMedico);
        URI uri = crearURIMedico(nuevoMedico);
        return ResponseEntity.created(uri).body(nuevoMedico);
	}


    private URI crearURIMedico(Medico medico) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(medico.getId())
				.toUri();
	}
    
}

