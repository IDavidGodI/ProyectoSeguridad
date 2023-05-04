
package modelo;

import Comunes.PropiedadesConexion;
import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Conexion { 
    private static Conexion sConexion;
    private Properties credenciales;
    Connection conexion =  null;
    private Conexion() throws IOException {
        
        credenciales= new Properties();
        cargarConfiguracion();
        String bd = "servicio_login";
        String usuario = credenciales.getProperty(PropiedadesConexion.PROP_USUARIO);
        String clave = credenciales.getProperty(PropiedadesConexion.PROP_CLAVE);
        String url = String.format(
                "jdbc:sqlserver://localhost:1433;"
                        + "databaseName=%s;"
                        + "user=%s;"
                        + "password=%s;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=20;"
                , bd,usuario,clave
        );
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.out.println(url);
            Logger.getLogger(Conexion.class.getName()).log(Level.INFO, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Connection getConexion(){
        if (sConexion==null) try {
            sConexion = new Conexion();
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sConexion.conexion;
    }
    
    public void setCredenciales(File archivoConf,String correo, String clave) throws IOException{
        
        if (!archivoConf.exists()){
            File carpeta = new File(PropiedadesConexion.DIR_CREDENCIALES);
            carpeta.mkdir();
            archivoConf.createNewFile();
        }

        FileWriter wArchivo = new FileWriter(archivoConf);
        BufferedWriter bArchivo = new BufferedWriter(wArchivo);
        credenciales.put(PropiedadesConexion.PROP_USUARIO, correo);
        credenciales.put(PropiedadesConexion.PROP_CLAVE, clave);
        
        
        credenciales.store(bArchivo,"Credenciales de la conexion a la base de datos de SQL SERVER");
        System.out.println("Archivo de credenciales creado");
    }
    
    public void setCredenciales(File archivoConf) throws IOException{
        
        String usuario = JOptionPane.showInputDialog("Nombre de usuario:");
        System.out.println("USUARIO: "+usuario);
        String clave; 
        JPasswordField campoClave = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(null, campoClave, "Ingresar contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (ok == JOptionPane.OK_OPTION) {
            clave = new String(campoClave.getPassword());
            try {
                setCredenciales(archivoConf, usuario, clave);
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        throw new IOException();
    }
    
    private void cargarConfiguracion() throws IOException{
        File f = new File(PropiedadesConexion.RUTA_CREDENCIALES);
        if (!f.exists()) setCredenciales(f);
        System.out.println("Archivo de credenciales SQL Server: "+f.getAbsolutePath());
        FileInputStream is = new FileInputStream(f);
        
        credenciales.load(is);
        if (credenciales.get(PropiedadesConexion.PROP_USUARIO) == null || ((String)credenciales.get(PropiedadesConexion.PROP_USUARIO)).isEmpty() ||
            credenciales.get(PropiedadesConexion.PROP_CLAVE) == null || ((String)credenciales.get(PropiedadesConexion.PROP_CLAVE)).isEmpty())
        {
            setCredenciales(f);
            cargarConfiguracion();
        }
    }
    
    
}
