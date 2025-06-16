
package cl.bibliotecaduocuc.bibliotecaduocuc;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private boolean disponible;
    
    /*------Creo mis constructores-----*/
    
    public Libro(String isbn, String titulo, String autor){
        this.isbn=isbn;
        this.titulo=titulo;
        this.autor=autor;
        this.disponible=true;
    }
    
    /*-----Creo mis Getter y Setters-----*/
    public String getIsbn(){
        return isbn;
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public String getAutor(){
        return autor;
    }
    
    public boolean isDisponible(){
    return disponible;
    }
    
    public void setDisponible(boolean disponible){
        this.disponible=disponible;
    }
    
    
    
}
