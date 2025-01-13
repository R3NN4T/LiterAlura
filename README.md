# LiterAlura  

Este proyecto es una aplicación desarrollada en **Java** utilizando el framework **Spring Boot** para gestionar libros y autores, consumiendo datos de la API de Gutendex.

## 📚 Características principales

- Buscar libros por título en la base de datos o en la API de Gutendex.
- Registrar libros y autores en una base de datos evitando duplicados.
- Listar libros por idioma.
- Listar autores vivos en un año específico.
- Validaciones para entradas de datos en los métodos interactivos.

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Hibernate** (para ORM)
- **Gutendex API** (para obtener información de libros)

## 🚀 Configuración del proyecto

### Prerrequisitos

Asegúrate de tener instalados los siguientes programas:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o superior.
- [Maven](https://maven.apache.org/) (para gestionar dependencias).
- Un IDE como [IntelliJ IDEA](https://www.jetbrains.com/idea/) o [Eclipse](https://www.eclipse.org/).

### Instalación

1. Clona este repositorio en tu máquina local:

   ```bash
   git clone https://github.com/tu-usuario/tu-repositorio.git
   cd tu-repositorio

2. Compila y construye el proyecto con Maven:

   ```bash
   mvn clean install
   
3. Ejecuta la Aplicacion:

   ```bash
   mvn spring-boot:run

### Estructura del Proyecto

&nbsp;&nbsp;&nbsp;&nbsp;src/main/java/com.alura.literalura   
&nbsp;&nbsp;&nbsp;&nbsp;│  
&nbsp;&nbsp;&nbsp;&nbsp;├── model : Entidades como Libro y Autor  
&nbsp;&nbsp;&nbsp;&nbsp;├── principal : Donde se genera la logica  
&nbsp;&nbsp;&nbsp;&nbsp;│── repository : Repositorios JPA  
&nbsp;&nbsp;&nbsp;&nbsp;├── service : Servicios para consumir la API  
&nbsp;&nbsp;&nbsp;&nbsp;└── LiteraluraAplication Clases main  



## 🌟 Funcionalidades destacadas
Buscar libros por título
El programa busca primero en la base de datos si el libro ya está registrado. Si no lo encuentra, realiza una búsqueda en la API de Gutendex y permite registrarlo.

Listar libros por idioma
Puedes filtrar los libros en la base de datos por idioma.

Listar autores vivos por un año
Se filtran los autores que estaban vivos en un año específico.

## 🛡️ Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.
