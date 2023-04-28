<%-- 
    Document   : registro
    Created on : 25 abr 2023, 16:48:25
    Author     : Lenovo
--%>
<%@page import = "java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registrarse</title>
    <link rel="stylesheet" href="formulario.css">
</head>
<body>
    <div class="container">
        
        <%
            
            String estadoCorreo = (String) request.getSession().getAttribute("estadoCorreo");
            String estadoClave = (String) request.getSession().getAttribute("estadoCorreo");
            String estadoConfirmClave = (String) request.getSession().getAttribute("estadoCorreo");
            List<String> errores = (List) request.getSession().getAttribute("ListaErrores");
        %>
            
        <h2>Registrarse</h2>
        <form method="post" action="SvRegistro">
                <label for="correo">Dirección de correo:</label>
                <input type="text" name="correo" id="correo" estado=<%=estadoCorreo%>><br>
                <label for="password">Contraseña:</label>
                <input type="password" name="clave" id="clave" estado=<%=estadoClave%>><br>
                <label for="confirm_password">Confirmar Contraseña:</label>
                <input type="password" name="confirm_clave" id="confirm_clave" estado=<%=estadoConfirmClave%>><br><br>
                <input type="submit" value="Registrarse">
        </form>
                <ul class="ListaError">
                    <%
                        if (errores!=null)
                        for (String error : errores){
                    %>
                        <li>
                            <p class="TextoError"><%=error%></p>
                        </li>
                    <%}%>
                </ul>
    </div>
                
        <%
            
        %>
</body>
</html>

