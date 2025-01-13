package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;

    private int numeroDescargas;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autor = new HashSet<>();


    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();

        // Si `idioma` contiene elementos, seleccionamos el primero, de lo contrario se maneja como nulo.
        if (!datosLibro.idioma().isEmpty()) {
            this.idiomas = Idiomas.fromString(datosLibro.idioma().get(0));
        } else {
            this.idiomas = null; // O podrías asignar un idioma por defecto si lo prefieres
        }

        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idiomas idiomas) {
        this.idiomas = idiomas;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas =  numeroDescargas;
    }

    public Set<Autor> getAutores() {
        return autor;
    }

    public void setAutores(Set<Autor> autores) {
        this.autor = autores;
    }
    @Override
    public String toString() {

        // Usamos streams para extraer los nombres de los autores y unirlos con comas
        String nombresAutores = autor.stream()
                .map(Autor::getNombre) // Obtiene el nombre de cada autor
                .reduce((a, b) -> a + " y  " + b) // Combina los nombres con comas
                .orElse("Sin autores"); // Maneja el caso en que no haya autores

        return "-------- Libro --------- " + '\n' +
                "Titulo     : " + titulo + '\n' +
                "Autores    : " + nombresAutores+ '\n' +
                "Idioma     : " + idiomas + '\n' +
                "N°Descargas: " + numeroDescargas + '\n' +
                "-----------------------" +'\n' ;

    }

}
