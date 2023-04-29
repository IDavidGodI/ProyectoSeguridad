/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Comunes.Formularios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    HttpSession sesion = request.getSession();
    CodigoVerificacion codigoVerif = (CodigoVerificacion) sesion.getAttribute("limiteCodVerif");
    ArrayList<String> errores = new ArrayList<>();
    if (codigoVerif != null) {
        
        if (sesion.getAttribute("limiteCodVerif") != null) {
            
            String codigoIngresado = request.getParameter("codigoVerificacion");
            String codigoEnviado = (String) sesion.getAttribute("codigoVerificacion");
            System.out.println("VERIFICANDO CODIGO");
            if (codigoIngresado!=null)System.out.println("el codigo ingresado es valido: "+codigoIngresado.matches(regexCodigo));
            if (!codigoIngresado.equals(codigoEnviado)) {
                errores.add("El codigo de verificacion no es correcto, intente de nuevo");
                
                request.setAttribute(Formularios.LISTA_ERRORES, errores);
                request.getRequestDispatcher("FactorAutenticacion.jsp").forward(request, response);
                System.out.println("Se vino adentro");
                return;
            }
            sesion.removeAttribute("codigoVerificacion"); 
            sesion.removeAttribute("limiteCodVerif"); 
                
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
