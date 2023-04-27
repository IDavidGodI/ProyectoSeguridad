
package ConexionDB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion { 
    Connection conexion =  null;
    Statement ControladorSQL;
    public Conexion(){
        
    }
    public Conexion(String url, String bd, String usuario, String clave) {
        if (!url.endsWith("/")) url+="/";
        try {
            conexion = DriverManager.getConnection(url+bd,usuario,clave);
            ControladorSQL = conexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String encriptarClave(String clave){
        MessageDigest md;
        byte[] hash = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            hash = md.digest(clave.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        // convierte el hash a una cadena hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        String hashHexadecimal = sb.toString();
        return hashHexadecimal;

    }
    
    public void registrarUsuario(String correo, String clave){
        String hashClave = encriptarClave(clave);
        System.out.println(hashClave);
//        ControladorSQL.executeUpdate("");
    }
}
