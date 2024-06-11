<!--Importamos la clase del servidor para poder llamar a los métodos-->
<%@ page import="es.ubu.lsi.Practica3ChatWeb.ChatServer"%>
<!--Configuramos el bean en el entorno de la aplicación y la clase del servidor-->
<jsp:useBean id="server" class="es.ubu.lsi.Practica3ChatWeb.ChatServer" scope="application" />
<!--Recuperamos todos los parámetros del bean-->
<jsp:setProperty name="server" property="*" />

<%
//Recuperamos la sesión del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Buscamos en la sesión el nombre del usuario
String nickname = (String) sesion.getAttribute("nickname");

//Hacemos logout en el servidor con el nombre del usuario de la sesion
server.logout(nickname);
//Borramos el atributo del nombre del usuario de la sesion para evitar errores en recarga de la página
sesion.removeAttribute("nickname");
//Invalidamos la sesión como última medida del proceso de logout
sesion.invalidate();

//Volvemos a la página de login de usuario
response.sendRedirect("login.jsp");
%>