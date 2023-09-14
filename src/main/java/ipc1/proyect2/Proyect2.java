package ipc1.proyect2;

import gui.LoginJFrame;

/**
 *
 * @author danis
 */
public class Proyect2 {

    public static void main(String[] args) {
        AppState.deserializar();
        if(AppState.usuarios.isEmpty()) {
            Administrador administrador = new Administrador("admin", "admin");
            AppState.usuarios.add(administrador);
            
            AppState.usuarios.add(new Profesores("1", "Pedro", "Perez", "pedro@gmail.com", "password", "M", "pedrito", "password"));
            AppState.usuarios.add(new Profesores("2", "Sandra", "Torres", "sandra@gmail.com", "password", "F", "sandrita", "password"));
            AppState.usuarios.add(new Profesores("3", "Tomás", "Perez", "tomas@gmail.com", "password", "M", "tomas", "password"));

            
            Estudiantes estudiante = new Estudiantes("202202745", "Juan", "Perez", "juan@gmail.com", "pasword", "M", "juanito", "pasword");
            AppState.usuarios.add(estudiante);
        }

				if(AppState.cursos.isEmpty()) {
						Curso curso = new Curso("1", "Mate 1", "5", "1");
						curso.idsEstudiantes.add("202202745");
						AppState.cursos.add(curso);
						AppState.cursos.add(new Curso("2", "Mate 2", "5", "3"));
						AppState.cursos.add(new Curso("3", "Mate 3", "5", "1"));
				}

        LoginJFrame loginJFrame = new LoginJFrame();
    }
}
