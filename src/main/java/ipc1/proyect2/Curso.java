package ipc1.proyect2;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author danis
 */
public class Curso implements Serializable {
    public String codigo;
    public String nombre;
    public String creditos;
    public static LinkedList<String> idsEstudiantes = new LinkedList();
    public String codigoProfesor;
    public static LinkedList<Actividad> actividades = new LinkedList();
    
    public Curso(String codigo, String nombre, String creditos, String codigoProfesor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.codigoProfesor = codigoProfesor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getProfesor(String codigo) {
			return null;
    }

    public void setProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }
		
		public void addEstudiante(String idEstudiante) {
			if(!this.idsEstudiantes.contains(idEstudiante)) {
				this.idsEstudiantes.add(idEstudiante);
			}
		}
    
               
    /*ublic static LinkedList<Actividad> getActividades() {
		LinkedList<Actividad> actividades = new LinkedList();
		for(Usuario u : usuarios) {
			if(u.tipoUsuario.equals("profesor")) {
				profesores.add((Profesores)u);
			}
		}
		return profesores;
	}*/
    
  /*  public Profesores getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesores profesor) {
        this.profesor = profesor;
    }*/

    //public LinkedList<Estudiantes> getEstudiantes() {
    //    return estudiantes;
    //}

    //public void setEstudiantes(LinkedList<Estudiantes> estudiantes) {
    //    this.estudiantes = estudiantes;
    //}
    
    

}
