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
}