package com.mycompany.flashnotes.control;

import com.mycompany.flashnotes.modelo.GestorNotas;
import com.mycompany.flashnotes.vista.VistaNotas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ControladorNotas implements ActionListener {
    private final GestorNotas gestor;
    private final VistaNotas vista;

    public ControladorNotas(GestorNotas gestor, VistaNotas vista) {
        this.gestor = gestor;
        this.vista = vista;
        initController();
    }

    private void initController() {
        vista.addCrearNotaListener(this);
        vista.addGuardarNotaListener(this);
        vista.addLimpiarTodoListener(this);
        vista.addBuscarListener(this);
        
        // Listener para la selección de notas
        vista.setNotaSeleccionadaListener(this::cambiarNotaSeleccionada);
        // Listener para guardar cambios de texto
        vista.setGuardarCambiosListener(this::guardarCambiosNotaActual);

        // Si no hay notas, crea una por defecto
        if (gestor.getNotas().isEmpty()) {
            gestor.crearNota("");
        }
        
        actualizarVistaCompleta();
    }
    
    // Método que se activa al hacer clic en los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Nueva Nota":
                crearNuevaNota();
                break;
            case "Guardar TXT":
                guardarNota();
                break;
            case "Limpiar Todo":
                limpiarTodas();
                break;
        }
    }
    
    // Maneja la creación de una nueva nota
    private void crearNuevaNota() {
        gestor.crearNota("");
        actualizarVistaCompleta();
        vista.seleccionarNota(gestor.getNotas().size() - 1);
        vista.setContenidoNota("");
    }

    // Maneja la acción de guardar un archivo
    private void guardarNota() {
        int indice = vista.getNotaSeleccionadaIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una nota para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        
        int userSelection = fileChooser.showSaveDialog(vista);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".txt")) {
                rutaArchivo += ".txt";
            }
            
            if (gestor.guardarNota(indice, rutaArchivo)) {
                JOptionPane.showMessageDialog(vista, "Nota guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Maneja la eliminación de todas las notas
    private void limpiarTodas() {
        int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Eliminar todas las notas? Esta acción no se puede deshacer",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gestor.eliminarTodas();
            gestor.crearNota(""); // Crear una nota vacía por defecto
            actualizarVistaCompleta();
            vista.seleccionarNota(0);
        }
    }
    
    // Método para manejar el clic en los labels de la lista de notas
    private void cambiarNotaSeleccionada(int indice) {
        if(indice >= 0 && indice < gestor.getNotas().size()){
            vista.setContenidoNota(gestor.getNota(indice).getContenido());
            vista.seleccionarNota(indice);
        }
    }
    
    // Método para guardar los cambios de la nota actual
    private void guardarCambiosNotaActual(String contenido) {
        int indice = vista.getNotaSeleccionadaIndex();
        if (indice >= 0 && indice < gestor.getNotas().size()) {
            gestor.actualizarContenidoNota(indice, contenido);
            vista.actualizarTituloNota(indice, contenido);
        }
    }

    // Este método centraliza la actualización de la vista desde el modelo
    private void actualizarVistaCompleta() {
        vista.mostrarNotas(gestor.getContenidoNotas());
        if (!gestor.getNotas().isEmpty()) {
            vista.setContenidoNota(gestor.getNota(0).getContenido());
            vista.seleccionarNota(0);
        } else {
            vista.setContenidoNota("");
            vista.seleccionarNota(-1);
        }
    }
}