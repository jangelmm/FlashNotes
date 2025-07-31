package com.mycompany.flashnotes.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaNotas extends JFrame {
    private JTextArea areaNotas;
    private JButton btnGuardar;

    public VistaNotas() {
        setTitle("FlashNotes");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        areaNotas = new JTextArea();
        btnGuardar = new JButton("Guardar");

        JScrollPane scrollPane = new JScrollPane(areaNotas);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);
    }

    public void setContenido(String contenido) {
        areaNotas.setText(contenido);
    }

    public String getContenido() {
        return areaNotas.getText();
    }

    public void addGuardarListener(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }
}