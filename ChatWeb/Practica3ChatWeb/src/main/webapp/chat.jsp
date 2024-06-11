<%
//Recuperamos la sesión del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Buscamos en la sesión el nombre del usuario
String nickname = (String) sesion.getAttribute("nickname");
//Si no existe un nombre de usuario en la sesión pasamos a la ventana de login y finalizamos el chat
if (nickname == null || nickname.isEmpty()) {
	response.sendRedirect("login.jsp");
	return;
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sala de chat: <%=nickname%></title>
    <script src="js/chatValidator.js"></script>
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
        .chat-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 500px;
        }
        .chat-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .chat-container label {
            display: block;
            margin-bottom: 5px;
        }
        .chat-container .input-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 15px;
        }
        .chat-container .input-group input[type="text"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .chat-container .user-control {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
        }
        .chat-container .user-control label {
            margin-right: 10px;
        }
        .chat-container .user-control input[type="text"] {
            flex: 1;
            margin-right: 10px;
        }
        .chat-container .user-control input[type="radio"] {
            margin-left: 10px;
            margin-right: 5px;
        }
        .chat-container .buttons {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .chat-container .buttons button {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .chat-container .send-btn {
            background-color: #4CAF50;
            color: white;
        }
        .chat-container .clear-btn {
            background-color: #f44336;
            color: white;
        }
        .chat-container .logout-link {
            text-align: right;
            margin-bottom: 10px;
        }
        .chat-container iframe {
            width: 100%;
            height: 200px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .chat-container .return-link {
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="chat-container">
 		<form name="formChat" id="formChat" method="POST" onsubmit="return validarFormulario()"
			action="chatSendForm.jsp">
			<h2>Chat Room: <%=nickname%></h2>
        	<div class="logout-link">
            	<a href="logout.jsp">Logout</a>
        	</div>
	        <div class="input-group">
    	        <label for="mensaje">Mensaje:</label>
        	    <input type="text" id="mensaje" name="mensaje">
	        </div>
    	    <div class="user-control">
        	    <label for="usuario">Usuario:</label>
            	<input type="text" id="usuario" name="usuario">
	            <input type="radio" id="bloquear" name="control" value="bloquear" checked>
    	        <label for="bloquear">Bloquear</label>
        	    <input type="radio" id="desbloquear" name="control" value="desbloquear">
            	<label for="desbloquear">Desbloquear</label>
	        </div>
    	    <div class="buttons">
        	    <button class="send-btn" type="submit">Enviar</button>
            	<button class="clear-btn" type="reset" onclick="document.getElementById('mensaje').value = ''; document.getElementById('usuario').value = '';">Borrar</button>
        	</div>
	        <iframe src=messages.jsp></iframe>        
    	    <div class="return-link">
        	    <a href="#">Inicio</a>
        	</div>
		</form>
    </div>
</body>
</html>