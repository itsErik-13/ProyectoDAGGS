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
import es.uvigo.dagss.recetas.daos.CitaDAO;
import es.uvigo.dagss.recetas.daos.FarmaciaDAO;
import es.uvigo.dagss.recetas.daos.MedicamentoDAO;
import es.uvigo.dagss.recetas.daos.MedicoDAO;
import es.uvigo.dagss.recetas.daos.PacienteDAO;
import es.uvigo.dagss.recetas.daos.PrescripcionDAO;
import es.uvigo.dagss.recetas.daos.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;

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

	@Autowired
	CitaDAO citaDAO;

	@Autowired
	MedicamentoDAO medicamentoDAO;

	@Autowired
	PrescripcionDAO prescripcionDAO;

	@Autowired
	RecetaDAO recetaDAO;

	public static void main(String[] args) {
		SpringApplication.run(RecetasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		eliminarEntidades();
		crearEntidades();
		// listarEntidades();
	}

	private void crearEntidades() {

		Administrador administrador = new Administrador("admin", "admin", "admin", "admin@recetas.com");
		Administrador administrador2 = new Administrador("admin2", "admin2", "admin2", "admin2@recetas.com");
		administrador = administradorDAO.save(administrador);
		administrador2 = administradorDAO.save(administrador2);

		CentroSalud centroSalud1 = new CentroSalud("Hospital Amanecer",
				new Direccion("Calle del Amanecer 45", "Ourense", "32002", "Ourense"), 988888888,
				"hospitalamanecer@recetas.com");
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

		Farmacia farmacia1 = new Farmacia("farmacia01", "Farmacia Central", "Laura", "Gómez López", "12345678B",
				"NC123456", new Direccion("Eulogio Gómez Franqueira", "Ourense", "32002", "Ourense"), 915678901,
				"farmaciacentral@gmail.com");
		Farmacia farmacia2 = new Farmacia("farmacia02", "Farmacia Salud y Bienestar", "Carlos", "Martínez Fernández",
				"98765432C", "NC789012", new Direccion("Calle de La Palma", "Vigo", "36201", "Pontevedra"), 932345678,
				"contacto@saludybienestar.com");

		farmacia1 = farmaciaDAO.save(farmacia1);
		farmacia2 = farmaciaDAO.save(farmacia2);

		Date fecha1 = new Date(125, 0, 18, 10, 15); // 18 de enero de 2025, 10:00 AM
		Date fecha2 = new Date(125, 0, 18, 10, 00); // 19 de enero de 2025, 12:30 PM
		Date fecha3 = new Date(125, 0, 18, 9, 15); // 20 de enero de 2025, 9:15 AM

		Cita cita1 = new Cita(paciente1, medico, fecha1, fecha1);
		Cita cita2 = new Cita(paciente2, medico2, fecha2, fecha2);
		Cita cita3 = new Cita(paciente3, medico, fecha3, fecha3);

		cita1 = citaDAO.save(cita1);
		cita2 = citaDAO.save(cita2);
		cita3 = citaDAO.save(cita3);

		Medicamento medicamento1 = new Medicamento("Paracetamol Kern", "Paracetamol", "Kern Pharma", "Analgésico", 20);
		Medicamento medicamento2 = new Medicamento("Ibuprofeno Normon", "Ibuprofeno", "Normon", "Antiinflamatorio", 30);
		Medicamento medicamento3 = new Medicamento("Dalsy", "Ibuprofeno", "Abbott", "Antiinflamatorio", 15);
		Medicamento medicamento4 = new Medicamento("Nolotil", "Metamizol", "Boehringer", "Analgésico", 10);
		Medicamento medicamento5 = new Medicamento("Amoxicilina Normon", "Amoxicilina", "Normon", "Antibiótico", 14);
		Medicamento medicamento6 = new Medicamento("Clamoxyl", "Amoxicilina", "GlaxoSmithKline", "Antibiótico", 21);

		medicamento1 = medicamentoDAO.save(medicamento1);
		medicamento2 = medicamentoDAO.save(medicamento2);
		medicamento3 = medicamentoDAO.save(medicamento3);
		medicamento4 = medicamentoDAO.save(medicamento4);
		medicamento5 = medicamentoDAO.save(medicamento5);
		medicamento6 = medicamentoDAO.save(medicamento6);

		Prescripcion prescripcion1 = new Prescripcion(medicamento1, paciente1, medico, 3.0,
				"Tomar 1 comprimido 3 veces al día después de las comidas", new Date(2025 - 1900, 1 - 1, 31));
		Prescripcion prescripcion2 = new Prescripcion(medicamento2, paciente2, medico2, 2.0,
				"Tomar 1 comprimido 2 veces al día. No exceder la dosis", new Date(2025 - 1900, 2 - 1, 15));
		Prescripcion prescripcion3 = new Prescripcion(medicamento3, paciente3, medico, 20.0,
				"Tomar 10 ml 2 veces al día con el medidor suministrado", new Date(2025 - 1900, 1 - 1, 20));
		Prescripcion prescripcion4 = new Prescripcion(medicamento4, paciente1, medico, 1.0,
				"Tomar 1 cápsula al día por la mañana", new Date(2025 - 1900, 1 - 1, 28));
		Prescripcion prescripcion5 = new Prescripcion(medicamento5, paciente2, medico2, 1.5,
				"Tomar 1 cápsula y media antes de acostarse", new Date(2025 - 1900, 1 - 1, 25));
		Prescripcion prescripcion6 = new Prescripcion(medicamento6, paciente3, medico, 4.0,
				"Tomar 1 comprimido cada 6 horas (4 por día). Completar tratamiento", new Date(2025 - 1900, 1 - 1, 31));

		prescripcion1 = prescripcionDAO.save(prescripcion1);
		prescripcion2 = prescripcionDAO.save(prescripcion2);
		prescripcion3 = prescripcionDAO.save(prescripcion3);
		prescripcion4 = prescripcionDAO.save(prescripcion4);
		prescripcion5 = prescripcionDAO.save(prescripcion5);
		prescripcion6 = prescripcionDAO.save(prescripcion6);

		Receta receta1 = new Receta(prescripcion1, new Date(2025 - 1900, 0, 10), new Date(2025 - 1900, 1, 10), 2);
		Receta receta2 = new Receta(prescripcion2, new Date(2025 - 1900, 0, 15), new Date(2025 - 1900, 1, 15), 1);
		Receta receta3 = new Receta(prescripcion3, new Date(2025 - 1900, 0, 5), new Date(2025 - 1900, 1, 20), 3);
		Receta receta4 = new Receta(prescripcion4, new Date(2025 - 1900, 0, 1), new Date(2025 - 1900, 1, 31), 1);
		Receta receta5 = new Receta(prescripcion5, new Date(2025 - 1900, 0, 20), new Date(2025 - 1900, 2, 1), 2);
		Receta receta6 = new Receta(prescripcion6, new Date(2025 - 1900, 0, 25), new Date(2025 - 1900, 2, 15), 4);

		receta1 = recetaDAO.save(receta1);
		receta2 = recetaDAO.save(receta2);
		receta3 = recetaDAO.save(receta3);
		receta4 = recetaDAO.save(receta4);
		receta5 = recetaDAO.save(receta5);
		receta6 = recetaDAO.save(receta6);

	}

	public void listarEntidades() {
		System.out.println("[RECETAS]: -------------------");
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

		List<Cita> citas = citaDAO.findAll();
		System.out.println("[RECETAS]: Todas las Citas");
		for (Cita c : citas) {
			System.out.println("[RECETAS]:    " + c);
		}

		citas = citaDAO.findByFecha(new Date(125, 0, 18, 10, 0));
		System.out.println("[RECETAS]: Citas del día 18/01/2025 ");
		for (Cita c : citas) {
			System.out.println("[RECETAS]:    " + c);
		}

		List<Medicamento> medicamentos = medicamentoDAO.findAll();
		System.out.println("[RECETAS]: Todos los Medicamentos");
		for (Medicamento m : medicamentos) {
			System.out.println("[RECETAS]:    " + m);
		}

		medicamentos = medicamentoDAO.findByFabricanteContaining("Normon");
		System.out.println("[RECETAS]: Medicamentos de Normon");
		for (Medicamento m : medicamentos) {
			System.out.println("[RECETAS]:    " + m);
		}

		List<Prescripcion> prescripciones = prescripcionDAO.findAll();
		System.out.println("[RECETAS]: Todas las Prescripciones");
		for (Prescripcion p : prescripciones) {
			System.out.println("[RECETAS]:    " + p);
		}

		List<Receta> recetas = recetaDAO.findAll();
		System.out.println("[RECETAS]: Todas las Recetas");
		for (Receta r : recetas) {
			System.out.println("[RECETAS]:    " + r);
		}

		System.out.println("[RECETAS]: -------------------");
	}

	public void eliminarEntidades() {
		administradorDAO.deleteAll();
		citaDAO.deleteAll();
		recetaDAO.deleteAll();
		prescripcionDAO.deleteAll();
		pacienteDAO.deleteAll();
		medicoDAO.deleteAll();
		centroSaludDAO.deleteAll();
		farmaciaDAO.deleteAll();
		medicamentoDAO.deleteAll();
	}

}
