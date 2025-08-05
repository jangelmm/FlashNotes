package com.mycompany.flashnotes.modelo;

import com.mycompany.flashnotes.persistencia.NotaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase GestorNotas es el corazón del modelo de la aplicación.
 * Es responsable de la lógica de negocio relacionada con la gestión de notas,
 * como crear, eliminar, guardar, buscar y acceder a las notas.
 *
 * Utiliza una instancia de NotaDAO para delegar las operaciones de
 * persistencia (guardar y cargar en archivos), manteniendo la separación de responsabilidades.
 *
 * @author jesus
 */
public class GestorNotas {
    
    // Una lista para almacenar los objetos Nota en memoria.
    private final List<Nota> notas;
    
    // Una instancia de NotaDAO para manejar las operaciones de archivo.
    private final NotaDAO notaDAO;

    /**
     * Constructor de la clase GestorNotas.
     * Inicializa la lista de notas y la instancia de NotaDAO.
     */
    public GestorNotas() {
        this.notas = new ArrayList<>();
        this.notaDAO = new NotaDAO();
    }

    /**
     * Crea una nueva nota con el contenido especificado y la añade a la lista.
     *
     * @param contenido El texto inicial para la nueva nota.
     */
    public void crearNota(String contenido) {
        notas.add(new Nota(contenido));
    }

    /**
     * Elimina una nota de la lista en el índice especificado.
     *
     * @param indice El índice de la nota a eliminar.
     */
    public void eliminarNota(int indice) {
        if (indice >= 0 && indice < notas.size()) {
            notas.remove(indice);
        }
    }

    /**
     * Elimina todas las notas de la lista.
     */
    public void eliminarTodas() {
        notas.clear();
    }

    /**
     * Guarda una nota específica en un archivo.
     * Esta operación delega la tarea a la clase NotaDAO.
     *
     * @param indice El índice de la nota a guardar.
     * @param rutaArchivo La ruta del archivo donde se guardará la nota.
     * @return true si la nota se guardó exitosamente, false en caso contrario.
     */
    public boolean guardarNota(int indice, String rutaArchivo) {
        if (indice >= 0 && indice < notas.size()) {
            Nota notaAGuardar = notas.get(indice);
            return notaDAO.guardarNotaEnArchivo(notaAGuardar, rutaArchivo);
        }
        return false;
    }

    /**
     * Busca notas que contengan un texto específico (sin distinción entre mayúsculas y minúsculas).
     *
     * @param texto El texto a buscar dentro de las notas.
     * @return Una lista de los índices de las notas que contienen el texto.
     */
    public List<Integer> buscar(String texto) {
        List<Integer> resultados = new ArrayList<>();
        String textoLower = texto.toLowerCase();
        for (int i = 0; i < notas.size(); i++) {
            if (notas.get(i).getContenido().toLowerCase().contains(textoLower)) {
                resultados.add(i);
            }
        }
        return resultados;
    }

    /**
     * Obtiene una copia de la lista de notas.
     *
     * @return Una nueva lista que contiene todas las notas.
     */
    public List<Nota> getNotas() {
        return new ArrayList<>(notas);
    }
    
    /**
     * Obtiene una nota específica por su índice.
     *
     * @param indice El índice de la nota deseada.
     * @return El objeto Nota en el índice especificado, o null si el índice es inválido.
     */
    public Nota getNota(int indice){
        if (indice >= 0 && indice < notas.size()) {
            return notas.get(indice);
        }
        return null;
    }

    /**
     * Obtiene una lista con el contenido de todas las notas.
     *
     * @return Una lista de cadenas de texto, donde cada cadena es el contenido de una nota.
     */
    public List<String> getContenidoNotas() {
        List<String> contenidos = new ArrayList<>();
        for (Nota nota : notas) {
            contenidos.add(nota.getContenido());
        }
        return contenidos;
    }
    
    /**
     * Actualiza el contenido de una nota en el índice especificado.
     *
     * @param indice El índice de la nota a actualizar.
     * @param contenido El nuevo texto para la nota.
     */
    public void actualizarContenidoNota(int indice, String contenido) {
        if (indice >= 0 && indice < notas.size()) {
            notas.get(indice).setContenido(contenido);
        }
    }    
}