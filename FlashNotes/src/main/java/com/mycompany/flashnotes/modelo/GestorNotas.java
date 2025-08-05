package com.mycompany.flashnotes.modelo;

import com.mycompany.flashnotes.persistencia.NotaDAO;
import java.util.ArrayList;
import java.util.List;

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
        String textoLower = texto.toLowerCase();
        for (int i = 0; i < notas.size(); i++) {
            if (notas.get(i).getContenido().toLowerCase().contains(textoLower)) {
                resultados.add(i);
            }
        }
        return resultados;
    }

    public List<Nota> getNotas() {
        return new ArrayList<>(notas);
    }
    
    // Método agregado para obtener una nota específica
    public Nota getNota(int indice){
        if (indice >= 0 && indice < notas.size()) {
            return notas.get(indice);
        }
        return null;
    }

    public List<String> getContenidoNotas() {
        List<String> contenidos = new ArrayList<>();
        for (Nota nota : notas) {
            contenidos.add(nota.getContenido());
        }
        return contenidos;
    }
    
    public void actualizarContenidoNota(int indice, String contenido) {
        if (indice >= 0 && indice < notas.size()) {
            notas.get(indice).setContenido(contenido);
        }
    }   
}