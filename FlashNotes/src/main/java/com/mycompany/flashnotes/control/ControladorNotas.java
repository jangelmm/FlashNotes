package com.mycompany.flashnotes.control;

import com.mycompany.flashnotes.modelo.GestorNotas;
import com.mycompany.flashnotes.vista.VistaNotas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * La clase ControladorNotas es el "Controlador" en el patrón de diseño MVC.
 * Actúa como el intermediario principal entre el modelo (GestorNotas) y la vista (VistaNotas).
 *
 * Sus responsabilidades son:
 * 1. Escuchar las acciones del usuario desde la vista.
 * 2. Interpretar estas acciones y manipular el modelo de acuerdo a ellas.
 * 3. Actualizar la vista para reflejar los cambios en el modelo.
 *
 * @author jesus
 */
public class ControladorNotas implements ActionListener {
    
    // Una referencia al modelo para acceder a la lógica de negocio.
    private final GestorNotas gestor;
    
    // Una referencia a la vista para manipular la interfaz de usuario.
    private final VistaNotas vista;

    /**
     * Constructor del controlador.
     *
     * @param gestor Una instancia de GestorNotas.
     * @param vista Una instancia de VistaNotas.
     */
    public ControladorNotas(GestorNotas gestor, VistaNotas vista) {
        this.gestor = gestor;
        this.vista = vista;
        initController();
    }

    /**
     * Inicializa el controlador, configurando los listeners para los eventos de la vista.
     */
    private void initController() {
        // Asigna este controlador como el ActionListener para varios botones y menús.
        vista.addCrearNotaListener(this);
        vista.addGuardarNotaListener(this);
        vista.addLimpiarTodoListener(this);
        vista.addBuscarListener(this);
        
        // Asigna métodos específicos a las interfaces de listener de la vista
        // usando expresiones lambda, lo que es una práctica moderna y limpia.
        vista.setNotaSeleccionadaListener(this::cambiarNotaSeleccionada);
        vista.setGuardarCambiosListener(this::guardarCambiosNotaActual);

        // Agrega estos dos listeners para los menús de tema
        vista.addCambiarTemaOscuro(this);
        vista.addCambiarTemaClaro(this);
        
        //Agregar listener para los botones de Ayuda
        vista.addVisitarDocumentacion(this);
        vista.addVisitarSitioWeb(this);
        
        // Si la aplicación se inicia sin notas, crea una nota vacía por defecto.
        if (gestor.getNotas().isEmpty()) {
            gestor.crearNota("");
        }
        
        // Carga la interfaz de usuario inicial con los datos del modelo.
        actualizarVistaCompleta();
        cambiarTemaClaro();
    }
    
    /**
     * Este método se activa cuando se hace clic en un botón o ítem de menú.
     *
     * @param e El evento de acción que contiene el comando del componente.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        // Usa una estructura switch para manejar los diferentes comandos de acción.
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
            case "Oscuro":
                cambiarTemaOscuro();
                break;
            case "Claro":
                cambiarTemaClaro();
                break;
            case "Documentacion":
                visitarDocumentacion();
                break;
            case "Donar":
                visitarSitioWeb();
                break;
        }
    }
    
    /**
     * Lógica para crear una nueva nota.
     * 1. Pide al modelo que cree una nueva nota.
     * 2. Pide a la vista que se actualice.
     * 3. Selecciona visualmente la nueva nota y muestra su contenido vacío.
     */
    private void crearNuevaNota() {
        gestor.crearNota("");
        actualizarVistaCompleta();
        vista.seleccionarNota(gestor.getNotas().size() - 1);
        vista.setContenidoNota("");
    }

    /**
     * Lógica para guardar la nota seleccionada en un archivo.
     * 1. Obtiene el índice de la nota seleccionada desde la vista.
     * 2. Usa JFileChooser para que el usuario elija la ubicación y el nombre del archivo.
     * 3. Llama al método del modelo para guardar la nota.
     * 4. Muestra mensajes de éxito o error al usuario.
     */
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
    
    /**
     * Lógica para eliminar todas las notas.
     * 1. Pide confirmación al usuario para evitar la eliminación accidental.
     * 2. Si el usuario confirma, le pide al modelo que elimine todas las notas.
     * 3. Crea una nota vacía por defecto.
     * 4. Actualiza la vista para reflejar el nuevo estado del modelo.
     */
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
    
    /**
     * Maneja el cambio de selección de notas en la vista.
     * Este método es llamado por el listener de selección de la vista.
     *
     * @param indice El índice de la nueva nota seleccionada.
     */
    private void cambiarNotaSeleccionada(int indice) {
        if(indice >= 0 && indice < gestor.getNotas().size()){
            vista.setContenidoNota(gestor.getNota(indice).getContenido());
            vista.seleccionarNota(indice);
        }
    }
    
    /**
     * Maneja el guardado automático de los cambios en el contenido de la nota actual.
     * Este método es llamado por el DocumentListener de la vista.
     *
     * @param contenido El nuevo contenido de la nota.
     */
    private void guardarCambiosNotaActual(String contenido) {
        int indice = vista.getNotaSeleccionadaIndex();
        if (indice >= 0 && indice < gestor.getNotas().size()) {
            gestor.actualizarContenidoNota(indice, contenido);
            vista.actualizarTituloNota(indice, contenido);
        }
    }

    /**
     * Centraliza la lógica de actualización de la interfaz de usuario.
     * Este método se encarga de sincronizar la vista con el estado actual del modelo.
     */
    private void actualizarVistaCompleta() {
        // Muestra todas las notas en el panel izquierdo de la vista.
        vista.mostrarNotas(gestor.getContenidoNotas());
        
        // Si hay notas, selecciona la primera y muestra su contenido.
        if (!gestor.getNotas().isEmpty()) {
            vista.setContenidoNota(gestor.getNota(0).getContenido());
            vista.seleccionarNota(0);
        } else {
            // Si no hay notas, limpia el área de texto y la selección.
            vista.setContenidoNota("");
            vista.seleccionarNota(-1);
        }
    }
    
    private void cambiarTemaOscuro(){
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Usa FlatDarkLaf para el tema oscuro
            SwingUtilities.updateComponentTreeUI(vista);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    private void cambiarTemaClaro(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Usa FlatLightLaf para el tema claro
            SwingUtilities.updateComponentTreeUI(vista);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    private void visitarDocumentacion(){
        try {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/jangelmm/FlashNotes"));
            } catch (IOException ex) {
                
            }
        } catch (URISyntaxException ex) {
            
        }
    }
    
    private void visitarSitioWeb(){
        try {
            try {
                Desktop.getDesktop().browse(new URI("https://jangelmm.github.io/"));
            } catch (IOException ex) {
                System.out.println("Error 1");
            }
        } catch (URISyntaxException ex) {
            System.out.println("Error 2");
        }
    }
}