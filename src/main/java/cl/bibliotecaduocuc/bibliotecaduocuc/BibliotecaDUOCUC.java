package cl.bibliotecaduocuc.bibliotecaduocuc;

import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class BibliotecaDUOCUC {

    public static void main(String[] args) {
            Scanner scanner = new Scanner (System.in);
            Biblioteca biblioteca = new Biblioteca();

            try{
                biblioteca.cargarLibrosDesdeCSV("libros.csv");
                biblioteca.cargarUsuariosDesdeCSV("usuarios.csv");
            } catch(IOException e){
                System.err.println("Error al cargar archivos: "+e.getMessage());
            }


            /*---Creación de Menú Principal de la Biblioteca---*/
            while(true){
                System.out.println("================================");
                System.out.println("   Bienvenido a la Biblioteca   ");
                System.out.println("            DUOC UC             ");
                System.out.println("================================");
                System.out.println("1. Buscar libro por código");
                System.out.println("2. Prestar libro");
                System.out.println("3. Generar reporte del prestamo");
                System.out.println("4. Lista de libros disponibles");
                System.out.println("5. Registrar nuevo usuario");
                System.out.println("6. Salir");
                System.out.println("Por favor, ingresa una opcion (1-4): ");

            /*----------Aplico mi try catch de menú------------*/
            try{
            int opcionMenu = Integer.parseInt(scanner.nextLine());

            switch(opcionMenu){
                case 1:
                    System.out.println("Ingrese el libro código del libro buscado: ");
                    String isbnBuscar=scanner.nextLine();
                    try{
                        Libro libro = biblioteca.buscarLibroPorISBN(isbnBuscar);
                        System.out.println("Libro encontrado: "+ libro.getTitulo());
                    }catch(LibroNoEncontradoException e){
                        System.err.println("Error: "+ e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el código del libro ");
                    String isbnPrestamo= scanner.nextLine();
                    System.out.println("RUT del usuario ");
                    String rutUsuario=scanner.nextLine();
                    try{
                        biblioteca.prestarLibro(isbnPrestamo, rutUsuario);
                        System.out.println("Prestamo Exitoso");
                    } catch(LibroNoEncontradoException | LibroYaPrestadoException | UsuarioNoEncontradoException e){
                        System.err.println("Error: "+ e.getMessage());
                    }
                    break;

                case 3:
                    try{
                        biblioteca.generarInformePrestamos("prestamos.txt");
                        System.out.println("Informe generado exitosamente");
                    } catch (IOException e){
                        System.err.println("Error al generar informe: "+e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println(biblioteca.listarLibros());
                    break;
                    
                case 5:
                                         System.out.print("RUT del nuevo usuario: ");
                        String rut = scanner.nextLine();
                        System.out.print("Nombre completo: ");
                        String nombre = scanner.nextLine();
                        
                        try {
                            biblioteca.registrarUsuario(rut, nombre);
                            System.out.println("Usuario registrado exitosamente");
                        } catch (UsuarioDuplicadoException e) {
                            System.err.println("ERROR: " + e.getMessage());
                        } catch (IOException e) {
                            System.err.println("Error al guardar en archivo: " + e.getMessage());
                        }
                        break;   
                
                case 6:
                    System.out.println("Saliendo del sistema");
                    return;

                default:
                    System.out.println("Opcion inválida");



            }

            }catch(InputMismatchException e){
                System.err.println("Error: Debe ingresar un número entero válido");
                scanner.nextLine();
            }
        }
    }
}
