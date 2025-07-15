# 🎓 **Módulo de Gestión de Estudiantes  con TDD**

Este proyecto es una aplicación Java que gestiona un CRUD (Crear, Leer, Actualizar, Eliminar) de estudiantes, desarrollado siguiendo estrictamente la metodología de Desarrollo Guiado por Pruebas (TDD). El objetivo principal fue construir una base de código robusta, bien probada y con un diseño limpio y escalable.

## **Características Principales**

* **Desarrollo 100% Guiado por Pruebas (TDD):** Cada funcionalidad fue escrita para satisfacer una prueba previamente fallida, siguiendo el ciclo Red-Green-Refactor.
* **Arquitectura en Capas:** Clara separación entre la capa de Servicio (lógica de negocio y validaciones) y la Capa de Acceso a Datos (DAO) (interacción con la base de datos).
* **Pruebas Unitarias Aisladas:** Se utilizó Mockito para simular (mock) la capa DAO, permitiendo probar la lógica de negocio del servicio en total aislamiento, sin necesidad de una base de datos real.
* **Pruebas de Integración:** Pruebas que validan la correcta interacción con una base de datos SQLite en memoria.
* **Alta Cobertura de Código:** e configuró **JaCoCo** para medir la cobertura de las pruebas, alcanzando y superando el umbral del 80% requerido.
* **Gestión de Dependencias con Maven:** Todas las librerías necesarias son gestionadas de forma centralizada a través del pom.xml.

## **Tecnologías Utilizadas**

* **Lenguaje: Java 21**
* **Gestor de Proyecto: Apache Maven**
* **Pruebas Unitarias:** JUnit 5
* **Simulación (Mocking):** Mockito
* **Base de Datos:** SQLite-JDBC (Driver de base de datos)
* **Cobertura de Código:** JaCoCo
* **IntelliJ IDEA CE**

## **Cómo Configurar y Ejecutar el Proyecto**

### **Prerrequisitos**

* Tener instalado Java JDK 11 o superior (el proyecto está configurado para la versión 21).
* Tener instalado Apache Maven.

### **Pasos**
1. **Clonar el Repositorio**  
   `git clone \[URL-DE-TU-REPOSITORIO-EN-GITHUB\]`
   `cd TDD-Proyecto-Basico`

2. **Compilar, Probar y Verificar**  
   Este es el comando principal. Limpia el proyecto, compila todo el código, ejecuta todas las pruebas (unitarias y de integración) y finalmente verifica que la cobertura de código cumple con el mínimo del 80%.  
   `mvn clean verify`
   Al finalizar, deberías ver un mensaje de `BUILD SUCCESS`.
   
3. **Generar y Ver el Reporte de Cobertura**  
   El comando anterior ya genera el reporte. Para visualizarlo:
    * Abre el siguiente archivo en tu navegador web:  
      `\[RUTA-DEL-PROYECTO\]/target/site/jacoco/index.html
    * Podrás explorar interactivamente la cobertura de cada clase y método.

4. **Base de Datos (SQLonline)**  
   Este proyecto está diseñado para interactuar con una base de datos simulada a través de SQLonline. 
   1.  **Acceder a SQLonline:** Visita [https://sqliteonline.com/](https://sqliteonline.com/)
   2.  **Crear la Tabla:** Copia el contenido del archivo `src/main/resources/schema.sql` y pégalo en el editor de SQLonline. Ejecuta las sentencias para crear la tabla `Estudiantes` y poblarla con datos de ejemplo.

## **Estructura del Proyecto**
.<br>
├── pom.xml                 # Archivo de configuración de Maven<br>
└── src<br>
├── main<br>
│   ├── java/com/educacion<br>
│   │   ├── dao         # Capa de Acceso a Datos (Interfaz e Implementación)<br>
│   │   ├── modelo      # Clases de dominio (POJO Estudiante)<br>
│   │   └── servicio    # Capa de lógica de negocio<br>
│   └── resources<br>
│       └── schema.sql  # Script de definición de la base de datos<br>
└── test<br>
└── java/com/educacion<br>
├── dao         # Pruebas de Integración para el DAO<br>
├── modelo      # Pruebas Unitarias para el Modelo<br>
└── servicio    # Pruebas Unitarias para el Servicio (con Mockito)<br>

---
## **📖 Documentación adicional**

* [Documentación JUnit](https://junit.org/)
* [Mockito](https://site.mockito.org/)
* [Jacoco](https://www.jacoco.org/)
* [Principios SOLID y refactorización](https://refactoring.com/)

---

## **👩‍💻 Autoría y contribución**

Este proyecto fue desarrollado como práctica integral de automatización de pruebas y TDD. Puede utilizarse como base para futuros módulos, aplicando los principios SOLID, técnicas de mocking y buenas prácticas de cobertura.