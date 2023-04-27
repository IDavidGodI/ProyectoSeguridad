<%-- 
    Document   : InicioSesion
    Created on : 25 abr 2023, 16:14:51
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="formulario.css">
</head>
<body>
    <div class="container">
        <h2>Iniciar Sesión</h2>
        <form method="post" action="SvInicioSesion" >
                <label for="username">Dirección de correo:</label>
                <input type="text" name="correo" id="correo"><br>
                <label for="clave">Contraseña:</label>
                <input type="password" name="clave" id="clave"><br><br>
                <input type="submit" value="Iniciar Sesión">
        </form>
    </div>
</body>
</html>

