package cl.bibliotecaduocuc.bibliotecaduocuc;

public class LibroNoEncontradoException extends Exception{
    public LibroNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
