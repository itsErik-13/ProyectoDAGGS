package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uvigo.dagss.recetas.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.servicios.PrescripcionService;
import es.uvigo.dagss.recetas.servicios.RecetaService;

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
     * PREGUNTAR SI DEVOLVER UNA LISTA DE DTOs O SI SE RENDERIZA EN FRONT LA INFORMACIÓN QUE SE PIDE
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

    
}
