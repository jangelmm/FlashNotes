package com.mycompany.flashnotes.control;


import com.mycompany.flashnotes.modelo.GestorNotas;
import com.mycompany.flashnotes.vista.VistaNotas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author jesus
 */
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

    private void crearNota() {
        gestor.crearNota("");
        actualizarVista();
    }

    private void guardarNota() {
        int idx = vista.getNotaSeleccionadaIndex();
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().getAbsolutePath();
            gestor.guardarNota(idx, ruta);
        }
    }

    private void limpiarTodas() {
        gestor.eliminarTodas();
        vista.setContenidoNota("");
        actualizarVista();
    }

    private void buscarNota() {
        String texto = vista.getTextoBusqueda();
        List<Integer> resultados = gestor.buscar(texto);
        if (!resultados.isEmpty()) {
            int primero = resultados.get(0);
            vista.mostrarNotas(gestor.getContenidoNotas());
            vista.seleccionarNota(primero);
            vista.setContenidoNota(gestor.getNotas().get(primero).getContenido());
        }
    }

    private void actualizarVista() {
        vista.mostrarNotas(gestor.getContenidoNotas());
    }
}

