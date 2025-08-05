package com.mycompany.flashnotes.persistencia;

import com.mycompany.flashnotes.modelo.Nota;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La clase NotaDAO (Data Access Object) se encarga de la persistencia de los datos,
 * es decir, de guardar y cargar objetos de tipo Nota desde y hacia el sistema de archivos.
 *
 * Su única responsabilidad es interactuar con el almacenamiento (en este caso, archivos de texto),
 * manteniendo esta lógica separada del modelo y el controlador.
 *
 * @author jesus
 */
public class NotaDAO {
    
    /**
     * Guarda el contenido de un objeto Nota en un archivo de texto.
     *
     * @param nota El objeto Nota que contiene el texto a guardar.
     * @param rutaArchivo La ruta completa del archivo donde se guardará la nota.
     * @return true si la nota se guardó exitosamente, false en caso de error.
     */
    public boolean guardarNotaEnArchivo(Nota nota, String rutaArchivo) {
        // El bloque try-with-resources asegura que el BufferedWriter se cierre automáticamente.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(nota.getContenido());
            return true;
        } catch (IOException e) {
            // En caso de un error de E/S, se imprime la pila de errores para depuración.
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carga el contenido de un archivo de texto y lo convierte en un objeto Nota.
     *
     * @param rutaArchivo La ruta completa del archivo a cargar.
     * @return Un nuevo objeto Nota con el contenido del archivo, o null si ocurre un error.
     */
    public Nota cargarNotaDesdeArchivo(String rutaArchivo) {
        StringBuilder contenido = new StringBuilder();
        // El bloque try-with-resources asegura que el BufferedReader se cierre automáticamente.
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // Lee el archivo línea por línea hasta el final.
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append(System.lineSeparator());
            }
            // Retorna un nuevo objeto Nota con el contenido del archivo.
            // Se usa .trim() para eliminar el último salto de línea.
            return new Nota(contenido.toString().trim());
        } catch (IOException e) {
            // En caso de un error de E/S, se imprime la pila de errores para depuración.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina un archivo del sistema de archivos.
     *
     * @param rutaArchivo La ruta completa del archivo a eliminar.
     * @return true si el archivo existía y fue eliminado exitosamente, false en caso contrario.
     */
    public boolean eliminarArchivo(String rutaArchivo) {
        File file = new File(rutaArchivo);
        // Verifica si el archivo existe y luego intenta eliminarlo.
        return file.exists() && file.delete();
    }
}