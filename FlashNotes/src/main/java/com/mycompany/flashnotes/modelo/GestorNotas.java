package com.mycompany.flashnotes.modelo;

import com.mycompany.flashnotes.persistencia.NotaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
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
        for (int i = 0; i < notas.size(); i++) {
            if (notas.get(i).getContenido().contains(texto)) {
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
        notas.forEach(n -> contenidos.add(n.getContenido()));
        return contenidos;
    }
}
