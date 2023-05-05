<%-- 
    Document   : index
    Created on : 26 abr 2023, 21:19:36
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "controlador.AutenticacionUsuarios"%>
<!DOCTYPE html>
<%
    if (!AutenticacionUsuarios.validarSesion(request, response)) response.sendRedirect("InicioSesion.jsp");
    AutenticacionUsuarios.crearSesion(request, response);
%>
<html>
    <head>
        <title>INICIO</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/BotonFormulario.css" type = "text/css">
    </head>
    <body>
        <div>
            <h1>BIENVENIDO</h1>
            <form method="post" action="cerrarSesion" >
                <input type="submit" value="Cerrar Sesion">
            </form>
        </div>
    </body>
</html>
