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
│   │   │                   │   ├── VistaNotas.java
│   │   │                   │   └── VistaNotasInterface.java
│   │   │                   └── FlashNotes.java
│   │   └── test
│   │       └── java
│   ├── target
│   │   ├── classes
│   │   │   └── com
│   │   │       └── mycompany
│   │   │           └── flashnotes
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
│   │   │               │   ├── VistaNotas.class
│   │   │               │   ├── VistaNotasInterface$GuardarCambiosListener.class
│   │   │               │   ├── VistaNotasInterface$NotaSeleccionadaListener.class
│   │   │               │   └── VistaNotasInterface.class
│   │   │               └── FlashNotes.class
│   │   ├── generated-sources
│   │   │   └── annotations
│   │   └── maven-status
│   │       └── maven-compiler-plugin
│   │           └── compile
│   │               └── default-compile
│   │                   ├── createdFiles.lst
│   │                   └── inputFiles.lst
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
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.mycompany.flashnotes.vista.VistaNotasInterface;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

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
public class ControladorNotas implements ActionListener, VistaNotasInterface.NotaSeleccionadaListener, VistaNotasInterface.GuardarCambiosListener, CaretListener {
    
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
        vista.setGuardarCambiosListener(this::guardarCambiosNotaActual);
        
        // Agrega estos dos listeners para los menús de tema
        vista.addCambiarTemaOscuro(this);
        vista.addCambiarTemaClaro(this);
        
        //Agregar listener para los botones de Ayuda
        vista.addVisitarDocumentacion(this);
        vista.addVisitarSitioWeb(this);
        
        //Listener para el caret (Ubicación de puntero)
        vista.addCaretListener(this);
        
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
        
        // Aquí obtienes el número de notas del modelo y actualizas la vista.
        int cantidadNotas = gestor.getNotas().size();
        vista.actualizarConteoNotas(cantidadNotas); // Llama al nuevo método para actualizar el conteo
        
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
    
    
    @Override
    public void caretUpdate(CaretEvent e) {
        // Obtener la posición del cursor
        int posicionCaret = e.getDot();
        
        // Obtener el JTextArea
        JTextArea areaDeTexto = txtCuerpoDerContenidoNota;
        
        int numeroLinea = 1;
        int numeroCaracter = 0;
        
        try {
            // Obtener el numero de linea y el caracter en la linea
            numeroLinea = areaDeTexto.getLineOfOffset(posicionCaret);
            numeroCaracter = posicionCaret - areaDeTexto.getLineStartOffset(numeroLinea);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        
        // Contar el numero total de caracteres
        int cantidadDeCaracteres = areaDeTexto.getText().length();

        // Construir el texto a mostrar
        String textoEstado = String.format("Línea: %d, Caracteres: %d", numeroLinea + 1, cantidadDeCaracteres);
        
        // Actualizar la etiqueta inferior de la vista
        vista.setInformacionInferior(textoEstado);
    }
    
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\modelo\GestorNotas.java`

```java
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
}```

## `FlashNotes\src\main\java\com\mycompany\flashnotes\modelo\Nota.java`

```java
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
}```

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
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * La clase VistaNotas representa la interfaz de usuario (UI) de la aplicación FlashNotes.
 *
 * Su principal responsabilidad es:
 * 1.  Mostrar la información que recibe del modelo a través del controlador.
 * 2.  Capturar las interacciones del usuario (clics en botones, escritura, etc.).
 * 3.  Notificar al controlador sobre estas interacciones, sin contener lógica de negocio.
 *
 * Esta clase implementa la interfaz {@link VistaNotasInterface}, lo que la desacopla
 * de manera efectiva del controlador y facilita la sustitución de la UI si fuera necesario.
 *
 * @author jesus
 */
public class VistaNotas extends javax.swing.JFrame implements VistaNotasInterface {

    // ====================================================================================
    // ATRIBUTOS DE LA CLASE
    // ¿Por qué están aquí? Son el "estado" de la vista. Son datos necesarios para
    // que la UI funcione, como el índice de la nota seleccionada o la apariencia
    // de los bordes. Declararlos al inicio es una convención estándar y limpia.
    // ====================================================================================
    
    /**
     * El índice de la nota que está actualmente seleccionada en la lista.
     * Su valor inicial es -1, indicando que no hay ninguna nota seleccionada.
     */
    private int notaSeleccionadaIndex = -1;
    
    /**
     * El borde por defecto para las notas que no están seleccionadas.
     * Es una variable final para garantizar que no se modifique en tiempo de ejecución.
     */
    private final Border defaultBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    
    /**
     * El borde para la nota que está actualmente seleccionada.
     * Su color y grosor la distinguen visualmente de las demás.
     */
    private final Border selectedBorder = BorderFactory.createLineBorder(new Color(155, 182, 255), 2);
    
    /**
     * El listener que se encarga de monitorear los cambios de texto en el área de contenido.
     * Es una variable final porque se inicializa una vez y no cambia.
     */
    private final DocumentListener documentListener;
    
    /**
     * Referencia al listener que el controlador proporciona para guardar cambios.
     * Se usa para notificar al controlador cuando el usuario edita una nota.
     */
    private VistaNotasInterface.GuardarCambiosListener guardarCambiosListener;
    
    /**
     * Referencia al listener que el controlador proporciona para la selección de notas.
     * Se usa para notificar al controlador cuando el usuario hace clic en una nota.
     */
    private VistaNotasInterface.NotaSeleccionadaListener notaSeleccionadaListener;
    
    // ====================================================================================
    // CONSTRUCTOR DE LA CLASE
    // ¿Por qué está organizado así? Para que el constructor sea más legible.
    // Se delega la lógica compleja de inicialización a métodos privados con nombres
    // descriptivos, siguiendo el Principio de Responsabilidad Única.
    // ====================================================================================

    public VistaNotas() {
        // Inicialización de listeners
        this.documentListener = crearDocumentListener();
        
        // Inicialización de los componentes de la interfaz de usuario.
        // Este método es autogenerado por NetBeans/Swing.
        initComponents();
        
        // Configuración adicional de la UI
        configurarUI();
        
        // Asignación de listeners a los componentes
        asignarListeners();
    }

    /**
     * Crea e inicializa el DocumentListener para el área de texto de la nota.
     * Su propósito es notificar al controlador en tiempo real cada vez que el
     * usuario escribe o borra texto.
     * @return Una nueva instancia de DocumentListener.
     */
    private DocumentListener crearDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void removeUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void changedUpdate(DocumentEvent e) { notificarCambios(); }
            
            private void notificarCambios() {
                // Solo notifica al controlador si hay un listener asignado y una nota seleccionada.
                if (guardarCambiosListener != null && notaSeleccionadaIndex >= 0) {
                    guardarCambiosListener.onGuardarCambios(txtCuerpoDerContenidoNota.getText());
                }
            }
        };
    }
    
    /**
     * Realiza configuraciones visuales adicionales que no están en initComponents().
     * Su propósito es centralizar la lógica de personalización de la ventana.
     */
    private void configurarUI() {
        setLocationRelativeTo(null); // Centra la ventana en la pantalla.
        
        // Configura el layout para que las notas se apilen verticalmente.
        panelCuerpoIzquierdo.setLayout(new javax.swing.BoxLayout(panelCuerpoIzquierdo, javax.swing.BoxLayout.Y_AXIS));

        // Configura los botones de radio para los temas, asegurando que solo uno pueda ser seleccionado.
        ButtonGroup temas = new ButtonGroup();
        temas.add(opcTemaClaro);
        temas.add(opcTemaOscuro);
        opcTemaClaro.setSelected(true);
    }
    
    /**
     * Asigna todos los listeners necesarios a los componentes de la UI.
     * Su propósito es separar la asignación de eventos del resto de la inicialización.
     */
    private void asignarListeners() {
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(this.documentListener);
    }
    
    // ====================================================================================
    // MÉTODOS DE LA INTERFAZ
    // Estos métodos son el "contrato" de la vista. El controlador los usa para
    // enviar comandos y recibir información sin saber los detalles internos de la UI.
    // ====================================================================================

    @Override
    public void setNotaSeleccionadaListener(VistaNotasInterface.NotaSeleccionadaListener listener) {
        this.notaSeleccionadaListener = listener;
    }

    @Override
    public void setGuardarCambiosListener(VistaNotasInterface.GuardarCambiosListener listener) {
        this.guardarCambiosListener = listener;
    }

    @Override
    public void addCrearNotaListener(ActionListener l) {
        btnElementoNuevaNota.addActionListener(l);
        opcNuevaNota.addActionListener(l);
    }

    @Override
    public void addGuardarNotaListener(ActionListener l) {
        btnElementoGuardarTxt.addActionListener(l);
        opcGuardarElemento.addActionListener(l);
    }

    @Override
    public void addCambiarTemaOscuro(ActionListener l){
        opcTemaOscuro.addActionListener(l);
    }
    
    @Override
    public void addCambiarTemaClaro(ActionListener l){
        opcTemaClaro.addActionListener(l);
    }
    
    @Override
    public void addVisitarDocumentacion(ActionListener l){
        opcDocumentacion.addActionListener(l);
    }
    @Override
    public void addVisitarSitioWeb(ActionListener l){
        opcSitioWeb.addActionListener(l);
    }
    
    @Override
    public void addLimpiarTodoListener(ActionListener l) {
        btnElementoLimpiarTodo.addActionListener(l);
        opcLimipiarTodo.addActionListener(l);
    }

    @Override
    public void addBuscarListener(ActionListener l) {
        txtElementoBuscarNotaActual.addActionListener(l);
    }

    @Override
    public String getTextoBusqueda() {
        return txtElementoBuscarNotaActual.getText();
    }

    @Override
    public String getContenidoNota() {
        return txtCuerpoDerContenidoNota.getText();
    }

    @Override
    public void setContenidoNota(String contenido) {
        // ¿Por qué se remueve y se agrega el listener?
        // Esto es un patrón común para evitar que el listener se active cuando el
        // controlador actualiza el texto programáticamente, lo que podría
        // causar un bucle de eventos no deseado.
        txtCuerpoDerContenidoNota.getDocument().removeDocumentListener(documentListener);
        txtCuerpoDerContenidoNota.setText(contenido);
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(documentListener);
    }

    @Override
    public int getNotaSeleccionadaIndex() {
        return notaSeleccionadaIndex;
    }
    
    /**
     * Actualiza la selección visual de las notas en el panel izquierdo.
     * Su propósito es puramente visual: cambiar el borde de la nota seleccionada.
     * No dispara ningún evento al controlador.
     * @param index El índice de la nota que debe ser marcada como seleccionada.
     */
    @Override
    public void seleccionarNota(int index) {
        this.notaSeleccionadaIndex = index;
        for (int i = 0; i < panelCuerpoIzquierdo.getComponentCount(); i++) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(i);
            label.setBorder(i == index ? selectedBorder : defaultBorder);
        }
    }

    /**
     * Muestra las notas en el panel lateral izquierdo.
     * Su propósito es renderizar la lista de notas que le proporciona el controlador.
     * @param contenidos Una lista de Strings con el contenido de cada nota.
     */
    @Override
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
            
            // ¿Por qué se agrega el MouseListener aquí?
            // Cada JLabel necesita su propio listener para notificar a la vista (y al controlador)
            // cuando se ha hecho clic en él. La lógica de qué hacer con ese clic se delega al
            // notaSeleccionadaListener.
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

    /**
     * Extrae un título de una nota (las primeras 3 palabras).
     * Es un método helper privado que ayuda a la vista a renderizar los títulos de las notas.
     * @param contenido El contenido completo de la nota.
     * @return El título de la nota, limitado a tres palabras.
     */
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

    /**
     * Actualiza el título visual de una nota en el panel izquierdo.
     * Se usa cuando el usuario edita el contenido de una nota.
     * @param index El índice de la nota a actualizar.
     * @param contenido El nuevo contenido de la nota para generar el título.
     */
    @Override
    public void actualizarTituloNota(int index, String contenido) {
        if (index >= 0 && index < panelCuerpoIzquierdo.getComponentCount()) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(index);
            label.setText(getTituloNota(contenido));
        }
    }
    
    @Override
    public void actualizarConteoNotas(int cantidad){
        lblEncabezadoNot.setText(cantidad + " notas act.");
    }
    
    @Override
    public void addCaretListener(CaretListener l) {
        txtCuerpoDerContenidoNota.addCaretListener(l);
    }
    
    @Override
    public void setInformacionInferior(String texto) {
        lblInferiorInformacion.setText(texto);
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

        opcGuardarElemento.setText("Guardar TXT");
        menuOpciones.add(opcGuardarElemento);

        opcLimipiarTodo.setText("Limpiar Todo");
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

        opcDocumentacion.setText("Documentacion");
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

## `FlashNotes\src\main\java\com\mycompany\flashnotes\vista\VistaNotasInterface.java`

```java
package com.mycompany.flashnotes.vista;

import java.awt.event.ActionListener;
import java.util.List;
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

    // Métodos para obtener datos de la vista
    String getTextoBusqueda();
    String getContenidoNota();
    int getNotaSeleccionadaIndex();

    // Métodos para que el controlador actualice la vista
    void setContenidoNota(String contenido);
    void mostrarNotas(List<String> contenidos);
    void seleccionarNota(int index);
    void actualizarTituloNota(int index, String contenido);
    void actualizarConteoNotas(int cantidad);
    void addCaretListener(CaretListener listener);
    void setInformacionInferior(String texto);
    
    // Método para hacer visible la ventana de la aplicación
    void setVisible(boolean visible);
}```

