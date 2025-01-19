package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/medicamentos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;
    
    /**
     * HU-A8
     * 
     * @return Lista de medicamentos
     */

    @GetMapping()
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        List<Medicamento> resultado = new ArrayList<>();
        resultado = medicamentoService.listarMedicamentos();
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * HU-A8
     * 
     * @param id de la medicamento a buscar
     * @return medicamento con el id dado
     */

     @GetMapping(path = "{id}")
     public ResponseEntity<Medicamento> buscarPorId(@PathVariable("id") Long id) {
         Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);
 
         if (medicamento.isEmpty()) {
             throw new ResourceNotFoundException("Medicamento no encontrado");
         } else {
             return new ResponseEntity<>(medicamento.get(), HttpStatus.OK);
         }
 
     }

     /**
      * HU-A8
      * 
      * @param id de la medicamento a eliminar
      * @return
      */

        @DeleteMapping(path = "{id}")
        public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
            Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);
    
            if (medicamento.isEmpty()) {
                throw new ResourceNotFoundException("Medicamento no encontrado");
            } else {
                medicamentoService.eliminar(medicamento.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
    
        }

        /**
         * HU-A8
         * 
         * @param nombre comercial del medicamento a buscar
         * @return lista de medicamentos com ese nombre comercial
         */

        @GetMapping(params = "nombreComercial")
        public ResponseEntity<List<Medicamento>> buscarPorNombre(@RequestParam("nombreComercial") String nombre) {
            List<Medicamento> medicamento = medicamentoService.buscarPorNombreComercial(nombre);
    
            if (medicamento.isEmpty()) {
                throw new ResourceNotFoundException("Medicamento no encontrado");
            } else {
                return new ResponseEntity<>(medicamento, HttpStatus.OK);
            }
    
        }

        /**
         * HU-A8
         * 
         * @param principio activo del medicamento a buscar
         * @return lista de medicamentos con ese principio activo
         */

        @GetMapping(params = "principioActivo")
        public ResponseEntity<List<Medicamento>> buscarPorPrincipioActivo(@RequestParam("principioActivo") String principioActivo) {
            List<Medicamento> medicamento = medicamentoService.buscarPorPrincipioActivo(principioActivo);
    
            if (medicamento.isEmpty()) {
                throw new ResourceNotFoundException("Medicamento no encontrado");
            } else {
                return new ResponseEntity<>(medicamento, HttpStatus.OK);
            }
    
        }

        /**
         * HU-A8
         * 
         * @param fabricante del medicamento a buscar
         * @return lista de medicamentos con ese fabricante
         */

        @GetMapping(params = "fabricante")
        public ResponseEntity<List<Medicamento>> buscarPorFabricante(@RequestParam("fabricante") String fabricante) {
            List<Medicamento> medicamento = medicamentoService.buscarPorFabricante(fabricante);
    
            if (medicamento.isEmpty()) {
                throw new ResourceNotFoundException("Medicamento no encontrado");
            } else {
                return new ResponseEntity<>(medicamento, HttpStatus.OK);
            }
    
        }

        /**
         * HU-A8
         * 
         * @param familia del medicamento a buscar
         * @return lista de medicamentos de esa familia
         */
        @GetMapping(params = "familia")
        public ResponseEntity<List<Medicamento>> buscarPorFamilia(@RequestParam("familia") String familia) {
            List<Medicamento> medicamento = medicamentoService.buscarPorFamilia(familia);
    
            if (medicamento.isEmpty()) {
                throw new ResourceNotFoundException("Medicamento no encontrado");
            } else {
                return new ResponseEntity<>(medicamento, HttpStatus.OK);
            }
    
        }

        /**
         * HU-A8
         * 
         * @param medicamento a crear
         * @return medicamento creado
         */
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Medicamento> crear(@Valid @RequestBody Medicamento medicamento) {
            String nombreComercial = medicamento.getNombreComercial();
            String principioActivo = medicamento.getPrincipioActivo();
            String fabricante = medicamento.getFabricante();
            String familia = medicamento.getFamilia();
            int numDosis = medicamento.getNumDosis();
        
            if (nombreComercial == null || nombreComercial.isBlank()) {
                throw new WrongParameterException("Falta indicar nombre comercial del medicamento");
            }
            if (principioActivo == null || principioActivo.isBlank()) {
                throw new WrongParameterException("Falta indicar principio activo del medicamento");
            }
            if (fabricante == null || fabricante.isBlank()) {
                throw new WrongParameterException("Falta indicar fabricante del medicamento");
            }
            if (familia == null || familia.isBlank()) {
                throw new WrongParameterException("Falta indicar familia del medicamento");
            }
            if (numDosis <= 0) {
                throw new WrongParameterException("Falta indicar número de dosis del medicamento");
            }
        
            Medicamento nuevoMedicamento = new Medicamento(nombreComercial, principioActivo, fabricante, familia, numDosis);
            nuevoMedicamento = medicamentoService.crear(nuevoMedicamento);
            URI uri = crearURIMedicamento(nuevoMedicamento);
            return ResponseEntity.created(uri).body(nuevoMedicamento);
        }


        private URI crearURIMedicamento(Medicamento medicamento) {
            return ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(medicamento.getId())
                    .toUri();
        }

}
