package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UsuarioDAO {
    Connection con = Conexion.getConexion();
    PreparedStatement ps;
    
    
    private String encriptarClave(String clave){
        MessageDigest md;
        byte[] hash = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            hash = md.digest(clave.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        String hashHexadecimal = sb.toString();
        return hashHexadecimal;

    }
    
    private PreparedStatement crearStatementUsurio(String sql, String correo, String clave) throws SQLException {
        PreparedStatement auxps = con.prepareStatement(sql);
        String hashClave = encriptarClave(clave);
        auxps.setString(1,correo);
        auxps.setString(2,hashClave);
        return ps;
    }

    public boolean ValidarUsuario(String correo, String clave) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE correo=? AND clave=?";
        ps = crearStatementUsurio(sql, correo, clave);
        ResultSet rs = ps.executeQuery();
        return rs!=null;
    }
    
    public boolean registrarUsuario(String correo, String clave) throws SQLException{
        String sql = "INSERT INTO usuario (correo,clave) VALUES (?,?)";
        String hashClave = encriptarClave(clave);
        System.out.println(hashClave);
        ps = crearStatementUsurio(sql, correo, clave);
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;
    }
}
