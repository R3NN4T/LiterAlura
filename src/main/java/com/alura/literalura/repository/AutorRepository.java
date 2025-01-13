package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    //Buscar el autor por nombre
    Optional<Autor> findByNombre(String nombre);

    //Busca los autores vivos por un determinado año    
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento < :anioAutor AND a.fechaFallecido > :anioAutor")
    List<Autor> buscarAutoresVivosPorAnio(int anioAutor);

    //Busca el autor por nombre usando @Query
    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombreAutor%")
    List<Autor> buscarAutorPorNombre(String nombreAutor);

}
