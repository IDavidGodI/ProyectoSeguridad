/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import Comunes.Formularios;
import Comunes.PropiedadesEnvios;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.UsuarioDAO;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "FactorAutenticacion", urlPatterns = {"/FactorAutenticacion"})
public class FactorAutenticacion extends HttpServlet {
    String regexCodigo= "^[0-9]{6}$";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("registro.jsp");
    }
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UsuarioDAO uDAO = new UsuarioDAO(); 
    HttpSession sesion = request.getSession();
    ArrayList<String> errores = new ArrayList<>();
    Object objRegistrado = sesion.getAttribute(PropiedadesEnvios.USUARIO_REGISTRADO);
    if (objRegistrado==null){
        sesion.invalidate();
        return;
    }
    boolean estaRegistrado = (boolean) objRegistrado;
    if (!CodigoVerificacion.codigoExpirado(request)) {
        String codigoIngresado = request.getParameter(PropiedadesEnvios.COD_VERIFICACION);
        String codigoEnviado = (String) sesion.getAttribute(PropiedadesEnvios.COD_VERIFICACION);
        if (codigoIngresado!=null)System.out.println("el codigo ingresado es valido: "+codigoIngresado.matches(regexCodigo));
        if (!codigoIngresado.equals(codigoEnviado)) {
            errores.add("El codigo de verificacion no es correcto, intente de nuevo");

            request.setAttribute(Formularios.LISTA_ERRORES, errores);
            request.getRequestDispatcher("FactorAutenticacion.jsp").forward(request, response);
            return;
        }
        sesion.removeAttribute(PropiedadesEnvios.COD_VERIFICACION); 
        sesion.removeAttribute(PropiedadesEnvios.COD_VERIFICACION); 
        String correo =  (String) sesion.getAttribute(Formularios.CORREO_ENVIADO);
        String clave = (String)sesion.getAttribute(Formularios.CLAVE_ENVIADA);
        if (estaRegistrado){
            response.sendRedirect("index.jsp");
            return;
        }
        if (uDAO.registrarUsuario(correo, clave)){
            response.sendRedirect("index.jsp");
            return;
        }
            
    }
    errores.add("El código de verificación ha expirado");
    request.setAttribute(Formularios.LISTA_ERRORES, errores);
    request.getRequestDispatcher("FactorAutenticacion.jsp").forward(request, response);
}

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
