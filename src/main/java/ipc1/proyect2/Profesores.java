package ipc1.proyect2;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author danis
 */
public class Profesores extends Usuario implements Serializable{
    public String codigop;
    public String nombrep;
    public String apellidop;
    public String correop;
    public String contrasenap;
    public String generop;
    
    public LinkedList<Curso>cursos = new LinkedList();
    
    public Profesores(String codigop,String nombrep,String apellidop, String correop, String contrasenap, 
            String generop,String codigolog, String contrasenalog) {
        super(codigolog, contrasenalog);
        this.codigop=codigop;
				this.id = codigop;
				this.tipoUsuario = "profesor";
        this.nombrep=nombrep;
        this.apellidop=apellidop;
        this.correop=correop;
        this.generop=generop;
        this.contrasenap =contrasenap;
    }
    
    public void agregarCurso(String codigo, String nombre, String creditos,String profesor ,String alumnos){
        Curso curso = new Curso(codigo, nombre, creditos, profesor);
        this.cursos.add(curso);
    }

    public String getCodigop() {
        return codigop;
    }

    public void setCodigop(String codigop) {
        this.codigop = codigop;
    }

    public String getNombrep() {
        return nombrep;
    }

    public void setNombrep(String nombrep) {
        this.nombrep = nombrep;
    }

    public String getApellidop() {
        return apellidop;
    }

    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    public String getCorreop() {
        return correop;
    }

    public void setCorreop(String correop) {
        this.correop = correop;
    }

    public String getContrasenap() {
        return contrasenap;
    }

    public void setContrasenap(String contrasenap) {
        this.contrasenap = contrasenap;
    }

    public String getGenerop() {
        return generop;
    }

    public void setGenerop(String generop) {
        this.generop = generop;
    }
    
    
}
