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
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import es.uvigo.dagss.recetas.servicios.FarmaciaServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/farmacia", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FarmaciaController {
    @Autowired
    private FarmaciaService farmaciaService;

    /**
     * HU-A6
     * 
     * @return Lista de farmacias
     */
    @GetMapping()
    public ResponseEntity<List<Farmacia>> listarCentrosSalud() {
        List<Farmacia> resultado = new ArrayList<>();
        resultado = farmaciaService.listarFarmacias();
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * HU-A6
     * 
     * @param id de la farmacia a buscar
     * @return farmacia con el id dado
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Farmacia> buscarPorId(@PathVariable("id") Long id) {
        Optional<Farmacia> farmacia = farmaciaService.buscarPorId(id);

        if (farmacia.isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        } else {
            return new ResponseEntity<>(farmacia.get(), HttpStatus.OK);
        }

    }

    /**
     * HU-A6
     * 
     * @param id de la farmacia a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Farmacia> farmacia = farmaciaService.buscarPorId(id);

        if (farmacia.isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        } else {
            farmaciaService.eliminar(farmacia.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * HU-A6
     * Sólo se updatea correctamente si se añade un id en el body y todos los datos
     * necesarios, en otro caso se crea una nueva entidad, PREGUNTAR
     * 
     * @param id            de la farmacia a modificar
     * @param administrador la farmacia modificada
     * @return la instancia modificada
     */
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Farmacia> modificar(@PathVariable("id") Long id, @Valid @RequestBody Farmacia farmacia) {
        Optional<Farmacia> farmaciaOpcional = farmaciaService.buscarPorId(id);

        if (farmaciaOpcional.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        } else {
            farmacia.setId(farmaciaOpcional.get().getId());
            Farmacia nuevaFarmacia = farmaciaService.modificar(farmacia);
            return new ResponseEntity<>(nuevaFarmacia, HttpStatus.OK);
        }
    }

    /**
     * HU-A6
     * 
     * @param nombre aproximado de las farmacias a buscar
     * @return Farmacias con el nombre aproxiamdo dado
     */
    @GetMapping(params = "nombre")
    public ResponseEntity<List<Farmacia>> buscarPorNombre(
            @RequestParam(name = "nombre", required = true) String nombre) {
        List<Farmacia> resultado = new ArrayList<>();
        resultado = farmaciaService.buscarPorNombreEstablecimiento(nombre);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * HU-A6
     * 
     * @param localidad aproximada de la farmacia a buscar
     * @return Farmacias con la localidad aproximada dada
     */
    @GetMapping(params = "localidad")
    public ResponseEntity<List<Farmacia>> buscarPorLocalidad(
            @RequestParam(name = "localidad", required = true) String localidad) {
        List<Farmacia> resultado = new ArrayList<>();
        resultado = farmaciaService.buscarPorLocalidad(localidad);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * HU-A6
     * 
     * @param farmacia
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Farmacia> crear(@Valid @RequestBody Farmacia farmacia) {
        String nombreEstablecimiento = farmacia.getNombreEstablecimiento();
        String nombreFarmaceutico = farmacia.getNombreFarmaceutico();
        String apellidosFarmaceutico = farmacia.getApellidosFarmaceutico();
        String dniOrNif = farmacia.getDniOrNif();
        String numeroColegiado = farmacia.getNumeroColegiado();
        Direccion direccion = farmacia.getDireccion();
        Integer telefono = farmacia.getTelefono();
        String email = farmacia.getEmail();
        String login = farmacia.getLogin();

        if (nombreEstablecimiento == null || nombreEstablecimiento.isBlank()) {
            throw new WrongParameterException("Falta indicar nombre del establecimiento");
        }
        if (nombreFarmaceutico == null || nombreFarmaceutico.isBlank()) {
            throw new WrongParameterException("Falta indicar nombre del farmacéutico");
        }
        if (apellidosFarmaceutico == null || apellidosFarmaceutico.isBlank()) {
            throw new WrongParameterException("Falta indicar apellidos del farmacéutico");
        }
        if (dniOrNif == null || dniOrNif.isBlank()) {
            throw new WrongParameterException("Falta indicar DNI o NIF");
        }
        if (numeroColegiado == null || numeroColegiado.isBlank()) {
            throw new WrongParameterException("Falta indicar número de colegiado");
        }
        if (direccion == null) {
            throw new WrongParameterException("Falta indicar dirección");
        }
        if (telefono == null) {
            throw new WrongParameterException("Falta indicar teléfono");
        }
        if (email == null || email.isBlank()) {
            throw new WrongParameterException("Falta indicar email");
        }
        if (login == null || login.isBlank()) {
            throw new WrongParameterException("Falta indicar login");
        }

        // Set the initial password as the number of colegiado
        String password = numeroColegiado;

        Farmacia nuevaFarmacia = new Farmacia(login, password, nombreEstablecimiento, nombreFarmaceutico,
                apellidosFarmaceutico, dniOrNif, direccion, telefono, email);
        nuevaFarmacia = farmaciaService.crear(nuevaFarmacia);
        URI uri = crearURIFarmacia(nuevaFarmacia);
        return ResponseEntity.created(uri).body(nuevaFarmacia);
    }

    private URI crearURIFarmacia(Farmacia farmacia) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(farmacia.getId())
                .toUri();
    }

}
