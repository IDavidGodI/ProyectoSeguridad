<%-- 
    Document   : registro
    Created on : 25 abr 2023, 16:48:25
    Author     : Lenovo
--%>

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
            String estadoCorreo =(String) request.getSession().getAttribute("estadoCorreo");
        %>
        <h2>Registrarse</h2>
        <form method="post" action="SvRegistro">
                <label for="correo">Dirección de correo:</label>
                <input type="text" name="correo" id="correo" estado=<%=estadoCorreo%>><br>
                <label for="password">Contraseña:</label>
                <input type="password" name="clave" id="clave"><br>
                <label for="confirm_password">Confirmar Contraseña:</label>
                <input type="password" name="confirm_clave" id="confirm_clave"><br><br>
                <input type="submit" value="Registrarse">
        </form>
    </div>
                
        <%
            
        %>
</body>
</html>

