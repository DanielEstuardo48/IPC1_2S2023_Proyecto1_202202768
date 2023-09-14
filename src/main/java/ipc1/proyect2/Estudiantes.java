package ipc1.proyect2;

import java.io.Serializable;

/**
 *
 * @author danis
 */
public class Estudiantes extends Usuario implements Serializable{
    public String codigoe;
    public String nombree;
    public String apellidoe;
    public String correoe;
    public String contrasenae;
    public String generoe;

    public Estudiantes(String codigoe,String nombree,String apellidoe, String correoe, String contrasenae, 
            String generoe,String codigolog, String contrasenalog) {
        super(codigolog, contrasenalog);
				this.tipoUsuario = "estudiante";
        this.codigoe=codigoe;
        this.id = codigoe;
        this.nombree=nombree;
        this.apellidoe=apellidoe;
        this.correoe=correoe;
        this.generoe=generoe;
    }

    public String getCodigoe() {
        return codigoe;
    }

    public void setCodigoe(String codigoe) {
        this.codigoe = codigoe;
    }

    public String getNombree() {
        return nombree;
    }

    public void setNombree(String nombree) {
        this.nombree = nombree;
    }

    public String getApellidoe() {
        return apellidoe;
    }

    public void setApellidoe(String apellidoe) {
        this.apellidoe = apellidoe;
    }

    public String getCorreoe() {
        return correoe;
    }

    public void setCorreoe(String correoe) {
        this.correoe = correoe;
    }

    public String getContrasenae() {
        return contrasenae;
    }

    public void setContrasenae(String contrasenae) {
        this.contrasenae = contrasenae;
    }

    public String getGeneroe() {
        return generoe;
    }

    public void setGeneroe(String generoe) {
        this.generoe = generoe;
    }
    
    
}
