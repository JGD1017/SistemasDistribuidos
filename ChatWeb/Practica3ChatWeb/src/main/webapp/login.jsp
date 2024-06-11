<%
//Recuperamos la sesión del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Buscamos en la sesión el nombre del usuario
String nickname = (String) sesion.getAttribute("nickname");
//Si existe un nombre de usuario en la sesión pasamos a la ventana de chat y finalizamos el login
if (nickname != null && !nickname.isEmpty()) {
	response.sendRedirect("chat.jsp");
	return;
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pantalla de login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }
        .login-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .login-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .login-container label {
            display: block;
            margin-bottom: 5px;
        }
        .login-container .input-group {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }
        .login-container .input-group label {
            margin-right: 10px;
        }
        .login-container input[type="text"] {
            flex: 1;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .login-container .button-container {
            display: flex;
            justify-content: flex-end;
        }
        .login-container button {
            padding: 10px 15px;
            margin-left: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .login-container .login-btn {
            background-color: #4CAF50;
            color: white;
        }
        .login-container .clear-btn {
            background-color: #f44336;
            color: white;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <form action="loginServer.jsp" method="POST">
        	<h2>Login</h2>
        	<div class="input-group">
            	<label for="nickname">Nickname:</label>
            	<input type="text" id="nickname" name="nickname" required>
        	</div>
        	<div class="button-container">
    	        <button class="login-btn" type="submit">Enviar</button>
            	<button class="clear-btn" onclick="document.getElementById('nickname').value = '';">Borrar</button>
        	</div>
       	</form>
    </div>
</body>
</html>