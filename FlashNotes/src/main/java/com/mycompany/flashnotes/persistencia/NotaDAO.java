package com.mycompany.flashnotes.persistencia;

import com.mycompany.flashnotes.modelo.Nota;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jesus
 */
public class NotaDAO {
    //Implementación de métodos para guardar/cargar archivos
    public boolean guardarNotaEnArchivo(Nota nota, String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(nota.getContenido());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Nota cargarNotaDesdeArchivo(String rutaArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append(System.lineSeparator());
            }
            return new Nota(contenido.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void eliminarArchivoNotas(){
        // No aplica en notas temporales
    }
}
