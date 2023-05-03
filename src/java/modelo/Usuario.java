package modelo;

public class Usuario {
    private final int id;
    private String correo;
    private String clave;
    
    public Usuario(int id, String correo, String clave) {
        this.id = id;
        this.correo = correo;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String contrasena) {
        this.clave = contrasena;
    }
}

