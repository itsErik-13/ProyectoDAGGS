package es.uvigo.dagss.recetas.controladores;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.uvigo.dagss.recetas.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import es.uvigo.dagss.recetas.servicios.RecetaService;

@RestController
@RequestMapping(path = "/api/recetas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RecetaController {
    @Autowired
    private RecetaService recetaService;

    @Autowired
    private FarmaciaService farmaciaService;


    /**
     * HU-F2 HU-P4
     * CHECKED!!!
     * 
     * 
     * @param paciente paciente a buscar las recetas
     * @return recetas del paciente con estado PLANIFICADA
     */
    @GetMapping(params = "paciente")
    public ResponseEntity<List<Receta>> listarRecetasPaciente(@RequestParam("paciente") Long paciente) {

        List<Receta> recetas = recetaService.buscarPorPacienteId(paciente);

        if (recetas == null) {
            throw new ResourceNotFoundException("Receta no encontrada");
        }

        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }

    /**
     * HU-F3
     * CHECKED!!!
     * DELETE localhost:8080/api/recetas/{idReceta}?farmacia={idFarmacia}
     * 
     * @param id de la farmacia a eliminar
     * @return
     */
    @DeleteMapping(path = "{id}", params = "farmacia")
    public ResponseEntity<HttpStatus> servir(@PathVariable("id") Long id, @RequestParam("farmacia") Long farmacia) {
        Optional<Receta> receta = recetaService.buscarPorId(id);

        Optional<Farmacia> farmaciaOpcional = farmaciaService.buscarPorId(farmacia);

        if(receta.isEmpty()) {
            throw new ResourceNotFoundException("Receta no encontrada");
        }

        if(farmaciaOpcional.isEmpty()) {
            throw new ResourceNotFoundException("Farmacia no encontrada");
        }

        if(receta.get().getEstado() != EstadoReceta.PLANIFICADA) {
            throw new ResourceNotFoundException("Receta no planificada");
        }

        recetaService.servir(receta.get(), farmaciaOpcional.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
