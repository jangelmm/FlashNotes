# Estructura del proyecto

```
FlashNotes
├── FlashNotes
│   ├── src
│   │   ├── main
│   │   │   └── java
│   │   │       └── com
│   │   │           └── mycompany
│   │   │               └── flashnotes
│   │   │                   ├── control
│   │   │                   │   └── ControladorNotas.java
│   │   │                   ├── imagenes
│   │   │                   │   ├── advertencia.png
│   │   │                   │   ├── borrar.png
│   │   │                   │   ├── buscar.png
│   │   │                   │   ├── destello.png
│   │   │                   │   ├── guardar.png
│   │   │                   │   └── nuevo.png
│   │   │                   ├── modelo
│   │   │                   │   ├── GestorNotas.java
│   │   │                   │   └── Nota.java
│   │   │                   ├── persistencia
│   │   │                   │   └── NotaDAO.java
│   │   │                   ├── vista
│   │   │                   │   ├── VistaNotas.form
│   │   │                   │   └── VistaNotas.java
│   │   │                   └── FlashNotes.java
│   │   └── test
│   │       └── java
│   ├── target
│   │   ├── classes
│   │   │   └── com
│   │   │       └── mycompany
│   │   │           └── flashnotes
│   │   │               ├── control
│   │   │               │   └── ControladorNotas.class
│   │   │               ├── imagenes
│   │   │               │   ├── advertencia.png
│   │   │               │   ├── borrar.png
│   │   │               │   ├── buscar.png
│   │   │               │   ├── destello.png
│   │   │               │   ├── guardar.png
│   │   │               │   └── nuevo.png
│   │   │               ├── modelo
│   │   │               │   ├── GestorNotas.class
│   │   │               │   └── Nota.class
│   │   │               ├── persistencia
│   │   │               │   └── NotaDAO.class
│   │   │               ├── vista
│   │   │               │   ├── VistaNotas$1.class
│   │   │               │   ├── VistaNotas$2.class
│   │   │               │   ├── VistaNotas$GuardarCambiosListener.class
│   │   │               │   ├── VistaNotas$NotaSeleccionadaListener.class
│   │   │               │   ├── VistaNotas.class
│   │   │               │   └── VistaNotas.form
│   │   │               └── FlashNotes.class
│   │   ├── generated-sources
│   │   │   └── annotations
│   │   ├── generated-test-sources
│   │   │   └── test-annotations
│   │   ├── maven-archiver
│   │   │   └── pom.properties
│   │   ├── maven-status
│   │   │   └── maven-compiler-plugin
│   │   │       ├── compile
│   │   │       │   └── default-compile
│   │   │       │       ├── createdFiles.lst
│   │   │       │       └── inputFiles.lst
│   │   │       └── testCompile
│   │   │           └── default-testCompile
│   │   │               ├── createdFiles.lst
│   │   │               └── inputFiles.lst
│   │   ├── test-classes
│   │   └── FlashNotes-1.0-SNAPSHOT.jar
│   ├── nbactions.xml
│   ├── notas.txt
│   ├── pom.xml
│   └── script.py
├── Images
│   └── Fase-2-Diseno-GUI-PlantUML.png
├── .gitignore
├── README.md
└── project_overview.md
```

## `.gitignore`

```text
/FlashNotes/target/
```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\FlashNotes.java`

```java
package com.mycompany.flashnotes;

import com.mycompany.flashnotes.control.ControladorNotas;
import com.mycompany.flashnotes.modelo.GestorNotas;
import com.mycompany.flashnotes.vista.VistaNotas;

public class FlashNotes {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VistaNotas vista = new VistaNotas();
            GestorNotas gestor = new GestorNotas();
            ControladorNotas controlador = new ControladorNotas(gestor, vista);
            vista.setVisible(true);
        });
    }
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\control\ControladorNotas.java`

```java
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
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\modelo\GestorNotas.java`

```java
package com.mycompany.flashnotes.modelo;

import com.mycompany.flashnotes.persistencia.NotaDAO;
import java.util.ArrayList;
import java.util.List;

public class GestorNotas {
    private final List<Nota> notas;
    private final NotaDAO notaDAO;

    public GestorNotas() {
        this.notas = new ArrayList<>();
        this.notaDAO = new NotaDAO();
    }

    public void crearNota(String contenido) {
        notas.add(new Nota(contenido));
    }

    public void eliminarNota(int indice) {
        if (indice >= 0 && indice < notas.size()) {
            notas.remove(indice);
        }
    }

    public void eliminarTodas() {
        notas.clear();
    }

    public boolean guardarNota(int indice, String rutaArchivo) {
        if (indice >= 0 && indice < notas.size()) {
            Nota notaAGuardar = notas.get(indice);
            return notaDAO.guardarNotaEnArchivo(notaAGuardar, rutaArchivo);
        }
        return false;
    }

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

    public List<Nota> getNotas() {
        return new ArrayList<>(notas);
    }
    
    // Método agregado para obtener una nota específica
    public Nota getNota(int indice){
        if (indice >= 0 && indice < notas.size()) {
            return notas.get(indice);
        }
        return null;
    }

    public List<String> getContenidoNotas() {
        List<String> contenidos = new ArrayList<>();
        for (Nota nota : notas) {
            contenidos.add(nota.getContenido());
        }
        return contenidos;
    }
    
    public void actualizarContenidoNota(int indice, String contenido) {
        if (indice >= 0 && indice < notas.size()) {
            notas.get(indice).setContenido(contenido);
        }
    }   
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\modelo\Nota.java`

```java
package com.mycompany.flashnotes.modelo;

import java.io.Serializable;

/**
 *
 * @author jesus
 */
public class Nota {
    private String contenido;

    public Nota(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\persistencia\NotaDAO.java`

```java
package com.mycompany.flashnotes.persistencia;

import com.mycompany.flashnotes.modelo.Nota;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jesus
 */
public class NotaDAO {
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

    public boolean eliminarArchivo(String rutaArchivo) {
        File file = new File(rutaArchivo);
        return file.exists() && file.delete();
    }
}
```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\vista\VistaNotas.java`

```java
package com.mycompany.flashnotes.vista;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author jesus
 */
public class VistaNotas extends javax.swing.JFrame {
    
    private int notaSeleccionadaIndex = -1;
    private final Border defaultBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private final Border selectedBorder = BorderFactory.createLineBorder(new Color(155, 182, 255), 2);
    private final DocumentListener documentListener;
    
    // Listener para guardar cambios de texto
    private GuardarCambiosListener guardarCambiosListener;
    
    // Listener para seleccionar una nota
    private NotaSeleccionadaListener notaSeleccionadaListener;
    
    /**
     * Creates new form VistaNotas
     */
    public VistaNotas() {
        // Inicializa el DocumentListener una sola vez
        documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void removeUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void changedUpdate(DocumentEvent e) { notificarCambios(); }
            
            private void notificarCambios() {
                if (guardarCambiosListener != null && notaSeleccionadaIndex >= 0) {
                    guardarCambiosListener.onGuardarCambios(txtCuerpoDerContenidoNota.getText());
                }
            }
        };
        
        initComponents();
        setLocationRelativeTo(null);
        // Agrega el listener inicial al JTextArea
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(documentListener);
        
        // Inicializa el panel izquierdo para que use un BoxLayout vertical
        panelCuerpoIzquierdo.setLayout(new javax.swing.BoxLayout(panelCuerpoIzquierdo, javax.swing.BoxLayout.Y_AXIS));

        // Agrupar temas
        ButtonGroup temas = new ButtonGroup();
        temas.add(opcTemaClaro);
        temas.add(opcTemaOscuro);
        opcTemaOscuro.setSelected(true);
        
    }
    
    public interface NotaSeleccionadaListener {
        void onNotaSeleccionada(int index);
    }
        
    public void setNotaSeleccionadaListener(NotaSeleccionadaListener listener) {
        this.notaSeleccionadaListener = listener;
    }

    public interface GuardarCambiosListener {
        void onGuardarCambios(String contenido);
    }
        
    public void setGuardarCambiosListener(GuardarCambiosListener listener) {
        this.guardarCambiosListener = listener;
    }
        
    public void addCrearNotaListener(ActionListener l) {
        btnElementoNuevaNota.addActionListener(l);
        opcNuevaNota.addActionListener(l);
    }

    public void addGuardarNotaListener(ActionListener l) {
        btnElementoGuardarTxt.addActionListener(l);
        opcGuardarElemento.addActionListener(l);
    }

    public void addLimpiarTodoListener(ActionListener l) {
        btnElementoLimpiarTodo.addActionListener(l);
        opcLimipiarTodo.addActionListener(l);
    }

    public void addBuscarListener(ActionListener l) {
        txtElementoBuscarNotaActual.addActionListener(l);
    }

    public String getTextoBusqueda() {
        return txtElementoBuscarNotaActual.getText();
    }

    public String getContenidoNota() {
        return txtCuerpoDerContenidoNota.getText();
    }

    // Este es el método corregido y completo que estabas pidiendo.
    public void setContenidoNota(String contenido) {
        // Remueve el listener antes de cambiar el texto para evitar que se dispare
        txtCuerpoDerContenidoNota.getDocument().removeDocumentListener(documentListener);
        
        // Cambia el texto del JTextArea
        txtCuerpoDerContenidoNota.setText(contenido);
        
        // Vuelve a agregar el listener
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(documentListener);
    }

    public int getNotaSeleccionadaIndex() {
        return notaSeleccionadaIndex;
    }
    
    // Este método ahora es el que se encarga de cambiar la selección visual
    public void seleccionarNota(int index) {
        this.notaSeleccionadaIndex = index;
        for (int i = 0; i < panelCuerpoIzquierdo.getComponentCount(); i++) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(i);
            label.setBorder(i == index ? selectedBorder : defaultBorder);
        }
    }

    // El controlador ahora es responsable de llamar a este método para actualizar la vista
    public void mostrarNotas(List<String> contenidos) {
        panelCuerpoIzquierdo.removeAll();
        panelCuerpoIzquierdo.revalidate();
        panelCuerpoIzquierdo.repaint();

        if (contenidos.isEmpty()) {
            panelCuerpoIzquierdo.add(lblCuerpoIzquierdoNotasActivas);
            return;
        }
        
        for (int i = 0; i < contenidos.size(); i++) {
            String contenido = contenidos.get(i);
            JLabel notaLabel = new JLabel(getTituloNota(contenido));
            notaLabel.setBorder(defaultBorder);
            notaLabel.putClientProperty("index", i);
            notaLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int idx = (int) ((JLabel) e.getSource()).getClientProperty("index");
                    if (notaSeleccionadaListener != null) {
                        notaSeleccionadaListener.onNotaSeleccionada(idx);
                    }
                }
            });
            panelCuerpoIzquierdo.add(notaLabel);
        }
        
        panelCuerpoIzquierdo.revalidate();
        panelCuerpoIzquierdo.repaint();
    }

    private String getTituloNota(String contenido) {
        if (contenido == null || contenido.trim().isEmpty()) {
            return "Nueva Nota";
        }
        String[] palabras = contenido.trim().split("\\s+");
        StringBuilder titulo = new StringBuilder();
        for (int i = 0; i < Math.min(3, palabras.length); i++) {
            titulo.append(palabras[i]).append(" ");
        }
        return titulo.toString().trim();
    }

    public void actualizarTituloNota(int index, String contenido) {
        if (index >= 0 && index < panelCuerpoIzquierdo.getComponentCount()) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(index);
            label.setText(getTituloNota(contenido));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelSuperior = new javax.swing.JPanel();
        lblEncabezadoImg = new javax.swing.JLabel();
        lblEncabezadoTxt = new javax.swing.JLabel();
        lblEncabezadoNot = new javax.swing.JLabel();
        panelElementos = new javax.swing.JPanel();
        btnElementoNuevaNota = new javax.swing.JButton();
        btnElementoGuardarTxt = new javax.swing.JButton();
        btnElementoLimpiarTodo = new javax.swing.JButton();
        lblElementoIconoBuscar = new javax.swing.JLabel();
        txtElementoBuscarNotaActual = new javax.swing.JTextField();
        panelCuerpo = new javax.swing.JPanel();
        scrollPanelCuerpoIzquierdo = new javax.swing.JScrollPane();
        panelCuerpoIzquierdo = new javax.swing.JPanel();
        lblCuerpoIzquierdoNotasActivas = new javax.swing.JLabel();
        panelCuerpoDerecho = new javax.swing.JPanel();
        scrollPanelCuerpoDer = new javax.swing.JScrollPane();
        txtCuerpoDerContenidoNota = new javax.swing.JTextArea();
        panelInferior = new javax.swing.JPanel();
        lblInferiorInformacion = new javax.swing.JLabel();
        lblInferiorAdvertencia = new javax.swing.JLabel();
        menuBarra = new javax.swing.JMenuBar();
        menuOpciones = new javax.swing.JMenu();
        opcNuevaNota = new javax.swing.JMenuItem();
        opcGuardarElemento = new javax.swing.JMenuItem();
        opcLimipiarTodo = new javax.swing.JMenuItem();
        menuApariencia = new javax.swing.JMenu();
        opcTemaOscuro = new javax.swing.JRadioButtonMenuItem();
        opcTemaClaro = new javax.swing.JRadioButtonMenuItem();
        menuAyuda = new javax.swing.JMenu();
        opcDocumentacion = new javax.swing.JMenuItem();
        opcSitioWeb = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FlashNotes");
        setMinimumSize(new java.awt.Dimension(600, 350));

        panelPrincipal.setLayout(new javax.swing.BoxLayout(panelPrincipal, javax.swing.BoxLayout.Y_AXIS));

        panelSuperior.setMaximumSize(new java.awt.Dimension(32767, 50));
        panelSuperior.setPreferredSize(new java.awt.Dimension(400, 50));

        lblEncabezadoImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/flashnotes/imagenes/destello.png"))); // NOI18N
        panelSuperior.add(lblEncabezadoImg);

        lblEncabezadoTxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEncabezadoTxt.setText("FlashNotes - Notas Temporales");
        panelSuperior.add(lblEncabezadoTxt);

        lblEncabezadoNot.setText("2 notas act.");
        panelSuperior.add(lblEncabezadoNot);

        panelPrincipal.add(panelSuperior);

        panelElementos.setMaximumSize(new java.awt.Dimension(32767, 50));
        panelElementos.setPreferredSize(new java.awt.Dimension(400, 50));

        btnElementoNuevaNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/flashnotes/imagenes/nuevo.png"))); // NOI18N
        btnElementoNuevaNota.setText("Nueva Nota");
        panelElementos.add(btnElementoNuevaNota);

        btnElementoGuardarTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/flashnotes/imagenes/guardar.png"))); // NOI18N
        btnElementoGuardarTxt.setText("Guardar TXT");
        panelElementos.add(btnElementoGuardarTxt);

        btnElementoLimpiarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/flashnotes/imagenes/borrar.png"))); // NOI18N
        btnElementoLimpiarTodo.setText("Limpiar Todo");
        panelElementos.add(btnElementoLimpiarTodo);

        lblElementoIconoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/flashnotes/imagenes/buscar.png"))); // NOI18N
        panelElementos.add(lblElementoIconoBuscar);

        txtElementoBuscarNotaActual.setToolTipText("");
        txtElementoBuscarNotaActual.setMinimumSize(new java.awt.Dimension(90, 26));
        txtElementoBuscarNotaActual.setPreferredSize(new java.awt.Dimension(100, 26));
        panelElementos.add(txtElementoBuscarNotaActual);

        panelPrincipal.add(panelElementos);

        panelCuerpo.setLayout(new javax.swing.BoxLayout(panelCuerpo, javax.swing.BoxLayout.LINE_AXIS));

        scrollPanelCuerpoIzquierdo.setMaximumSize(new java.awt.Dimension(150, 32767));
        scrollPanelCuerpoIzquierdo.setMinimumSize(new java.awt.Dimension(150, 22));
        scrollPanelCuerpoIzquierdo.setPreferredSize(new java.awt.Dimension(150, 100));

        panelCuerpoIzquierdo.setLayout(new javax.swing.BoxLayout(panelCuerpoIzquierdo, javax.swing.BoxLayout.Y_AXIS));

        lblCuerpoIzquierdoNotasActivas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCuerpoIzquierdoNotasActivas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCuerpoIzquierdoNotasActivas.setText("Notas Activas");
        lblCuerpoIzquierdoNotasActivas.setMaximumSize(new java.awt.Dimension(170, 20));
        lblCuerpoIzquierdoNotasActivas.setMinimumSize(new java.awt.Dimension(120, 20));
        lblCuerpoIzquierdoNotasActivas.setPreferredSize(new java.awt.Dimension(120, 20));
        panelCuerpoIzquierdo.add(lblCuerpoIzquierdoNotasActivas);

        scrollPanelCuerpoIzquierdo.setViewportView(panelCuerpoIzquierdo);

        panelCuerpo.add(scrollPanelCuerpoIzquierdo);

        panelCuerpoDerecho.setBackground(new java.awt.Color(255, 153, 153));
        panelCuerpoDerecho.setLayout(new javax.swing.BoxLayout(panelCuerpoDerecho, javax.swing.BoxLayout.LINE_AXIS));

        txtCuerpoDerContenidoNota.setColumns(20);
        txtCuerpoDerContenidoNota.setRows(5);
        scrollPanelCuerpoDer.setViewportView(txtCuerpoDerContenidoNota);

        panelCuerpoDerecho.add(scrollPanelCuerpoDer);

        panelCuerpo.add(panelCuerpoDerecho);

        panelPrincipal.add(panelCuerpo);

        panelInferior.setMaximumSize(new java.awt.Dimension(32767, 25));
        panelInferior.setPreferredSize(new java.awt.Dimension(400, 25));
        panelInferior.setLayout(new javax.swing.BoxLayout(panelInferior, javax.swing.BoxLayout.LINE_AXIS));

        lblInferiorInformacion.setText("Linea: 12, Caracteres: 445");
        lblInferiorInformacion.setMaximumSize(new java.awt.Dimension(150, 16));
        lblInferiorInformacion.setMinimumSize(new java.awt.Dimension(150, 16));
        lblInferiorInformacion.setPreferredSize(new java.awt.Dimension(150, 16));
        panelInferior.add(lblInferiorInformacion);

        lblInferiorAdvertencia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblInferiorAdvertencia.setForeground(new java.awt.Color(255, 204, 51));
        lblInferiorAdvertencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/flashnotes/imagenes/advertencia.png"))); // NOI18N
        lblInferiorAdvertencia.setText("Notas temporales - Se eliminarán al cerrar");
        panelInferior.add(lblInferiorAdvertencia);

        panelPrincipal.add(panelInferior);

        menuOpciones.setText("Opciones");

        opcNuevaNota.setText("Nueva Nota");
        menuOpciones.add(opcNuevaNota);

        opcGuardarElemento.setText("Guardar");
        menuOpciones.add(opcGuardarElemento);

        opcLimipiarTodo.setText("LimpiarTodo");
        menuOpciones.add(opcLimipiarTodo);

        menuBarra.add(menuOpciones);

        menuApariencia.setText("Apariencia");

        opcTemaOscuro.setSelected(true);
        opcTemaOscuro.setText("Oscuro");
        menuApariencia.add(opcTemaOscuro);

        opcTemaClaro.setSelected(true);
        opcTemaClaro.setText("Claro");
        menuApariencia.add(opcTemaClaro);

        menuBarra.add(menuApariencia);

        menuAyuda.setText("Ayuda");

        opcDocumentacion.setText("Documentación");
        menuAyuda.add(opcDocumentacion);

        opcSitioWeb.setText("Donar");
        menuAyuda.add(opcSitioWeb);

        menuBarra.add(menuAyuda);

        setJMenuBar(menuBarra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnElementoGuardarTxt;
    private javax.swing.JButton btnElementoLimpiarTodo;
    private javax.swing.JButton btnElementoNuevaNota;
    private javax.swing.JLabel lblCuerpoIzquierdoNotasActivas;
    private javax.swing.JLabel lblElementoIconoBuscar;
    private javax.swing.JLabel lblEncabezadoImg;
    private javax.swing.JLabel lblEncabezadoNot;
    private javax.swing.JLabel lblEncabezadoTxt;
    private javax.swing.JLabel lblInferiorAdvertencia;
    private javax.swing.JLabel lblInferiorInformacion;
    private javax.swing.JMenu menuApariencia;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenuBar menuBarra;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenuItem opcDocumentacion;
    private javax.swing.JMenuItem opcGuardarElemento;
    private javax.swing.JMenuItem opcLimipiarTodo;
    private javax.swing.JMenuItem opcNuevaNota;
    private javax.swing.JMenuItem opcSitioWeb;
    private javax.swing.JRadioButtonMenuItem opcTemaClaro;
    private javax.swing.JRadioButtonMenuItem opcTemaOscuro;
    private javax.swing.JPanel panelCuerpo;
    private javax.swing.JPanel panelCuerpoDerecho;
    private javax.swing.JPanel panelCuerpoIzquierdo;
    private javax.swing.JPanel panelElementos;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JScrollPane scrollPanelCuerpoDer;
    private javax.swing.JScrollPane scrollPanelCuerpoIzquierdo;
    private javax.swing.JTextArea txtCuerpoDerContenidoNota;
    private javax.swing.JTextField txtElementoBuscarNotaActual;
    // End of variables declaration//GEN-END:variables
}
```

