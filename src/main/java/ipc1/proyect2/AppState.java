package ipc1.proyect2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danis
 */
public class AppState {
	public static Usuario usuariolog;

	public static LinkedList<Usuario>usuarios = new LinkedList();
	public static LinkedList<Curso> cursos  = new LinkedList();
        public static String rutaUsuariosSerializados = "./DatosSerializados/appState.bin";

	public static LinkedList<Profesores> getProfesores() {
		LinkedList<Profesores> profesores = new LinkedList();
		for(Usuario u : usuarios) {
			if(u.tipoUsuario.equals("profesor")) {
				profesores.add((Profesores)u);
			}
		}
		return profesores;
	}

	public static Profesores findProfesor(String id) {
		for(Profesores profesor : getProfesores()) {
			if(profesor.codigop.equals(id)) {
				return profesor;
			}
		}
		return null;
	}

	public static Estudiantes findEstudiante(String id) {
		for(Estudiantes estudiante : getEstudiantes()) {
			if(estudiante.codigoe.equals(id)) {
				return estudiante;
			}
		}
		return null;
	}
        
	public static LinkedList<Estudiantes> getEstudiantes() {
			LinkedList<Estudiantes> estudiantes = new LinkedList();
			for(Usuario u: usuarios){
					if(u.tipoUsuario.equals("estudiante")){
							estudiantes.add((Estudiantes)u);
					}
			}
			return estudiantes;
	}

	public static void addEstudiante(Estudiantes estudiante) {
		boolean estudianteExiste = false;
		for(Estudiantes e : getEstudiantes()) {
			if(e.codigoe.equals(estudiante.codigoe)) {
				estudianteExiste = true;
			}
		}
		if(!estudianteExiste) {
			usuarios.add(estudiante);
		}
	}
       
        public static void serializar() {
            File file = new File(rutaUsuariosSerializados);

            // Creamos la carpeta donde iran los archivos serializados, si no existia
            if (!file.exists()) {
                file.getParentFile().mkdir();
            }

             // Se crea el archvio donde se va serializar
            try {
                file.createNewFile();
            } catch(IOException ex) {
                System.out.println("No se pudo crear el archvio de serialización.");
            }

            // Se escriben los datos de serializados en sobre el archivo
            try {
                FileOutputStream fos = new FileOutputStream(rutaUsuariosSerializados);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(usuarios);
            } catch (FileNotFoundException ex) {
                System.out.println("No se encotro el archivo para serializar");
            } catch (IOException ex) {
                System.out.println("Ocurrio un error al intentar escribir sobre el archivo.");
            }
        }
        
        public static void deserializar() {
        try {
            File file = new File(rutaUsuariosSerializados);
            if (!file.exists()) {
                return;
            }
            FileInputStream fis = new FileInputStream(rutaUsuariosSerializados);
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (LinkedList<Usuario>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AppState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
