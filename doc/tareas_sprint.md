# SPRINT
**Objetivo**: Desarrollar los componentes JPA y Spring responsables de la implementación del _backend_ REST del proyecto "Receta electrónica"

**Inicio**:  21/11/2024

**Duración**: 4-? semanas (hasta 22/1/2025, día del examen)

**Proyecto de partida:** [GitHub: recetas-24](https://github.com/esei-si-dagss/recetas-24) (Proyecto Spring Boot de partida, con la jerarquía de entidades incompletas encargada de almacenar los diferentes tipos de usuario de la aplicación) 



## TAREAS SPRINT (organización orientativa, no obligatoria)

### Semana 1 (hasta 27-28/11/2024)
* Estudio de la descripción del proyecto y las [historias de usuario](historias.md) incluidas en el Sprint backlog.
* Modelo E-R con las entidades y relaciones necesarias para dar soporte a las historias del Sprint.
* Implementar las entidades JPA correspondientes, junto con sus relaciones.


### Semana 2 (hasta 11-12/12/2024)
* Implementar los DAOs (_data access object_)/Repository de Spring Data JPA necesarios, definiendo las consultas (mediante JPQL en los casos que corresponda) necesarias para dar soporte a las funcionalidades a usar en las historias de usuario incluidas en el Sprint.
* Implementación de la capa de servicios (`@Service`) para dar soporte a las historias que los necesiten.

### Semana 3 (hasta 18-19/12/2024)
* Listado de los _endpoint_ REST a implementar y primer esbozo de su implementación con Spring MVC.

### "Semana" 4 (hasta 22/1/2025, día del examen)
* Implementación del API REST.
* (opcional) Inclusión de tests automáticos (`@SpringBootTest`, `@DataJpaTest`, `@WebMvcTest`).
* Redacción del documento entregable.



## ENTREGA

* Proyecto en grupos de **3 personas**
* Entrega en MOOVI (**hasta 22/1/2025**, fecha del examen) 
* Requisitos mínimos:
   * Entrega de una clase auxiliar o consultas SQL para la inicialización de la base de datos (datos de prueba de la aplicación).
   * Soporte para las siguientes historias de usuario:
      * HU-M4. [Médico]   Creación de _prescripciones_
      * HU-M5. [Médico]   Generación de "planes de _recetas_" para una _prescripción_
      * HU-P3. [Paciente] Creación de _cita_
      * HU-P4. [Paciente] Visualización de "planes de _recetas_"



### Contenidos de los documentos entregables

#### Informe del proyecto

##### Material a entregar (en un único ZIP)

* Carpeta con código fuente del proyecto  (haced antes `mvn clean` para que sólo se incluya el código fuente)
* Memoria del proyecto (ver estructura propuesta)



##### Estructura propuesta para la memoria del proyecto (extensión orientativa:  hasta 7-8 páginas)

**Parte 1: Descripción técnica**
1. Breve descripción general del proyecto.
2. Descripción de las funcionalidades cubiertas.
3. Descripción de los elementos que conforman la implementación realizada:
    - Entidades JPA (acompañadas de modelo ER).
    - Repositorios/DAOs, servicios.
    - "Controllers" REST de Spring MVC.
      - **Importante:** detallar los EndPoints REST y las operaciones implementandas sobre ellos.
    - Otros detalles de configuración relevantes.
4. Instrucciones de compilación y uso (si fueran necesarias).
5. Detalle del reparto de tareas entre los miembros del grupo.

**Parte 2: Revisión crítica de la arquitectura del proyecto Spring**
* Se trata de realizar una revisión de la arquitectura de la aplicación y de las cuestiones de diseño que pudieran ser incorrectas o mejorables en la solución propuesta (duplicidad de código, mejor uso de herencia, ''sobreingeniería'' provocada por el uso de Spring, etc), así como las impresiones personales sobre las soluciones adoptadas.
   * **Importante:** detallar los patrones de diseño empleados o aplicados. 
* El formato es libre, como orientación se pueden aborar los siguientes puntos:
   * Conclusiones, problemas y/o mejoras respecto a la arquitectura general del proyecto y de la idoneidad del empleo de las tecnologías de Spring.
   * Conclusiones, problemas y/o mejoras respecto al diseño del modelo de datos (entidades JPA) propuesto [aspectos poco claros, mejoras en la modelización, relaciones superfluas, diseños alternativos, etc].
   * Conclusiones, problemas y/o mejoras respecto al diseño de la capa de lógica [limitaciones y mejoras de la arquitectura empleada, ideas para la mejora de la organización y reutilización del código, dificultades de las tecnologías empleadas, etc].
   * Conclusiones, problemas y/o mejoras respecto al diseño de la capa REST [limitaciones y mejoras de la arquitectura empleada, ideas para la mejora de la organización y reutilización del código, dificultades de las tecnologías empleadas, etc].
   * Conclusiones generales de la práctica y de la tecnologías empleadas.