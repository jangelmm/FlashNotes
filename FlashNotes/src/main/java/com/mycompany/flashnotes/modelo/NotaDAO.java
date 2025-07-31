package com.mycompany.flashnotes.modelo;

import java.io.*;
import java.util.*;

public class NotaDAO {
    private static final String FILE_PATH = "notas.txt";

    public List<Nota> cargarNotas() {
        List<Nota> notas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                notas.add(new Nota(linea));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notas;
    }

    public void guardarNotas(List<Nota> notas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Nota nota : notas) {
                bw.write(nota.getContenido());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}