package com.mycompany.flashnotes;

import com.mycompany.flashnotes.control.ControladorNotas;
import com.mycompany.flashnotes.modelo.GestorNotas;
import com.mycompany.flashnotes.vista.VistaNotas;

/**
 * La clase principal de la aplicación FlashNotes.
 *
 * Su única responsabilidad es inicializar y arrancar la aplicación,
 * creando las instancias del Modelo, la Vista y el Controlador y
 * conectándolos entre sí.
 *
 * @author jesus
 */
public class FlashNotes {
    
    /**
     * El punto de entrada principal de la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        // Asegura que la interfaz gráfica de usuario (GUI) se ejecute en el hilo
        // de despacho de eventos de AWT (AWT-EventQueue), que es la forma segura
        // y recomendada de trabajar con Swing para evitar problemas de concurrencia.
        java.awt.EventQueue.invokeLater(() -> {
            // 1. Instancia la Vista.
            // La vista se crea sin conocer el modelo o el controlador.
            VistaNotas vista = new VistaNotas();
            
            // 2. Instancia el Modelo.
            // El modelo se crea sin conocer la vista o el controlador.
            GestorNotas gestor = new GestorNotas();
            
            // 3. Instancia el Controlador, inyectando las dependencias del modelo y la vista.
            // El controlador es el que conoce y une al modelo y la vista.
            ControladorNotas controlador = new ControladorNotas(gestor, vista);
            
            // 4. Hace visible la ventana de la aplicación.
            vista.setVisible(true);
        });
    }
}