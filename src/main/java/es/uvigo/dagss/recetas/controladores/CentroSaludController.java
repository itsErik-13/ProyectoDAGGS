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
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/centrossalud", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CentroSaludController {
    @Autowired
    private CentroSaludService centroSaludService;

    /**
     * HU-A3
     * CHECKED!!
     * @return Lista de centros de salud
     */
    @GetMapping()
	public ResponseEntity<List<CentroSalud>> listarCentrosSalud() {
		List<CentroSalud> resultado = new ArrayList<>();
		resultado = centroSaludService.listarCentrosSalud();
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A3
     * CHECHKED!!
     * @param id del centro de salud a buscar
     * @return Centro de salud con el id dado
     */
    @GetMapping(path = "{id}")
	public ResponseEntity<CentroSalud> buscarPorId(@PathVariable("id") Long id) {
		Optional<CentroSalud> centroSalud = centroSaludService.buscarPorId(id);

		if (centroSalud.isEmpty()) {
			throw new ResourceNotFoundException("Centro de salud no encontrado");
		} else {
			return new ResponseEntity<>(centroSalud.get(), HttpStatus.OK);
		}

	}

    /**
     * HU-A3
     * CHECKED!!
     * @param id del centro de salud a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
		Optional<CentroSalud> centroSalud = centroSaludService.buscarPorId(id);

		if (centroSalud.isEmpty()) {
			throw new ResourceNotFoundException("Centro de salud no encontrado");
		} else {
			centroSaludService.eliminar(centroSalud.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    /**
     * HU-A3
     * Sólo se updatea correctamente si se añade un id en el body y todos los datos necesarios, en otro caso se crea una nueva entidad, PREGUNTAR
     * @param id del centro de salud a modificar
     * @param administrador el centro de salud modificado
     * @return la instancia modificada
     */
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CentroSalud> modificar(@PathVariable("id") Long id, @Valid @RequestBody CentroSalud centroSalud) {
		Optional<CentroSalud> centroSaludOpcional = centroSaludService.buscarPorId(id);

		if (centroSaludOpcional.isEmpty()) {
			throw new ResourceNotFoundException("Centro de salud no encontrado");
		} else {
            centroSalud.setId(centroSaludOpcional.get().getId());
			CentroSalud nuevocCentroSalud = centroSaludService.modificar(centroSalud);
			return new ResponseEntity<>(nuevocCentroSalud, HttpStatus.OK);
		}
	}

    /**
     * HU-A3
     * CHECKED!!
     * @param nombre aproximado del centro/s de salud a buscar
     * @return Centros de salud con el nombre aproxiamdo dado
     */
    @GetMapping(params = "nombre")
	public ResponseEntity<List<CentroSalud>> buscarPorNombre(
			@RequestParam(name = "nombre", required = true) String nombre) {
		List<CentroSalud> resultado = new ArrayList<>();
		resultado = centroSaludService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A3
     * CHECKED!!
     * @param localidad aproximada del centro/s de salud a buscar
     * @return Centros de salud con la localidad aproximada dada
     */
    @GetMapping(params = "localidad")
	public ResponseEntity<List<CentroSalud>> buscarPorLocalidad(
			@RequestParam(name = "localidad", required = true) String localidad) {
		List<CentroSalud> resultado = new ArrayList<>();
		resultado = centroSaludService.buscarPorLocalidad(localidad);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A3
     * CHECKED!! POST BODY: { "nombre": "Hospital REST", "direccion": { "domicilio": "Calle del REST 13", "localidad": "REST", "codigoPostal": "13013", "provincia": "REST" }, "telefono": 131313131, "email": "hospitalrest@recetas.com" }
     * @param centroSalud
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CentroSalud> crear(@Valid @RequestBody CentroSalud centroSalud) {
        String nombre = centroSalud.getNombre();
		Direccion direccion = centroSalud.getDireccion();
        Integer telefono = centroSalud.getTelefono();
        String email = centroSalud.getEmail();
        if(nombre == null || nombre.isBlank()) {
            throw new WrongParameterException("Falta indicar nombre");
        }
        if(direccion == null) {
            throw new WrongParameterException("Falta indicar dirección");
        }
        if(telefono == null) {
            throw new WrongParameterException("Falta indicar teléfono");
        }
        if(email == null || email.isBlank()) {
            throw new WrongParameterException("Falta indicar email");
        }
        
        CentroSalud nuevoCentroSalud = new CentroSalud(nombre, direccion, telefono, email);
        nuevoCentroSalud = centroSaludService.crear(nuevoCentroSalud);
        URI uri = crearURICentroSalud(nuevoCentroSalud);
        return ResponseEntity.created(uri).body(nuevoCentroSalud);
	}


    private URI crearURICentroSalud(CentroSalud centroSalud) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(centroSalud.getId())
				.toUri();
	}
    
}
