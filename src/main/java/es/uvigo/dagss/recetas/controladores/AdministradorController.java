package es.uvigo.dagss.recetas.controladores;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.dagss.recetas.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.controladores.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/administradores", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdministradorController {
    @Autowired
    AdministradorService administradorService;

    /**
     * HU-A2
     * CHECKED!!
     * @return Lista de administradores
     */
    @GetMapping()
	public ResponseEntity<List<Administrador>> listarAdministradores() {
		List<Administrador> resultado = new ArrayList<>();
		resultado = administradorService.listarAdministradores();
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    /**
     * HU-A2
     * CHECHKED!!
     * @param id del administrador a buscar
     * @return Administrador con el id dado
     */
    @GetMapping(path = "{id}")
	public ResponseEntity<Administrador> buscarPorId(@PathVariable("id") Long id) {
		Optional<Administrador> administrador = administradorService.buscarPorId(id);

		if (administrador.isEmpty()) {
			throw new ResourceNotFoundException("Administrador no encontrado");
		} else {
			return new ResponseEntity<>(administrador.get(), HttpStatus.OK);
		}

	}

    /**
     * HU-A2
     * CHECKED!!
     * @param id del administrador a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
		Optional<Administrador> administrador = administradorService.buscarPorId(id);

		if (administrador.isEmpty()) {
			throw new ResourceNotFoundException("Administrador no encontrado");
		} else {
			administradorService.eliminar(administrador.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    /**
     * HU-A2
     * Sólo se updatea correctamente si se añade un id en el body y todos los datos necesarios, en otro caso se crea una nueva entidad
     * @param id del administrador a modificar
     * @param administrador el administrador modificado
     * @return la instancia modificada
     */
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Administrador> modificar(@PathVariable("id") Long id, @Valid @RequestBody Administrador administrador) {
		Optional<Administrador> administradorOptional = administradorService.buscarPorId(id);

		if (administradorOptional.isEmpty()) {
			throw new ResourceNotFoundException("Almacen no encontrado");
		} else {
            administrador.setId(administradorOptional.get().getId());
			Administrador nuevoAdministrador = administradorService.modificar(administrador);
			return new ResponseEntity<>(nuevoAdministrador, HttpStatus.OK);
		}
	}

    /**
     * HU-A2
     * CHECKED!! POST BODY: { "login" : "adminPruebaREST", "password" : "passPruebaREST", "nombre" : "nombreAdminREST", "email" : "emailAdminREST" }
     * @param administrador
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Administrador> crear(@Valid @RequestBody Administrador administrador) {
		String login = administrador.getLogin();
        String password = administrador.getPassword();
        String nombre = administrador.getNombre();
        String email = administrador.getEmail();
        if(login == null || login.isBlank()) {
            throw new WrongParameterException("Falta indicar login");
        }
        if(password == null || password.isBlank()) {
            throw new WrongParameterException("Falta indicar password");
        }
        if(nombre == null || nombre.isBlank()) {
            throw new WrongParameterException("Falta indicar nombre");
        }
        if(email == null || email.isBlank()) {
            throw new WrongParameterException("Falta indicar email");
        }
        
        Administrador nuevoAdministrador = new Administrador(login, password, nombre, email);
        nuevoAdministrador = administradorService.crear(nuevoAdministrador);
        URI uri = crearURIAdministrador(nuevoAdministrador);
        return ResponseEntity.created(uri).body(nuevoAdministrador);
	}


    private URI crearURIAdministrador(Administrador administrador) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(administrador.getId())
				.toUri();
	}
}
