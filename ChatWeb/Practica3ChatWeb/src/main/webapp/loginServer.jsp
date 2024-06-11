<!--Importamos la clase del servidor para poder llamar a los métodos-->
<%@ page import="es.ubu.lsi.Practica3ChatWeb.ChatServer"%>
<!--Configuramos el bean en el entorno de la aplicación y la clase del servidor-->
<jsp:useBean id="server" class="es.ubu.lsi.Practica3ChatWeb.ChatServer" scope="application" />
<!--Recuperamos todos los parámetros del bean-->
<jsp:setProperty name="server" property="*" />
<%
//Realizamos el login en el servidor
server.loginUser(request.getParameter("nickname"));
//Recuperamos la sesión del usuario, si no existe devuelve una nueva
HttpSession sesion = request.getSession(true);
//Añadimos en el parámetro nickname el nombre del usuario que inicia sesión
sesion.setAttribute("nickname", request.getParameter("nickname"));
//Saltamos a la página del chat
response.sendRedirect("chat.jsp");
%>