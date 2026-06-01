# Memoria de Práctica 4: Herramientas de Análisis Estático de Código
**Asignatura:** Análisis de la Calidad del Código y Seguridad en la Ingeniería del Software (ACCSI)  
**Curso Académico:** 2025/2026  
**Fecha de Realización:** 1 de junio de 2026  
**Tipografía Sugerida para Entrega:** Garamond 12 ptos  

---

## 1. Introducción y Objetivos
El objetivo principal de esta práctica es familiarizarse con el uso de herramientas de **Análisis Estático de Código** y aprender a interpretar sus resultados relacionándolos de manera rigurosa con los estándares internacionales de calidad de software, concretamente la norma **ISO/IEC 25000 (SQuaRE)**.

El análisis estático es una fase fundamental en el ciclo de vida del desarrollo de software (SDLC) que permite examinar el código fuente sin ejecutarlo. A través de este análisis, se identifican tempranamente bugs potenciales, malas prácticas, vulnerabilidades de seguridad, duplicación de código y problemas de diseño (como un exceso de complejidad ciclomática o acoplamiento).

Para esta evaluación se ha seleccionado **PMD**, una de las herramientas de análisis estático más consolidadas para el ecosistema Java. PMD analiza el código fuente construyendo un **Árbol de Sintaxis Abstracta (AST - Abstract Syntax Tree)** y aplicando un conjunto de reglas (rulesets) parametrizables que permiten medir la calidad del producto final antes de su despliegue.

---

## 2. Instalación e Integración de la Herramienta PMD
Para esta práctica, se ha optado por integrar PMD mediante el **Maven PMD Plugin** en lugar de realizar una instalación local independiente basada en la línea de comandos (CLI). 

### Justificación Técnica de la Integración vía Maven:
1. **Automatización en el Ciclo de Construcción**: La integración en el `pom.xml` permite que el análisis estático forme parte del flujo nativo del proyecto. Ejecutar el análisis es tan sencillo como lanzar un comando Maven estándar, lo que facilita su posterior automatización en entornos de Integración Continua (CI/CD).
2. **Portabilidad del Entorno de Desarrollo**: Al definir la versión del plugin y el conjunto de reglas dentro del control de versiones del proyecto, se garantiza que todos los desarrolladores del equipo utilicen exactamente las mismas métricas y umbriles, evitando el clásico problema de "en mi máquina funciona".
3. **Generación Automatizada de Reportes**: El plugin genera de forma nativa reportes tanto interactivos (HTML) como estructurados (XML) dentro del directorio de construcción (`target/`).

### Configuración en `pom.xml`:
Se incorporó el plugin en el apartado `<build><plugins>` del archivo `pom.xml` del proyecto con la siguiente estructura:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.21.0</version>
    <configuration>
        <linkXRef>false</linkXRef>
        <sourceEncoding>UTF-8</sourceEncoding>
        <targetJdk>17</targetJdk>
        <rulesets>
            <ruleset>${project.basedir}/pmd-ruleset.xml</ruleset>
        </rulesets>
        <includeTests>false</includeTests>
        <failOnViolation>false</failOnViolation>
    </configuration>
</plugin>
```
*Nota: Se estableció `<failOnViolation>false</failOnViolation>` para permitir que la fase de construcción compile el código por completo y genere un reporte integral con todos los fallos encontrados, en lugar de detener la ejecución ante la primera alerta.*

---

## 3. Elección de Métricas y Justificación (Enfoque ISO/IEC 25000)
Para evaluar el código de manera rigurosa y evitar falsos positivos o alertas triviales ("ruido"), se ha diseñado un archivo de configuración personalizado llamado `pmd-ruleset.xml`. 

Las reglas seleccionadas cubren 5 categorías esenciales de PMD, cada una mapeada directamente con las características de calidad del estándar **ISO/IEC 25000 (ISO 25010)**:

| Categoría PMD | Regla Específica | Descripción de la Regla | Característica ISO/IEC 25000 | Justificación Académica / Técnica |
| :--- | :--- | :--- | :--- | :--- |
| **Best Practices** | `UnusedPrivateField`, `UnusedLocalVariable`, `UnusedPrivateMethod` | Detecta variables, campos y métodos privados declarados pero nunca utilizados. | **Mantenibilidad** (Analizabilidad y Modificabilidad) | El código muerto aumenta la carga cognitiva del desarrollador y dificulta el mantenimiento sin aportar valor funcional. |
| **Best Practices** | `AvoidReassigningParameters` | Evita que se reasignen valores a los parámetros de entrada de un método. | **Fiabilidad** (Tolerancia a fallos) y **Mantenibilidad** | Reasignar parámetros puede generar efectos colaterales inesperados y dificulta el seguimiento del flujo de datos en la depuración. |
| **Code Style** | `ExtendsObject`, `ForLoopShouldBeWhileLoop` | Identifica redundancias sintácticas y fomenta estructuras de bucle más legibles. | **Mantenibilidad** (Analizabilidad) | Mejora la legibilidad del código facilitando que nuevos desarrolladores lo comprendan rápidamente. |
| **Design** | `CyclomaticComplexity` | Mide la complejidad ciclomática de McCabe (número de caminos independientes en el flujo). | **Mantenibilidad** (Analizabilidad y Capacidad de prueba) | Métodos con alta complejidad ciclomatica son propensos a bugs y requieren una cantidad excesiva de pruebas unitarias para lograr una cobertura completa. |
| **Design** | `ExcessiveMethodLength` y `ExcessiveClassLength` | Limita la longitud de los métodos (umbral: 60 líneas) y clases (umbral: 500 líneas). | **Mantenibilidad** (Modularidad y Cohesión) | Fomenta el principio de responsabilidad única. Métodos o clases gigantes ("God Classes") son difíciles de refactorizar y probar. |
| **Error Prone** | `EmptyCatchBlock` | Advierte sobre bloques `catch` de excepciones que están vacíos (excepciones silenciadas). | **Fiabilidad** (Tolerancia a fallos y Recuperabilidad) | Silenciar excepciones oculta fallos graves del sistema en tiempo de ejecución, impidiendo que el software responda de forma controlada ante un error. |
| **Performance** | `StringInstantiation`, `UseStringBufferForStringAppends` | Evita instanciaciones redundantes de `String` y la concatenación con el operador `+` en bucles. | **Eficiencia de Desempeño** (Utilización de recursos y Comportamiento temporal) | Evita la asignación masiva de objetos en memoria heap, reduciendo la frecuencia del Garbage Collector y optimizando el tiempo de respuesta. |
| **Security** | `InsecureCryptoIv` | Detecta el uso de vectores de inicialización (IVs) estáticos y cableados en cifrados criptográficos. | **Seguridad** (Confidencialidad e Integridad) | Los IVs reutilizados o predecibles facilitan ataques de criptoanálisis que rompen el cifrado. |
| **Security** | `HardCodedCryptoKey` | Advierte sobre claves de cifrado o secretos declarados directamente en el código fuente. | **Seguridad** (Confidencialidad y Autenticidad) | Las claves en código quedan expuestas en el repositorio y en los compilados, permitiendo descifrar datos a cualquier atacante con acceso al software. |

---

## 4. Descripción del Código Java Evaluado
El código fuente evaluado corresponde a **ProyectoDAGGS** ("Recetas"), un backend desarrollado en Java empleando el framework **Spring Boot** (versión 3.3.5) y **Spring Data JPA** para la capa de persistencia de datos.

### Ficha Técnica del Código Evaluado:
* **Fecha de Realización del Código:** 26 de enero de 2025 (Jan 26, 2025).
* **Fecha de Evaluación:** 1 de junio de 2026.
* **Versión de Java:** Java 17.
* **Arquitectura del Proyecto:** Arquitectura multicapa clásica en aplicaciones web/REST:
  - **Entidades (`entidades`)**: Modelado del dominio (Paciente, Medico, Cita, Receta, Farmacia, etc.) mapeado a una base de datos relacional MySQL/MariaDB mediante JPA.
  - **DAOs (`daos`)**: Interfaces que extienden `JpaRepository` para las operaciones CRUD.
  - **Servicios (`servicios`)**: Capa de negocio estructurada en interfaces e implementaciones (`ServiceImpl`).
  - **Controladores (`controladores`)**: Capa de presentación REST que expone endpoints HTTP en formato JSON.
* **Métricas de Tamaño Generales:**
  - **Número total de ficheros analizados:** 54 archivos `.java` (53 de código principal de la aplicación y 1 archivo de tests unitarios).
  - **Líneas de Código Totales (LOC):** 3.234 líneas de código Java.

---

## 5. Secuencia de Comandos Ejecutados
Para llevar a cabo el análisis, se ejecutaron las siguientes operaciones desde la terminal de comandos (PowerShell) dentro del directorio raíz del proyecto:

1. **Compilación y Limpieza del Proyecto**:
   Asegura que el código compile correctamente antes del análisis y que el directorio `target` esté limpio.
   ```powershell
   .\mvnw.cmd clean compile
   ```
2. **Ejecución del Análisis PMD**:
   Ejecuta el ciclo de vida de PMD definido por el plugin, el cual procesa los ficheros Java contra las reglas definidas en `pmd-ruleset.xml`.
   ```powershell
   .\mvnw.cmd pmd:pmd
   ```
3. **Resultado de la Ejecución**:
   El comando finalizó exitosamente (`BUILD SUCCESS`) y generó dos artefactos de reporte dentro del directorio `target/`:
   - Un archivo estructurado **XML** en `target/pmd.xml` ideal para procesamiento automatizado.
   - Un reporte **HTML** interactivo para consumo humano en `target/site/pmd.html`.

---

## 6. Listado de Errores Detectados y Mapeo con ISO/IEC 25000
El análisis estático detectó varias alertas importantes concentradas principalmente en la clase principal de la aplicación y en los controladores REST. A continuación se detallan las violaciones más significativas organizadas por su tipo, analizando su impacto en la calidad del software e indicando cómo deben solucionarse.

### A. Tipo de Violación: `UnusedPrivateMethod` (Categoría: Best Practices)
* **Localización:** `RecetasApplication.java`, Línea 75 (método `crearEntidades()`).
* **Severidad PMD:** Priority 3 (Medium).
* **Descripción:** Se detectó el método privado `crearEntidades` que está declarado en la clase principal pero no es invocado desde ningún punto del código.
* **Relación con ISO/IEC 25000 (Mantenibilidad / Analizabilidad y Modificabilidad):**
  - El código no utilizado ("código muerto") perjudica directamente la **analizabilidad** del producto. Cuando un desarrollador realiza tareas de mantenimiento, debe leer y comprender este fragmento de código inútil, aumentando la fatiga cognitiva y el tiempo de resolución de cambios. Además, dificulta la **modificabilidad** porque induce a errores al dar a entender que ciertas entidades se están creando al arrancar la aplicación cuando en realidad no es así.
* **Justificación de la Causa:**
  - En la clase `RecetasApplication.java`, dentro del método obligatorio `run` de la interfaz `CommandLineRunner` (líneas 70-72), se observa que la llamada a `crearEntidades();` se encuentra comentada. Dado que el método está marcado como `private`, el compilador y PMD detectan con precisión que es inaccesible.
* **Solución Propuesta:**
  - Si el código de inicialización ya no es necesario, el método completo debe eliminarse para limpiar la base de código. Si por el contrario se pretendía usar para poblar la base de datos en fase de pruebas, se debe descomentar su llamada en el método `run()`.

---

### B. Tipo de Violación: `ExcessiveMethodLength` (Categoría: Design)
* **Localización:** `RecetasApplication.java`, Líneas 75-187 (método `crearEntidades()`).
* **Severidad PMD:** Priority 3 (Medium).
* **Descripción:** El método `crearEntidades()` tiene una extensión de 112 líneas de código, superando ampliamente el límite establecido de 60 líneas.
* **Relación con ISO/IEC 25000 (Mantenibilidad / Modularidad y Capacidad de Prueba):**
  - Métodos muy largos violan el principio de **cohesión** y el principio de responsabilidad única. Esto afecta negativamente a la **modularidad**, ya que el método asume la tarea de instanciar e insertar en base de datos nueve tipos de entidades diferentes (administradores, médicos, pacientes, citas, medicamentos, etc.). Además, degrada la **capacidad de prueba (testability)**: resulta sumamente complejo diseñar pruebas unitarias aisladas para un método monolítico que interactúa con tantos DAOs diferentes al mismo tiempo.
* **Solución Propuesta:**
  - Refactorizar el método aplicando técnicas de extracción de métodos (Extract Method). Se pueden crear sub-métodos cohesivos como `crearMedicos()`, `crearPacientes()` o `crearCitas()`. Una solución arquitectónicamente superior sería delegar esta funcionalidad a un servicio especializado de inicialización de datos (ej. `DatabaseSeederService`).

---

### C. Tipo de Violación: `CyclomaticComplexity` (Categoría: Design)
* **Localización:** `PacienteController.java`, Líneas 175-231 (método `crear(Paciente)`).  
  *(Nota: Esta violación también se repite sistemáticamente en el método `crear` de todos los controladores REST del proyecto, como `FarmaciaController` con una complejidad de 26, y `MedicoController` con una complejidad de 23).*
* **Severidad PMD:** Priority 3 (Medium).
* **Descripción:** El método `crear(Paciente)` de la API REST presenta una complejidad ciclomática de **32**, superando el límite máximo recomendado de 10.
* **Relación con ISO/IEC 25000 (Mantenibilidad / Capacidad de Prueba y Analizabilidad):**
  - La complejidad de McCabe mide el número de caminos independientes del método. Una complejidad de 32 significa que existen 32 flujos lógicos posibles para ejecutar la función. Esto destruye la **capacidad de prueba**, puesto que para garantizar una cobertura de código (code coverage) del 100% se requeriría diseñar un mínimo de 32 casos de prueba unitaria diferentes únicamente para este método. Asimismo, reduce drásticamente la **analizabilidad** del código, haciéndolo extremadamente denso y propenso a que futuros cambios introduzcan bugs colaterales.
* **Justificación de la Causa (Análisis de Código):**
  - Al revisar el código de `PacienteController.java`, se observa que el método `crear` realiza una validación manual exhaustiva e imperativa de cada campo de la entidad recibida por parámetro:
    ```java
    if(nombre == null || nombre.isBlank()) { throw new WrongParameterException("..."); }
    if(apellidos == null || apellidos.isBlank()) { throw new WrongParameterException("..."); }
    // ... así sucesivamente para DNI, tarjeta sanitaria, dirección, teléfono, médico, etc.
    ```
    Cada sentencia `if` y cada operador lógico `||` añade un nuevo nodo de decisión en el grafo de flujo del programa, disparando la complejidad ciclomática.
* **Solución y Recomendación Arquitectónica (Para una nota excelente en la defensa):**
  - El diseño actual sufre de un acoplamiento estrecho y una fuga de responsabilidades: el controlador REST está asumiendo las reglas de validación que pertenecen al modelo de dominio.
  - **Refactorización Limpia**: Dado que la aplicación utiliza Spring Boot con Hibernate/JPA, la solución correcta es emplear **Jakarta Bean Validation**. Debemos mover las reglas de validación directamente a la clase de entidad `Paciente.java` mediante anotaciones declarativas (ej. `@NotBlank`, `@NotNull`, `@Size`):
    ```java
    @Entity
    @Data
    public class Paciente extends Usuario {
        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        @NotBlank(message = "Los apellidos son obligatorios")
        private String apellidos;
        
        @NotNull(message = "La dirección es obligatoria")
        @Embedded
        private Direccion direccion;
        // ...
    }
    ```
  - En el controlador `PacienteController.java`, eliminamos por completo las decenas de sentencias `if`. Como el parámetro ya incluye la anotación `@Valid`, el framework Spring validará el objeto automáticamente antes de entrar al método. Si ocurre un fallo de validación, Spring lanzará una excepción global controlada (`MethodArgumentNotValidException`), la cual será capturada por el manejador global de excepciones (`GlobalExceptionHandler`).
  - **Resultado**: La complejidad ciclomática del método `crear` se reduce de **32** a **1**, eliminando el código redundante en el controlador y mejorando drásticamente la mantenibilidad global de la aplicación.

---

### D. Tipo de Violación: `InsecureCryptoIv` (Categoría: Security)
* **Localización:** `Usuario.java`, Línea 164 (método `getCipherIv()`).
* **Severidad PMD:** Priority 3 (Medium).
* **Descripción:** Se ha instanciado un vector de inicialización (IV) criptográfico usando un array de bytes estático y predecible (todo ceros: `new byte[] { 0, 0, ... }`) para pasar a `IvParameterSpec`.
* **Relación con ISO/IEC 25000 (Seguridad / Confidencialidad e Integridad):**
  - Esta vulnerabilidad atenta de forma crítica contra la **confidencialidad** y la **integridad** del software. El estándar de calidad ISO 25000 evalúa en su apartado de seguridad que los datos sensibles permanezcan protegidos. El uso de un IV estático hace que el texto cifrado sea vulnerable a ataques de repetición o criptoanálisis (como el ataque de diccionario o de frecuencias de bloques), permitiendo a un atacante comprometer la confidencialidad de la información transmitida o almacenada.
* **Justificación de la Causa:**
  - En la clase base `Usuario.java`, el método `getCipherIv()` declara y retorna un vector de inicialización cableado con bytes fijos para ser utilizado como parámetro de cifrado de credenciales. PMD analiza las llamadas a `IvParameterSpec` y detecta que el vector no es dinámico ni aleatorio.
* **Solución Propuesta:**
  - Emplear un generador de números pseudoaleatorios criptográficamente fuerte (`java.security.SecureRandom`) para generar un IV único y aleatorio por cada operación de cifrado, y guardarlo junto al mensaje cifrado si es necesario:
    ```java
    public byte[] generarIvSeguro() {
        byte[] ivBytes = new byte[16];
        java.security.SecureRandom random = new java.security.SecureRandom();
        random.nextBytes(ivBytes);
        return ivBytes;
    }
    ```

---

### E. Tipo de Violación: `HardCodedCryptoKey` (Categoría: Security)
* **Localización:** `Usuario.java`, Líneas 168-169 (método `getCipherKey()`).
* **Severidad PMD:** Priority 3 (Medium).
* **Descripción:** Declaración en código de una clave simétrica AES cableada (`keyBytes`) para construir un `SecretKeySpec`.
* **Relación con ISO/IEC 25000 (Seguridad / Confidencialidad y Autenticidad):**
  - Hardcodear claves de cifrado en el código compromete el principio de **confidencialidad** y **autenticidad** de la ISO 25000. Al compilar el proyecto, la clave queda grabada en texto plano dentro de los archivos `.class` y del `.jar` final. Cualquier persona con acceso al binario puede descompilarlo fácilmente (con herramientas como JD-GUI o Jadx) y extraer la clave, comprometiendo todo el sistema criptográfico del software.
* **Justificación de la Causa:**
  - En `Usuario.java`, el método `getCipherKey()` inicializa directamente un array de 16 bytes fijos (`0x01, 0x02, ...`) en el código y los utiliza para generar una clave simétrica de cifrado. PMD detecta de forma precisa esta constante utilizada como argumento en el constructor del validador de claves.
* **Solución Propuesta:**
  - Las claves criptográficas deben gestionarse fuera de la base de código. Se deben usar variables de entorno seguras, almacenes de claves (como **Java KeyStore - JKS**) o servicios de bóveda de secretos remotos (como **HashiCorp Vault**, **AWS Secrets Manager** o **Spring Cloud Config Secrets**), inyectando el valor en tiempo de ejecución de manera segura.

---

## 7. Conclusiones
La aplicación de la herramienta de análisis estático **PMD** sobre el código del proyecto "Recetas" ha demostrado ser sumamente valiosa para identificar oportunidades clave de mejora que impactan de manera directa en la **Mantenibilidad** del software según el estándar **ISO/IEC 25000**.

Aunque el proyecto presenta un diseño modular correcto dividiendo la lógica en controladores, servicios y DAOs, el análisis estático reveló un antipatrón recurrente en la capa de exposición (controladores REST): la validación imperativa y manual de los datos entrantes. Esto infla artificialmente la complejidad ciclomática de los métodos y duplica código innecesariamente. 

Implementar las recomendaciones sugeridas (como delegar la validación a anotaciones declarativas del dominio e implementar un servicio de seeding estructurado) permitiría pasar de una base de código funcional pero frágil, a un producto robusto, testeable y alineado con los máximos estándares de calidad que demanda la industria del software.
