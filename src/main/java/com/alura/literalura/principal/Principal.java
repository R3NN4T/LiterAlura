package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final ConvierteDatos conversor = new ConvierteDatos();
    private LibroService libroServicio;
    private AutorService autorServicio;

    public Principal(LibroService libroService, AutorService autorService) {
        this.libroServicio = libroService;
        this.autorServicio = autorService;
    }


    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    =============== L I T E R A L U R A ===============
                    |                                                 |
                    |  1 - Buscar libro por Título                    |
                    |  2 - Listar libros registrados                  |
                    |  3 - Listar autores registrados                 |
                    |  4 - Listar autores vivos en un año determinado |
                    |  5 - Listar libros por idioma                   |
                    |  0 - Salir                                      |
                    |                                                 |
                    ===================================================
                    Ingresa una de las opciones:
                    
                    """);


            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } else {
                System.out.println("Por favor, selecciona una opción válida.");
                teclado.nextLine(); // Consumir entrada no válida
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine().trim();

        // Buscar en la base de datos
        System.out.println("Buscando el libro en la base de datos...");
        Optional<Libro> libroExistente = libroServicio.buscarLibroPorTitulo(nombreLibro);

        if (libroExistente.isPresent()) {
            // Si el libro ya está en la base de datos, se muestra y se termina el metodo
            System.out.println("El libro ya está registrado en la base de datos:");
            System.out.println(libroExistente.get());
            return;
        }

        // Buscar en la API si no está en la base de datos
        System.out.println("El libro no está en la base de datos. Buscando en la API de Gutendex...");
        var url = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        DatosResults resultados = conversor.obtenerDatos(url, DatosResults.class);

        Optional<DatosLibro> libroBuscado = resultados.resultadosLibros().stream()
                .filter(l -> l.titulo().equalsIgnoreCase(nombreLibro))
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibro datosLibro = libroBuscado.get();

            // Crear entidad `Libro` y asignar datos
            Libro nuevoLibro = new Libro(datosLibro);

            // Crear y guardar los autores
            for (DatosAutor datosAutor : datosLibro.autores()) {
                Autor autor = autorServicio.obtenerORegistrarAutor(datosAutor.nombre(), datosAutor);

                // Establecer relación bidireccional solo si no existe
                if (!nuevoLibro.getAutores().contains(autor)) {
                    nuevoLibro.getAutores().add(autor);
                    autor.getLibros().add(nuevoLibro);
                }
            }

            // Guardar el libro en la base de datos
            try {
                libroServicio.guardarLibro(nuevoLibro);
                System.out.println("El libro y sus autores han sido registrados en la base de datos.");
                System.out.println(nuevoLibro);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Error: El libro ya está registrado.");
            }
        } else {
            System.out.println("No se encontró un libro con ese título en la API.");
        }
    }




    private void listarLibrosRegistrados() {
        System.out.println("Libros registrados en la base de datos:");
        List<Libro> libros = libroServicio.listarLibrosRegistrados();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("================== LIBROS REGISTRADOS =================="+ '\n');
            libros.forEach(System.out::println);
            System.out.println("============================================================");
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("Autores registrados en la base de datos:");
        List<Autor> autores = autorServicio.listarAutoresRegistrados();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("================== AUTORES REGISTRADOS =================="+ '\n');
            autores.forEach(System.out::println);
            System.out.println("============================================================");
        }
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Introduce el año para buscar autores vivos:");

        // Validar entrada del año
        int anio;
        while (true) {
            try {
                String entrada = teclado.nextLine().trim();
                anio = Integer.parseInt(entrada);

                if (anio < 0) {
                    System.out.println("Por favor, introduce un año válido .");
                } else {
                    break; // Salir del bucle si el año es válido
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduce un año válido.");
            }
        }

        // Filtrar autores por las fechas
        int finalAnio = anio;
        List<Autor> autores = autorServicio.listarAutoresRegistrados().stream()
                .filter(autor -> {
                    Integer fechaNacimiento = autor.getFechaNacimiento(); // Asumiendo que es Integer
                    Integer fechaFallecido = autor.getFechaFallecido();  // Asumiendo que es Integer

                    // Validar condiciones de fechas
                    return fechaNacimiento != null && fechaNacimiento <= finalAnio &&
                            (fechaFallecido == null || fechaFallecido > finalAnio);
                })
                .collect(Collectors.toList());

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        } else {
            System.out.println("============= AUTORES VIVOS EN EL AÑO " + anio + " ============="+ '\n');
            autores.forEach(System.out::println);
            System.out.println("============================================================");
        }
    }


    private void listarLibrosPorIdioma() {
        System.out.println("Introduce el idioma para listar libros (por ejemplo: 'en' o 'ingles' para inglés):");

        // Validar entrada del idioma
        Idiomas idioma = null;
        while (idioma == null) {
            String entrada = teclado.nextLine().trim();
            try {
                idioma = Idiomas.fromString(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Idioma no válido. Por favor, introduce un idioma válido (ejemplo: 'español', 'ingles').");
            }
        }

        List<Libro> libros = libroServicio.buscarLibroPorIdiomas(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            System.out.println("============= LIBROS EN EL IDIOMA " + idioma + " ============="+ '\n');
            libros.forEach(System.out::println);
            System.out.println("============================================================");
        }
    }


}
