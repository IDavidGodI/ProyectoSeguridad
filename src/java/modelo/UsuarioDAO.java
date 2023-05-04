package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class UsuarioDAO {
    Connection con;
    PreparedStatement ps;
    public UsuarioDAO(){
        con = Conexion.getConexion();
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
        System.out.println(auxps.toString());
        return auxps;
    }
    public boolean correoDuplicado(String correo) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE correo=?";
        ps = con.prepareStatement(sql);
        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public boolean validarUsuario(String correo, String clave) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE correo=? AND clave=?";
        ps = crearStatementUsurio(sql, correo, clave);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    public boolean registrarUsuario(String correo, String clave){
        int filasAfectadas = 0;
        String sql = "INSERT INTO usuario (correo,clave) VALUES (?,?)";
        String hashClave = encriptarClave(clave);
        System.out.println(hashClave);
        try {
            ps = crearStatementUsurio(sql, correo, clave);
            filasAfectadas = ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filasAfectadas > 0;
    }
}
