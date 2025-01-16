package es.uvigo.dagss.recetas;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uvigo.dagss.recetas.daos.AdministradorDAO;
import es.uvigo.dagss.recetas.daos.CentroSaludDAO;
import es.uvigo.dagss.recetas.daos.FarmaciaDAO;
import es.uvigo.dagss.recetas.daos.MedicoDAO;
import es.uvigo.dagss.recetas.daos.PacienteDAO;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;

@SpringBootApplication
public class RecetasApplication implements CommandLineRunner {

	@Autowired
	AdministradorDAO administradorDAO;

	@Autowired
	CentroSaludDAO centroSaludDAO;

	@Autowired
	MedicoDAO medicoDAO;

	@Autowired
	PacienteDAO pacienteDAO;

	@Autowired
	FarmaciaDAO farmaciaDAO;

	public static void main(String[] args) {
		SpringApplication.run(RecetasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		crearEntidades();
		listarEntidades();
		eliminarEntidades();
	}

	private void crearEntidades() {
		Administrador administrador = new Administrador("admin", "admin", "admin", "admin@recetas.com");
		Administrador administrador2 = new Administrador("admin2", "admin2", "admin2", "admin2@recetas.com");
		administrador = administradorDAO.save(administrador);
		administrador2 = administradorDAO.save(administrador2);

		CentroSalud centroSalud1 = new CentroSalud("Hospital Libertad",
				new Direccion("Avenida de la Libertad 45", "Ourense", "32002", "Ourense"), 988888888,
				"hospitallibertad@recetas.com");
		CentroSalud centroSalud2 = new CentroSalud("Punto de Atención Continuada de Vigo",
				new Direccion("Avenida de la Avioneta 32", "Vigo", "36201", "Pontevedra"), 988777777,
				"pacvigo@recetas.com");
		CentroSalud centroSalud3 = new CentroSalud("Punto de Atención Continuada de Ourense",
				new Direccion("Curros Enríquez", "Ourense", "32002", "Ourense"), 988666666, "pacourense@recetas.com");

		centroSalud1 = centroSaludDAO.save(centroSalud1);
		centroSalud2 = centroSaludDAO.save(centroSalud2);
		centroSalud3 = centroSaludDAO.save(centroSalud3);

		Medico medico = new Medico("medico1", "Jose", "Fernández González", "76735654H", "12345678", 619845763,
				centroSalud1, "medico1@recetas.com");
		Medico medico2 = new Medico("medico2", "Luis", "Trujillo González", "76735654H", "12345678", 639043653,
				centroSalud2, "medico2@recetas.com");

		medico = medicoDAO.save(medico);
		medico2 = medicoDAO.save(medico2);

		Paciente paciente1 = new Paciente("paciente1", "Pepe", "Gómez Rodríguez", "73423455L", "123456690AS",
				"1234145235SDF", new Direccion("Eulogio Gómez Franqueira", "Ourense", "32002", "Ourense"), 678134084,
				new Date(95, 0, 17), centroSalud1, medico, "paciente1@recetas.com");
		Paciente paciente2 = new Paciente("paciente2", "María", "Rodríguez Pérez", "73423456L", "123456789AS",
				"1234145236SDF", new Direccion("Calle de La Palma", "Vigo", "36201", "Pontevedra"), 678134085,
				new Date(95, 1, 5), centroSalud2, medico2, "paciente2@recetas.com");
		Paciente paciente3 = new Paciente("paciente3", "Luis", "Fernández Díaz", "73423457L", "123456790AS",
				"1234145237SDF", new Direccion("San Rosendo", "Ourense", "32002", "Ourense"), 678134086,
				new Date(95, 2, 25), centroSalud1, medico, "paciente3@recetas.com");

		paciente1 = pacienteDAO.save(paciente1);
		paciente2 = pacienteDAO.save(paciente2);
		paciente3 = pacienteDAO.save(paciente3);

		Farmacia farmacia1 = new Farmacia("farmacia01", "Farmacia Central", "Laura", "Gómez López", "12345678B", "NC123456", new Direccion("Eulogio Gómez Franqueira", "Ourense", "32002", "Ourense"), 915678901, "farmaciacentral@gmail.com");
		Farmacia farmacia2 = new Farmacia("farmacia02", "Farmacia Salud y Bienestar", "Carlos", "Martínez Fernández", "98765432C", "NC789012", new Direccion("Calle de La Palma", "Vigo", "36201", "Pontevedra"), 932345678, "contacto@saludybienestar.com");

		farmacia1 = farmaciaDAO.save(farmacia1);
		farmacia2 = farmaciaDAO.save(farmacia2);



	}

	public void listarEntidades() {
		System.out.println("[RECETAS]: -------------------");
		administradorDAO.findAll().forEach(System.out::println);

		List<Administrador> administradores = administradorDAO.findAll();
		System.out.println("[RECETAS]: Todos los Administradores");
		for (Administrador a : administradores) {
			System.out.println("[RECETAS]:    " + a);
		}

		List<CentroSalud> centros = centroSaludDAO.findAll();
		System.out.println("[RECETAS]: Todos los Centros de Salud");
		for (CentroSalud c : centros) {
			System.out.println("[RECETAS]:    " + c);
		}

		centros = centroSaludDAO.findByDireccionLocalidadContaining("Ourense");
		System.out.println("[RECETAS]: Centros de salud de Ourense");
		for (CentroSalud c : centros) {
			System.out.println("[RECETAS]:    " + c);
		}

		List<Medico> medicos = medicoDAO.findAll();
		System.out.println("[RECETAS]: Todos los Medicos");
		for (Medico m : medicos) {
			System.out.println("[RECETAS]:    " + m);
		}

		medicos = medicoDAO.findByCentroSaludDireccionLocalidadContaining("Ourense");
		System.out.println("[RECETAS]: Medicos con Centro de salud de Ourense");
		for (Medico m : medicos) {
			System.out.println("[RECETAS]:    " + m);
		}

		List<Paciente> pacientes = pacienteDAO.findAll();
		System.out.println("[RECETAS]: Todos los Pacientes");
		for (Paciente p : pacientes) {
			System.out.println("[RECETAS]:    " + p);
		}

		pacientes = pacienteDAO.findByDireccionLocalidadContaining("Ourense");
		System.out.println("[RECETAS]: Pacientes con direccion de Ourense");
		for (Paciente p : pacientes) {
			System.out.println("[RECETAS]:    " + p);
		}

		List<Farmacia> farmacias = farmaciaDAO.findAll();
		System.out.println("[RECETAS]: Todos las Farmacias");
		for (Farmacia f : farmacias) {
			System.out.println("[RECETAS]:    " + f);
		}

		farmacias = farmaciaDAO.findByDireccionLocalidadContaining("Ourense");
		System.out.println("[RECETAS]: Farmacias con direccion de Ourense");
		for (Farmacia f : farmacias) {
			System.out.println("[RECETAS]:    " + f);
		}

		System.out.println("[RECETAS]: -------------------");
	}

	public void eliminarEntidades() {
		administradorDAO.deleteAll();
		pacienteDAO.deleteAll();
		medicoDAO.deleteAll();
		centroSaludDAO.deleteAll();
		farmaciaDAO.deleteAll();

	}

}
