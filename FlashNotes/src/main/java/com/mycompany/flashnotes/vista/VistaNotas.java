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
 * La clase VistaNotas representa la interfaz de usuario de la aplicación.
 * Es la "Vista" en el patrón de diseño MVC. Su única responsabilidad es
 * mostrar la información al usuario y capturar sus interacciones (clics,
 * entrada de texto, etc.) para luego notificar al controlador.
 *
 * Esta clase no contiene lógica de negocio; solo lógica de presentación.
 *
 * @author jesus
 */
public class VistaNotas extends javax.swing.JFrame {
    
    // El índice de la nota que está seleccionada actualmente en la lista.
    private int notaSeleccionadaIndex = -1;
    
    // Estilos de borde para los JLabels que representan las notas.
    private final Border defaultBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private final Border selectedBorder = BorderFactory.createLineBorder(new Color(155, 182, 255), 2);
    
    // Un único DocumentListener para el JTextArea, usado para notificar cambios.
    private final DocumentListener documentListener;
    
    // Listener para guardar cambios de texto, implementado en el controlador.
    private GuardarCambiosListener guardarCambiosListener;
    
    // Listener para seleccionar una nota, implementado en el controlador.
    private NotaSeleccionadaListener notaSeleccionadaListener;
    
    /**
     * Crea y configura una nueva instancia del formulario VistaNotas.
     */
    public VistaNotas() {
        // Inicializa el DocumentListener una sola vez. Se usará para detectar
        // cambios en el texto del JTextArea y notificar al controlador.
        documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void removeUpdate(DocumentEvent e) { notificarCambios(); }
            @Override
            public void changedUpdate(DocumentEvent e) { notificarCambios(); }
            
            /**
             * Llama al listener para guardar cambios si está disponible y si
             * hay una nota seleccionada.
             */
            private void notificarCambios() {
                if (guardarCambiosListener != null && notaSeleccionadaIndex >= 0) {
                    guardarCambiosListener.onGuardarCambios(txtCuerpoDerContenidoNota.getText());
                }
            }
        };
        
        initComponents(); // Método generado por el diseñador de GUI de NetBeans.
        setLocationRelativeTo(null);
        
        // Agrega el listener inicial al JTextArea.
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(documentListener);
        
        // Inicializa el panel izquierdo para que use un BoxLayout vertical.
        panelCuerpoIzquierdo.setLayout(new javax.swing.BoxLayout(panelCuerpoIzquierdo, javax.swing.BoxLayout.Y_AXIS));

        // Agrupa los botones de radio para los temas.
        ButtonGroup temas = new ButtonGroup();
        temas.add(opcTemaClaro);
        temas.add(opcTemaOscuro);
        opcTemaOscuro.setSelected(true);
    }
    
    /**
     * Interfaz para notificar al controlador cuando una nota es seleccionada.
     */
    public interface NotaSeleccionadaListener {
        void onNotaSeleccionada(int index);
    }
        
    /**
     * Establece el listener que se llamará cuando se seleccione una nota.
     * @param listener La implementación de la interfaz NotaSeleccionadaListener.
     */
    public void setNotaSeleccionadaListener(NotaSeleccionadaListener listener) {
        this.notaSeleccionadaListener = listener;
    }

    /**
     * Interfaz para notificar al controlador cuando el contenido de la nota cambia.
     */
    public interface GuardarCambiosListener {
        void onGuardarCambios(String contenido);
    }
        
    /**
     * Establece el listener que se llamará cuando el contenido de la nota cambie.
     * @param listener La implementación de la interfaz GuardarCambiosListener.
     */
    public void setGuardarCambiosListener(GuardarCambiosListener listener) {
        this.guardarCambiosListener = listener;
    }
        
    /**
     * Agrega un ActionListener para el botón y el ítem de menú de "Nueva Nota".
     * @param l El ActionListener que manejará el evento.
     */
    public void addCrearNotaListener(ActionListener l) {
        btnElementoNuevaNota.addActionListener(l);
        opcNuevaNota.addActionListener(l);
    }

    /**
     * Agrega un ActionListener para el botón y el ítem de menú de "Guardar TXT".
     * @param l El ActionListener que manejará el evento.
     */
    public void addGuardarNotaListener(ActionListener l) {
        btnElementoGuardarTxt.addActionListener(l);
        opcGuardarElemento.addActionListener(l);
    }

    /**
     * Agrega un ActionListener para el botón y el ítem de menú de "Limpiar Todo".
     * @param l El ActionListener que manejará el evento.
     */
    public void addLimpiarTodoListener(ActionListener l) {
        btnElementoLimpiarTodo.addActionListener(l);
        opcLimipiarTodo.addActionListener(l);
    }

    /**
     * Agrega un ActionListener para el campo de texto de búsqueda.
     * @param l El ActionListener que manejará el evento.
     */
    public void addBuscarListener(ActionListener l) {
        txtElementoBuscarNotaActual.addActionListener(l);
    }

    /**
     * Obtiene el texto del campo de búsqueda.
     * @return El texto que el usuario ha introducido en el campo de búsqueda.
     */
    public String getTextoBusqueda() {
        return txtElementoBuscarNotaActual.getText();
    }

    /**
     * Obtiene el contenido actual del área de texto de la nota.
     * @return El contenido de la nota.
     */
    public String getContenidoNota() {
        return txtCuerpoDerContenidoNota.getText();
    }

    /**
     * Establece el contenido del área de texto de la nota.
     * Antes de cambiar el texto, se remueve y vuelve a agregar el listener
     * para evitar que se dispare el evento de cambio de documento.
     *
     * @param contenido El nuevo texto para el JTextArea.
     */
    public void setContenidoNota(String contenido) {
        txtCuerpoDerContenidoNota.getDocument().removeDocumentListener(documentListener);
        txtCuerpoDerContenidoNota.setText(contenido);
        txtCuerpoDerContenidoNota.getDocument().addDocumentListener(documentListener);
    }

    /**
     * Obtiene el índice de la nota seleccionada actualmente.
     * @return El índice de la nota seleccionada.
     */
    public int getNotaSeleccionadaIndex() {
        return notaSeleccionadaIndex;
    }
    
    /**
     * Actualiza la selección visual de las notas en el panel izquierdo.
     * @param index El índice de la nota que debe ser marcada como seleccionada.
     */
    public void seleccionarNota(int index) {
        this.notaSeleccionadaIndex = index;
        for (int i = 0; i < panelCuerpoIzquierdo.getComponentCount(); i++) {
            JLabel label = (JLabel) panelCuerpoIzquierdo.getComponent(i);
            label.setBorder(i == index ? selectedBorder : defaultBorder);
        }
    }

    /**
     * Muestra una lista de notas en el panel izquierdo.
     * Este método elimina todas las notas existentes y las recrea.
     * @param contenidos Una lista de cadenas de texto (contenidos) que representan las notas.
     */
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

    /**
     * Genera un título corto para una nota a partir de su contenido.
     * @param contenido El contenido completo de la nota.
     * @return Un título de hasta 3 palabras.
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
     * Actualiza el título de una nota específica en el panel izquierdo.
     * @param index El índice de la nota cuyo título se va a actualizar.
     * @param contenido El nuevo contenido de la nota para generar el nuevo título.
     */
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
