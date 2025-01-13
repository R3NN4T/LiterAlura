package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepositorio;


    @Transactional
    public Autor obtenerORegistrarAutor(String nombreAutor, DatosAutor datosAutor) {
        Optional<Autor> autorExistente = autorRepositorio.findByNombre(nombreAutor);//Buscamos el autor
        if (autorExistente.isPresent()) {
            return autorExistente.get(); // Este autor es gestionado porque fue recuperado del repositorio.
        } else {
            Autor nuevoAutor = new Autor(datosAutor);
            return autorRepositorio.save(nuevoAutor); // Guardamos el Autor
        }
    }

    public List<Autor> listarAutoresRegistrados() {
        return autorRepositorio.findAll();
    }

}
