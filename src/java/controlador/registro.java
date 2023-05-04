package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Comunes.Formularios;
import Comunes.PropiedadesEnvios;
import Comunes.expresionesRegulares;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.UsuarioDAO;


@WebServlet(name = "SvRegistro", urlPatterns = {"/registro"})
public class registro extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.sendRedirect("registro.jsp");
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UsuarioDAO uDAO = new UsuarioDAO();
            String correo = request.getParameter("correo");
            String clave = request.getParameter("clave");
            String confirm_clave = request.getParameter("confirm_clave");
            boolean registroValido = true;
            ArrayList<String> errores = new ArrayList<>();
            
            if (!correo.matches(expresionesRegulares.REGEX_CORREO)){
                request.setAttribute(Formularios.ESTADO_CORREO, "error");
                errores.add("El correo ingresado no es válido");
                registroValido = false;
            }
            else if (uDAO.correoDuplicado(correo)){
                request.setAttribute(Formularios.ESTADO_CORREO, "error");
                errores.add("La direccion de correo ya se encuentra en uso.");
                registroValido = false;
            }
            if (!clave.matches(expresionesRegulares.REGEX_CLAVE)){
                request.setAttribute(Formularios.ESTADO_CLAVE, "error");
                errores.add("Ingrese una contraseña más segura (al menos una mayúscula, una minúscula, al menos un caracter especial y un caracter numérico)");
                registroValido = false;
            }
            if (!clave.equals(confirm_clave) || confirm_clave.isEmpty()){
                request.setAttribute(Formularios.ESTADO_CONFIRM_CLAVE, "error");
                errores.add("Las contraseñas no coinciden");
                registroValido=false;
            }
            
            if (registroValido) {
                request.setAttribute(Formularios.ESTADO_CORREO, "");
                request.setAttribute(Formularios.ESTADO_CLAVE, "");
                request.setAttribute(Formularios.ESTADO_CONFIRM_CLAVE, "");

                request.setAttribute("destinatario", correo);
                request.setAttribute(Formularios.CORREO_ENVIADO, correo);
                request.setAttribute(Formularios.CLAVE_ENVIADA, clave);
                request.setAttribute(PropiedadesEnvios.USUARIO_REGISTRADO, false);
                request.getRequestDispatcher("envioCorreos").forward(request, response);
                return;
            }
            request.setAttribute(Formularios.CORREO_ENVIADO, correo);
            request.setAttribute(Formularios.CLAVE_ENVIADA, clave);
            request.setAttribute(Formularios.LISTA_ERRORES, errores);
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(registro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
