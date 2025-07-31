package com.mycompany.flashnotes.control;

/**
 *
 * @author jesus
 */

import com.mycompany.flashnotes.modelo.Nota;
import com.mycompany.flashnotes.modelo.NotaDAO;
import com.mycompany.flashnotes.vista.VistaNotas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class ControladorNotas implements ActionListener {
    private VistaNotas vista;
    private NotaDAO notaDAO;
    private List<Nota> notas;

    public ControladorNotas(VistaNotas vista, NotaDAO notaDAO) {
        this.vista = vista;
        this.notaDAO = notaDAO;
        this.vista.addGuardarListener(this);

        this.notas = notaDAO.cargarNotas();
        String contenido = notas.stream()
                                .map(Nota::getContenido)
                                .collect(Collectors.joining("\n"));
        vista.setContenido(contenido);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] lineas = vista.getContenido().split("\\n");
        notas.clear();
        for (String linea : lineas) {
            notas.add(new Nota(linea));
        }
        notaDAO.guardarNotas(notas);
        JOptionPane.showMessageDialog(vista, "Notas guardadas correctamente.");
    }
}
