package com.mycompany.flashnotes;

import com.mycompany.flashnotes.control.ControladorNotas;
import com.mycompany.flashnotes.modelo.NotaDAO;
import com.mycompany.flashnotes.vista.VistaNotas;

/**
 *
 * @author jesus
 */

public class FlashNotes {
    public static void main(String[] args) {
        VistaNotas vista = new VistaNotas();
        NotaDAO dao = new NotaDAO();
        new ControladorNotas(vista, dao);
        vista.setVisible(true);
    }
}