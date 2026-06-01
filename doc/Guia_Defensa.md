# Guía de Preparación para la Defensa Oral: Práctica 4 (PMD + ISO 25000)
**Asignatura:** ACCSI (Análisis de la Calidad del Código y Seguridad en la Ingeniería del Software)  
**Objetivo:** Obtener la máxima calificación (10/10) demostrando solvencia teórica y práctica ante el tribunal/profesor.

---

## 1. Preguntas Frecuentes y Respuestas de Alto Nivel

### P1: ¿Qué es el Análisis Estático de Código y en qué se diferencia del Análisis Dinámico?
* **Respuesta clave**: 
  - El **Análisis Estático** examina el código fuente (o el bytecode compiling) sin llegar a ejecutar el programa. Busca identificar errores estructurales, malas prácticas, deuda técnica o vulnerabilidades lógicas tempranamente.
  - El **Análisis Dinámico** evalúa el comportamiento del programa *durante su ejecución* (mediante tests unitarios, de integración, perfilado de rendimiento, monitorización de fugas de memoria, etc.).
  - **Frase de impacto**: *"Son enfoques complementarios. El análisis estático previene fallos de diseño y mantiene la mantenibilidad a bajo coste, mientras que el dinámico asegura que el comportamiento funcional sea el esperado bajo condiciones reales".*

### P2: ¿Cómo funciona PMD internamente? ¿Qué es el AST?
* **Respuesta clave**:
  - PMD toma los archivos de código fuente (`.java`) y los procesa a través de un analizador sintáctico (parser) para transformarlos en un **Árbol de Sintaxis Abstracta (AST - Abstract Syntax Tree)**.
  - El AST es una representación en forma de árbol jerárquico de la estructura gramatical del código (donde cada nodo representa una clase, un método, una asignación, un bucle `if`, etc.).
  - Las reglas de PMD (escritas en Java o consultas XPath) recorren este árbol buscando patrones problemáticos (ejemplo: un nodo `CatchStatement` que no contiene ningún nodo hijo ejecutable, lo que delata un bloque `catch` vacío).

### P3: ¿Por qué decidiste integrar PMD mediante Maven y no instalar la herramienta local en tu sistema (CLI)?
* **Respuesta clave**:
  - Integrar PMD en el archivo `pom.xml` es la **mejor práctica a nivel industrial**. Permite que el análisis estático esté integrado nativamente en el ciclo de vida del proyecto (build lifecycle).
  - Al estar definido en Maven, cualquier miembro del equipo puede ejecutar exactamente el mismo análisis con un solo comando (`mvn pmd:pmd`), garantizando la homogeneidad de las reglas.
  - Además, facilita la integración en pipelines de Integración Continua (CI/CD) para que se ejecute de manera automatizada en cada Pull Request o Commit, bloqueando código que no cumpla con los estándares.

### P4: ¿Por qué no usaste el "ruleset" por defecto completo de PMD y creaste uno personalizado (`pmd-ruleset.xml`)?
* **Respuesta clave**:
  - Utilizar todas las reglas predeterminadas de PMD genera una gran cantidad de "ruido" y falsos positivos (alertas triviales o incompatibles con frameworks modernos).
  - Por ejemplo, reglas estrictas de nomenclatura o sobre constructores vacíos entran en conflicto con la metaprogramación de **Lombok** (que genera getters/setters y constructores automáticamente) o con los estándares de **Spring Boot**.
  - Crear un `pmd-ruleset.xml` personalizado nos permite seleccionar y justificar las métricas críticas que realmente impactan en la calidad del producto según la **ISO/IEC 25000**, demostrando criterio técnico y rigor metodológico.

### P5: Háblame de la Complejidad Ciclomática de McCabe. ¿Qué significa que un método tenga complejidad 32?
* **Respuesta clave**:
  - La **Complejidad Ciclomática** mide el número de caminos lineales independientes a través de un fragmento de código (generalmente un método). Se calcula basándose en el grafo de flujo de control del método (contando los puntos de decisión como `if`, `for`, `while`, `case` y operadores booleanos como `||` y `&&`).
  - Un valor de **32** en el método `crear` de `PacienteController` indica que existen 32 flujos de ejecución posibles.
  - **Implicación bajo la ISO 25000 (Mantenibilidad)**:
    - **Capacidad de prueba (Testability)**: Para garantizar una cobertura de caminos del 100% en las pruebas unitarias, se requeriría codificar al menos 32 casos de prueba diferentes. Esto encarece el mantenimiento y tiempo de desarrollo.
    - **Analizabilidad**: Un método con 32 bifurcaciones es sumamente difícil de comprender por un ser humano, aumentando el riesgo de introducir nuevos fallos al realizar modificaciones (degradando la **Modificabilidad**).

### P6: ¿Cuál es el problema de diseño (antipatrón) en las validaciones de los controladores de tu proyecto y cómo se resuelve de forma elegante?
* **Respuesta clave**:
  - **El Antipatrón**: Existe una fuga de responsabilidades. La lógica de validar si los datos de una entidad (como un `Paciente` o una `Farmacia`) son válidos se ha implementado de forma imperativa y manual dentro de los métodos `crear` de la capa de Controladores (mediante múltiples bloques `if(campo == null)`). Esto viola el principio de cohesión y el principio de responsabilidad única.
  - **La Solución Arquitectónica**: Implementar **validación declarativa** en el modelo de dominio mediante **Jakarta Bean Validation** (JSR 380). 
    1. Se añaden anotaciones como `@NotBlank` o `@NotNull` sobre las propiedades de la clase `Paciente.java`.
    2. En el controlador `PacienteController.java`, se retiran todas las sentencias `if` de validación y simplemente se conserva el parámetro anotado con `@Valid @RequestBody Paciente paciente`.
    3. Spring Boot interceptará la petición automáticamente y, si incumple las validaciones, disparará una excepción que capture el `GlobalExceptionHandler`.
  - **Resultado**: La complejidad ciclomática del método del controlador baja de **32** a **1**, haciendo el código infinitamente más mantenible, limpio y desacoplado.

### P7: ¿Qué vulnerabilidades de Seguridad detectó la herramienta y cómo se relacionan con la ISO 25000?
* **Respuesta clave**:
  - Se detectaron dos vulnerabilidades criptográficas críticas en la clase `Usuario.java`:
    1. **InsecureCryptoIv**: En el método `getCipherIv()`, el uso de un vector de inicialización (IV) estático y cableado (`new byte[] { 0, 0, ... }`) para cifrado AES.
    2. **HardCodedCryptoKey**: En el método `getCipherKey()`, el uso de una clave criptográfica simétrica cableada en el código fuente (`0x01, 0x02, ...`).
  - **Relación con la ISO 25000 (Seguridad)**: Ambas atentan directamente contra las subcaracterísticas de **Confidencialidad** e **Integridad**. Un IV constante hace que el cifrado sea vulnerable a análisis de patrones y ataques de reproducción. Por otro lado, una clave hardcodeada en texto plano es extremadamente fácil de extraer descompilando el código (`.class`), invalidando por completo la seguridad del sistema.
  - **Solución**: Generar el IV dinámicamente en cada cifrado usando un generador criptográficamente fuerte (`java.security.SecureRandom`). Para las claves, extraerlas fuera del código fuente e inyectarlas mediante variables de entorno en el servidor o almacenes de credenciales seguros (como AWS Secrets Manager, HashiCorp Vault o Java KeyStore).

---

## 2. Conceptos Teóricos Clave de ISO/IEC 25000 para Memorizar

Para justificar las violaciones ante la profesora, utiliza siempre estos términos exactos del estándar:

1. **Mantenibilidad (Maintainability)**: La facilidad con la que un software puede ser modificado para corregir fallos, mejorar rendimiento o adaptarlo.
   - *Modularidad*: Capacidad de que los componentes influyan mínimamente en otros ante cambios (afectado por clases gigantes).
   - *Analizabilidad*: Facilidad para diagnosticar deficiencias o causas de fallos en el código (afectado por código muerto y alta complejidad).
   - *Modificabilidad*: Capacidad para realizar cambios de forma efectiva y eficiente sin introducir fallos colaterales.
   - *Capacidad de ser probado (Testability)*: Facilidad con la que se pueden establecer criterios de prueba y ejecutar tests (afectado directamente por la complejidad ciclomática).

2. **Fiabilidad (Reliability)**: Capacidad del sistema para realizar sus funciones bajo condiciones específicas.
   - *Tolerancia a fallos (Fault Tolerance)*: Capacidad del sistema para operar con normalidad a pesar de fallos en el software o hardware (afectado por capturar excepciones vacías: `EmptyCatchBlock`).

3. **Eficiencia de Desempeño (Performance Efficiency)**: Relación entre el nivel de rendimiento del software y los recursos utilizados.
   - *Comportamiento temporal*: Tiempos de respuesta y procesamiento (afectado por concatenar cadenas con `+` en bucles, obligando al recolector de basura a trabajar de más).
   - *Utilización de recursos*: Cantidad de recursos (memoria heap, CPU) que consume el programa.

---

## 3. Estrategia de Exposición para la Defensa
1. **Comienza con el "Por Qué"**: Explica que querías hacer un análisis realista, por lo que integraste PMD en Maven para simular un flujo de desarrollo moderno e industrial.
2. **Presenta el Diagnóstico**: Explica que el análisis arrojó un código bastante limpio en servicios y lógica de datos, pero destapó problemas repetitivos de diseño en la capa de controladores y código muerto en la aplicación principal.
3. **Muestra una Violación y su Solución**: No te limites a leer la lista de errores. Céntrate en la complejidad ciclomática de los controladores. Explica que la complejidad es 32, detalla el por qué (validaciones imperativas manuales) y **propón la solución limpia con Jakarta Bean Validation**. Esto demuestra que no solo sabes usar la herramienta, sino que comprendes el diseño de software orientado a objetos y la arquitectura de Spring Boot.
4. **Cierra con la ISO 25000**: Relaciona siempre cada hallazgo con el estándar (Modularidad, Capacidad de ser probado, Tolerancia a fallos). Eso es exactamente lo que la rúbrica de un máster busca evaluar.
