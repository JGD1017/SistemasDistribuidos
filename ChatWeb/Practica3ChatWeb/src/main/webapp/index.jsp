<%
//Recuperamos la sesión del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Buscamos en la sesión el nombre del usuario
String nickname = (String) sesion.getAttribute("nickname");

//Si existe un nombre de usuario en la sesión pasamos a la ventana de chat
if (nickname != null && !nickname.isEmpty()) {
	response.sendRedirect("chat.jsp");
	return;
} else {
	//Si no existe un nombre de usuario en la sesión pasamos a la ventana de login
	response.sendRedirect("login.jsp");
	return;
}
%>