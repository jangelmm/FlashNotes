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
        opcTemaOscuro.setSelected(true);
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
