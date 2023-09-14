package ipc1.proyect2;

import java.io.Serializable;

/**
 *
 * @author danis
 */
public class Usuario implements Serializable {
	public String id;
  public String Codigolog;
  public String Contrasenalog;
	public String tipoUsuario;

    public Usuario(String codigolog, String contrasenalog) {
        this.Codigolog = codigolog;
        this.Contrasenalog = contrasenalog;
    }
  
}
