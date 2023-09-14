package ipc1.proyect2;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author danis
 */
public class Administrador extends Usuario implements Serializable{
    public  LinkedList<Profesores> profesores = new LinkedList();
    
    public Administrador(String codigolog, String contrasenalog) {
        super(codigolog, contrasenalog);
				this.tipoUsuario = "administrador";
    }
    
    /*public void agregarprofesor(String codigop, String nombre, String apellido,
            String correo,String contrasena, String genero, String codigolog, String contralog){
        Profesores profesor = new Profesores(codigop, nombre, apellido,
                correo, contrasena, genero, codigolog, contralog);
        this.profesores.add(profesor);
    }*/
}
