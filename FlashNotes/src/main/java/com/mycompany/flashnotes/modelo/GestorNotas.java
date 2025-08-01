package com.mycompany.flashnotes.modelo;

import com.mycompany.flashnotes.persistencia.NotaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
public class GestorNotas {
    private List<Nota> notas;
    private NotaDAO notaDAO;
    
    public GestorNotas() {
        this.notas = new ArrayList<>();
        this.notaDAO = new NotaDAO();
        //En caso de crear una Nota inicial()
    }
    
    public void crearNota(String contenido){
        notas.add(new Nota(contenido));
    }
    
    public void eliminarNota(int indice){
        if(indice >= 0 && indice < notas.size()){
            notas.remove(indice);
        }
    }
    
    public boolean guardarNota(int indice, String rutaArchivo){
        if(indice >= 0 && indice < notas.size()){
            Nota notaAGuardar = notas.get(indice);
            return notaDAO.guardarNotaEnArchivo(notaAGuardar, rutaArchivo);
        }
        return false;
    }
    
    //... Implementación de buscar, eliminarTodas, etc.
    
    //Retornar las Notas
    public List<String> getContenidoNotas(){
        List<String> contenidos = new ArrayList<>();
        for(Nota nota: notas){
            contenidos.add(nota.getContenido());
        }
        return contenidos;
    }
}
