package cl.bibliotecaduocuc.bibliotecaduocuc;
import cl.bibliotecaduocuc.bibliotecaduocuc.excepciones.UsuarioNoEncontradoException;
import cl.bibliotecaduocuc.bibliotecaduocuc.excepciones.UsuarioDuplicadoException;
import cl.bibliotecaduocuc.bibliotecaduocuc.excepciones.LibroNoEncontradoException;
import cl.bibliotecaduocuc.bibliotecaduocuc.excepciones.LibroYaPrestadoException;
import java.io.*;
import java.util.*;

public class Biblioteca {
    private ArrayList<Libro> libros;
    private HashMap<String, Usuario> usuarios;
    
    public Biblioteca(){
        libros = new ArrayList<>();
        usuarios = new HashMap<>();
    }
    
    /*----Aquí se cargarán los datos desde un archivo CSV----*/
    public void cargarLibrosDesdeCSV(String ruta) throws IOException{
        usuarios.clear();/*Lo implementé para limpiar datos previos*/
        try(BufferedReader br=new BufferedReader(new FileReader(ruta))){
        String linea;
        br.readLine();/*Sirve para saltarse la cabecera del archivo*/
        while((linea=br.readLine()) !=null){
            String[] datos = linea.split(",");
            if(datos.length == 3){
                libros.add(new Libro(datos[0], datos[1], datos[2]));
                }
            }
        }
    }
    
    public void cargarUsuariosDesdeCSV(String ruta) throws IOException{
        usuarios.clear();
        File archivo = new File(ruta);
        
        /*--- Esto crea el archivo si no existe ----*/
        if (!archivo.exists()) {
            try (FileWriter fw = new FileWriter(archivo)) {
                fw.write("RUT,Nombre\n");  /*---Cabecera---*/
        }
    }
            if (!archivo.exists()) {
        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write("RUT,Nombre\n");  // Cabecera
        }
    }
        
        try(BufferedReader br=new BufferedReader(new FileReader(ruta))){
            String linea;
            br.readLine();
            while ((linea=br.readLine())!=null){
                String[] datos=linea.split(",");
                if(datos.length ==2){
                    usuarios.put(datos[0],new Usuario(datos[0],datos[1]));
                }
            }
        }
    }
    
    /*---- Búsqueda de libros con manejo de las excepciones----*/
    public Libro buscarLibroPorISBN(String isbn) throws LibroNoEncontradoException{
        for(Libro libro : libros){
            if(libro.getIsbn().equals(isbn)){
                return libro;
            }
        }
        throw new LibroNoEncontradoException("Libro con ISBN "+ isbn + "no encontrado");
    }
    
    /*---Préstamo de libros con manejo de excepciones-----*/
    public void prestarLibro(String isbn, String rutUsuario)
            throws LibroNoEncontradoException, LibroYaPrestadoException, UsuarioNoEncontradoException{
        Libro libro = buscarLibroPorISBN(isbn);
        Usuario usuario = usuarios.get(rutUsuario);
        
        /*Aquí valido si el usuario está en mi lista de usuarios.csv*/
        if (usuario==null){
                throw new UsuarioNoEncontradoException("Usuario con RUT " + rutUsuario + "no resgitrado");
            }
        
        if (!libro.isDisponible()){
            throw new LibroYaPrestadoException("El libro "+isbn+" ya está prestado");
        }
        
        libro.setDisponible(false);
        usuario.prestarLibro(libro);
        
    }
    /*----Generador de informe de préstamos de libros----*/
    public void generarInformePrestamos(String rutaSalida) throws IOException{
        try(FileWriter fw=new FileWriter(rutaSalida)){
            for(Usuario usuario : usuarios.values()){
                for(Libro libro : usuario.getLibrosPrestados()){
                    fw.write(usuario.getRut()+ "," +libro.getIsbn()+"\n");
                }
            }
        }
    }
    
    /*--------Método para leer la lista de libros-------*/
    public String listarLibros(){
        if(libros.isEmpty()){
            return "No hay libros registrados en el sistema";
        }
        StringBuilder sb=new StringBuilder();
        sb.append("\n===LISTA DE LIBROS EN LA BIBLIOTECA===\n");
        sb.append(String.format("%-15s %-30s %-25s %-12s%n", 
            "CÓDIGO", "TÍTULO", "AUTOR", "DISPONIBLE"));
        sb.append("--------------------------------------------\n");
        
        for(Libro libro : libros){
            sb.append(String.format("%-15s %-30s %-25s %-12s%n", 
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.isDisponible() ? "Sí" : "No"));
        }
        return sb.toString();
    }
    
    /*---Metodo para registrar usuario en el archivo CSV---*/
    public void registrarUsuario(String rut, String nombre) throws UsuarioDuplicadoException,IOException{
        rut=rut.trim().replace(".", "").replace(" ","");
            // Validar si el usuario ya existe
    if (usuarios.containsKey(rut)) {
        throw new UsuarioDuplicadoException("El RUT " + rut + " ya está registrado");
    }
    
    /*-----------Crear nuevo usuario--------*/
    Usuario nuevoUsuario = new Usuario(rut, nombre);
    usuarios.put(rut, nuevoUsuario);
    
    /*------- Guardar en el archivo CSV (modo append)-------*/
    guardarUsuarioEnCSV("usuarios.csv", nuevoUsuario);
}

    private void guardarUsuarioEnCSV(String ruta, Usuario usuario) throws IOException {
        try (FileWriter fw = new FileWriter(ruta, true)) {  // true para modo append
            fw.append(usuario.getRut())
              .append(",")
              .append(usuario.getNombre())
              .append("\n");
        }
    }
}
