package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import com.alura.literalura.model.Idiomas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    //Buscar el libro por el titulo
    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) = LOWER(:titulo)")
    Optional<Libro> findByTitulo(@Param("titulo") String titulo);

    //Busca los libros por idioma
    @Query("SELECT l FROM Libro l WHERE l.idiomas = :idiomas")
    List<Libro> findByIdiomas(Idiomas idiomas);

}
