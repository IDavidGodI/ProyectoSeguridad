/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import Comunes.Formularios;
import Comunes.PropiedadesEnvios;
import Comunes.expresionesRegulares;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.UsuarioDAO;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "InicioSesion", urlPatterns = {"/InicioSesion"})
public class InicioSesion extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.sendRedirect("InicioSesion.jsp");
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            UsuarioDAO uDAO = new UsuarioDAO();
            String correo = request.getParameter("correo");
            String clave = request.getParameter("clave");
            boolean registroValido = true;
            ArrayList<String> errores = new ArrayList<>();
            
           if (!correo.matches(expresionesRegulares.REGEX_CORREO)){
               request.setAttribute(Formularios.ESTADO_CORREO, "error");
               registroValido = false;
           }
           if (!clave.matches(expresionesRegulares.REGEX_CLAVE)){
               request.setAttribute(Formularios.ESTADO_CLAVE, "error");
               registroValido = false;
           }
            
            if (registroValido) {
                    request.setAttribute(Formularios.ESTADO_CORREO, "");
                    request.setAttribute(Formularios.ESTADO_CLAVE, "");
                    
                    request.setAttribute("destinatario", correo);
                    request.setAttribute(Formularios.CORREO_ENVIADO, correo);
                    request.setAttribute(Formularios.CLAVE_ENVIADA, clave);
                try {
                    if (uDAO.validarUsuario(correo, clave)){
                        if (AutenticacionUsuarios.validarDobleFactor(request, response)){
                            AutenticacionUsuarios.crearSesion(request, response);
                            response.sendRedirect("index.jsp");
                            return;
                        } 
                        request.setAttribute(PropiedadesEnvios.USUARIO_REGISTRADO, true);
                        request.getRequestDispatcher("envioCorreos").forward(request, response);
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            errores.add("El correo o la contraseña ingresados no son validos, intente de nuevo");
            request.setAttribute(Formularios.CORREO_ENVIADO, correo);
            request.setAttribute(Formularios.CLAVE_ENVIADA, clave);
            request.setAttribute(Formularios.LISTA_ERRORES, errores);
            request.getRequestDispatcher("InicioSesion.jsp").forward(request,response);
           
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
