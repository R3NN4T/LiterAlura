package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer fechaNacimiento;

    private Integer fechaFallecido;


    @ManyToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();

    public Autor(DatosAutor datosAutores) {
        this.nombre = datosAutores.nombre();
        this.fechaNacimiento = datosAutores.fechaNacimiento();
        this.fechaFallecido = datosAutores.fechaMuerte();
    }
    public Autor(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecido() {
        return fechaFallecido;
    }

    public void setFechaFallecido(Integer fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    // Metodo toString para mostrar la información del autor.
    @Override
    public String toString() {
        return "-------- Autor -------- " + '\n' +
                " Nombre(s): " + nombre + '\n' +
                " Año de nacimiento: " + fechaNacimiento +'\n' +
                " Año de fallecimiento: " + fechaFallecido + '\n' +
                "-----------------------" +'\n' ;

    }

}
