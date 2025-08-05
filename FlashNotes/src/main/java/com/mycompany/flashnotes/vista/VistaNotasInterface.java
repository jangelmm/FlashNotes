package com.mycompany.flashnotes.vista;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.event.CaretListener;

/**
 * La interfaz VistaNotasInterface define los métodos que el controlador puede
 * utilizar para interactuar con la vista. Esto desacopla el controlador de
 * la implementación específica de la interfaz de usuario (por ejemplo, Swing).
 */
public interface VistaNotasInterface {

    /**
     * Interfaz anidada para notificar al controlador cuando una nota es seleccionada.
     */
    interface NotaSeleccionadaListener {
        void onNotaSeleccionada(int index);
    }
    
    /**
     * Interfaz anidada para notificar al controlador cuando el contenido de la nota cambia.
     */
    interface GuardarCambiosListener {
        void onGuardarCambios(String contenido);
    }

    // Métodos para establecer los listeners del controlador
    void setNotaSeleccionadaListener(NotaSeleccionadaListener listener);
    void setGuardarCambiosListener(GuardarCambiosListener listener);
    
    // Métodos para que el controlador adjunte ActionListeners a los componentes de la vista
    void addCrearNotaListener(ActionListener listener);
    void addGuardarNotaListener(ActionListener listener);
    void addLimpiarTodoListener(ActionListener listener);
    void addBuscarListener(ActionListener listener);
    void addCambiarTemaOscuro(ActionListener listener);
    void addCambiarTemaClaro(ActionListener listener);
    void addVisitarDocumentacion(ActionListener listener);
    void addVisitarSitioWeb(ActionListener listener);
    void addBuscarKeyListener(KeyListener listener);
    
    // Métodos para obtener datos de la vista
    String getTextoBusqueda();
    String getContenidoNota();
    int getNotaSeleccionadaIndex();
    String getTextoBuscar();
    JTextArea getTxtCuerpoDerContenidoNota();

    // Métodos para que el controlador actualice la vista
    void setContenidoNota(String contenido);
    void mostrarNotas(List<String> contenidos);
    void seleccionarNota(int index);
    void actualizarTituloNota(int index, String contenido);
    void actualizarConteoNotas(int cantidad);
    void addCaretListener(CaretListener listener);
    void setInformacionInferior(String texto);
    void setCaretPosition(int position);
    
    // Método para hacer visible la ventana de la aplicación
    void setVisible(boolean visible);
}