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
│   │   │               │   ├── VistaNotas$3.class
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
            
            // Configurar listeners adicionales
            vista.setNotaSeleccionadaListener(controlador::cambiarNotaSeleccionada);
            vista.setGuardarCambiosListener(controlador::guardarCambiosNotaActual);
            
            vista.setVisible(true);
        });
    }
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\control\ControladorNotas.java`

```java
package com.mycompany.flashnotes.control;

import com.mycompany.flashnotes.modelo.GestorNotas;
import com.mycompany.flashnotes.modelo.Nota;
import com.mycompany.flashnotes.vista.VistaNotas;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ControladorNotas {
    private final GestorNotas gestor;
    private final VistaNotas vista;

    public ControladorNotas(GestorNotas gestor, VistaNotas vista) {
        this.gestor = gestor;
        this.vista = vista;
        initController();
    }

    private void initController() {
        vista.addCrearNotaListener(e -> crearNota());
        vista.addGuardarNotaListener(e -> guardarNota());
        vista.addLimpiarTodoListener(e -> limpiarTodas());
        vista.addBuscarListener(e -> buscarNota());
        vista.setContenidoNota("");
        actualizarVista();
    }

    private void guardarNota() {
        int idx = vista.getNotaSeleccionadaIndex();
        if (idx >= 0) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
                String ruta = chooser.getSelectedFile().getAbsolutePath();
                gestor.guardarNota(idx, ruta);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona una nota para guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarTodas() {
        int confirm = JOptionPane.showConfirmDialog(vista, 
                "¿Eliminar todas las notas? Esta acción no se puede deshacer",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gestor.eliminarTodas();
            vista.setContenidoNota("");
            actualizarVista();
        }
    }

    private void buscarNota() {
        String texto = vista.getTextoBusqueda();
        List<Integer> resultados = gestor.buscar(texto);
        if (!resultados.isEmpty()) {
            int primero = resultados.get(0);
            vista.mostrarNotas(gestor.getContenidoNotas());
            vista.seleccionarNota(primero);
            vista.setContenidoNota(gestor.getNotas().get(primero).getContenido());
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontraron coincidencias.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarVista() {
        vista.mostrarNotas(gestor.getContenidoNotas());
    }

    public void cambiarNotaSeleccionada(int indice) {
        if (indice >= 0 && indice < gestor.getNotas().size()) {
            if (vista.getNotaSeleccionadaIndex() >= 0) {
                guardarCambiosNotaActual(vista.getContenidoNota());
            }
            Nota nota = gestor.getNotas().get(indice);
            vista.setContenidoNota(nota.getContenido());
            vista.seleccionarNota(indice);
            actualizarVista();
        }
    }

    public void crearNota() {
        if (vista.getNotaSeleccionadaIndex() >= 0) {
            guardarCambiosNotaActual(vista.getContenidoNota());
        }
        gestor.crearNota(""); // Crear nueva nota vacía
        actualizarVista();
        int nuevaNotaIndex = gestor.getNotas().size() - 1;
        vista.seleccionarNota(nuevaNotaIndex);
        vista.setContenidoNota(""); // Limpiar el área de texto
    }
    
    public void guardarCambiosNotaActual(String contenido) {
        int indice = vista.getNotaSeleccionadaIndex();
        if (indice >= 0 && indice < gestor.getNotas().size()) {
            System.out.println("Guardando nota " + indice + " con contenido: " + contenido);
            gestor.actualizarContenidoNota(indice, contenido);
            actualizarVista();
        } else {
            System.out.println("Índice inválido para guardar: " + indice);
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
    /**
     * Creates new form VistaNotas
     */
    public VistaNotas() {
        
        initComponents();
        setLocationRelativeTo(null);
        
        // Inicializa el panel izquierdo para que use un BoxLayout vertical
        panelCuerpoIzquierdo.setLayout(new javax.swing.BoxLayout(panelCuerpoIzquierdo, javax.swing.BoxLayout.Y_AXIS));

        // Agrupar temas
        ButtonGroup temas = new ButtonGroup();
        temas.add(opcTemaClaro);
        temas.add(opcTemaOscuro);
        opcTemaOscuro.setSelected(true);
        
        // Agregar listener para cambios en el texto
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void removeUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void changedUpdate(DocumentEvent e) { notificarCambios(); }
            
            private void notificarCambios() {
                if (guardarCambiosListener != null && notaSeleccionadaIndex >= 0) {
                    guardarCambiosListener.onGuardarCambios(txtCuerpoDerContenidoNota.getText());
                    actualizarTituloNota(notaSeleccionadaIndex);
                }
            }
        });
    }
    
    public interface NotaSeleccionadaListener {
        void onNotaSeleccionada(int index);
    }
    
    private NotaSeleccionadaListener notaSeleccionadaListener;
    
    public void setNotaSeleccionadaListener(NotaSeleccionadaListener listener) {
        this.notaSeleccionadaListener = listener;
    }

    public interface GuardarCambiosListener {
        void onGuardarCambios(String contenido);
    }
    
    private GuardarCambiosListener guardarCambiosListener;
    
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

    public void setContenidoNota(String contenido) {
        txtCuerpoDerContenidoNota.setText(contenido);
    }

    public int getNotaSeleccionadaIndex() {
        return notaSeleccionadaIndex;
    }

    public void mostrarNotas(List<String> contenidos) {
        panelCuerpoIzquierdo.removeAll();
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

    public void seleccionarNota(int index) {
        this.notaSeleccionadaIndex = index;
        for (int i = 0; i < panelCuerpoIzquierdo.getComponentCount(); i++) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(i);
            label.setBorder(i == index ? selectedBorder : defaultBorder);
        }
        if (notaSeleccionadaListener != null) {
            notaSeleccionadaListener.onNotaSeleccionada(index);
        }
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
    
    private void guardarCambiosNotaActual() {
        if (notaSeleccionadaIndex >= 0 && guardarCambiosListener != null) {
            guardarCambiosListener.onGuardarCambios(txtCuerpoDerContenidoNota.getText());
        }
    }
    
    private void actualizarTituloNota(int index) {
        if (index >= 0 && index < panelCuerpoIzquierdo.getComponentCount()) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(index);
            label.setText(getTituloNota(txtCuerpoDerContenidoNota.getText()));
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaNotas().setVisible(true);
            }
        });
    }

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

