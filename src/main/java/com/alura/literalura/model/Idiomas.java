package com.alura.literalura.model;

import java.util.HashMap;
import java.util.Map;

public enum Idiomas {
    // Idiomas con distintas variantes
    en("en", "ingles", "inglés", "Inglés", "INGLÉS"),
    es("es", "espanol", "español", "Español", "ESPAÑOL"),
    fr("fr", "frances", "francés", "Francés", "FRANCÉS"),
    hu("hu", "hungaro", "húngaro", "Húngaro", "HÚNGARO"),
    fi("fi", "fines", "finés", "Finés", "FINÉS"),
    pt("pt", "portugues","portugués", "Portugués", "PORTUGUÉS");

    private final String codigo;
    private final String[] variantes;
    private static final Map<String, Idiomas> mapaIdiomas = new HashMap<>();

    static {
        // Inicializar el mapa con todas las variantes posibles
        for (Idiomas idioma : Idiomas.values()) {
            mapaIdiomas.put(idioma.codigo.toLowerCase(), idioma); // Agregar el código principal
            for (String variante : idioma.variantes) { // Agregar todas las variantes
                mapaIdiomas.put(variante.toLowerCase(), idioma);
            }
        }
    }

    // Constructor que acepta el código y variantes
    Idiomas(String codigo, String... variantes) {
        this.codigo = codigo;
        this.variantes = variantes;
    }

    // Metodo para obtener un idioma desde una cadena
    public static Idiomas fromString(String texto) {
        Idiomas idioma = mapaIdiomas.get(texto.toLowerCase());
        if (idioma == null) {
            throw new IllegalArgumentException("No se encontró ningún idioma para: " + texto);
        }
        return idioma;
    }

    public String getCodigo() {
        return codigo;
    }
}