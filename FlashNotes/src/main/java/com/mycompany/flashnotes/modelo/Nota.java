package com.mycompany.flashnotes.modelo;

import java.io.Serializable;

/**
 * La clase Nota representa un objeto de datos simple (POJO - Plain Old Java Object)
 * que encapsula el contenido de una nota de texto.
 *
 * Esta clase es parte del modelo del patrón de diseño MVC y se encarga
 * de gestionar la información de una sola nota, sin preocuparse por la
 * interfaz de usuario o la lógica de negocio.
 *
 * @author jesus
 */
public class Nota implements Serializable {
    
    // El contenido de la nota. Es el dato principal de este objeto.
    private String contenido;

    /**
     * Constructor de la clase Nota.
     *
     * @param contenido El texto inicial de la nota.
     */
    public Nota(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene el contenido de la nota.
     *
     * @return El contenido de la nota como una cadena de texto.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece o actualiza el contenido de la nota.
     *
     * @param contenido El nuevo texto para la nota.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}