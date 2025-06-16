package cl.bibliotecaduocuc.bibliotecaduocuc;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String rut;
    private String nombre;
    private List<Libro> librosPrestados; //Cree una lista privada para dejar regsitro de los libros que se encuenrtan prestados actualmente
    
    /*--Creación de mis constructores---*/
    public Usuario(String rut, String nombre){
    this.rut=rut;
    this.nombre=nombre;
    this.librosPrestados = new ArrayList<>();    
    }
    
    /*-----Creación de mis getter y Setters------*/

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }
    
    public List<Libro> getLibrosPrestados(){
        return librosPrestados;
    }
    
    public void prestarLibro(Libro libro){
        librosPrestados.add(libro);
    }
    
    public void devolverLibro(Libro libro){
        librosPrestados.remove(libro);
    }

}
