package es.ubu.lsi.Practica3ChatWeb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Clase que hace de servidor del chat
 */
public class ChatServer {
	//Formato para la hora de los mensajes
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	//Usuarios activos en el chat
	private HashSet<String> chatUsers = new HashSet<String>();
	//Mapa de usuarios baneados por cada usuario
	private HashMap<String, ArrayList<String>> bannedUsers = new HashMap<String, ArrayList<String>>();
	//Mapa de historial de mensajes de cada usuario
	private HashMap<String, ArrayList<String>> usersMessages = new HashMap<String, ArrayList<String>>();

	/**
	 * Método para registrar un usuario en el chat, si ya está registrado ignora la petición
	 * Inicializa las listas de usuario
	 * 
	 * @param nickname usuario a registrar
	 */
	public void loginUser(String nickname) {
		//Comprobamos si existe el usuario y si no existe lo añadimos
		if (!chatUsers.contains(nickname)) {
			//Añadimos el usuario
			chatUsers.add(nickname);
			//Creamos su lista de mensajes
			usersMessages.put(nickname, new ArrayList<String>());
			//Creamos su lista de baneados
			bannedUsers.put(nickname, new ArrayList<String>());
			//Avisamos de que el usuario se ha unido al chat
			sendMessagge(nickname, "Se ha unido al chat");
		}
	}
	
	/**
	 * Método para desregistrar un usuario, si no existe ignora la petición
	 * Borra las listas del usuario
	 * 
	 * @param chatUser usuario a desregistrar
	 */
	public void logout(String chatUser) {
		//Si existe el usuario
		if (chatUsers.contains(chatUser)) {
			//Borramos el usuario de la lista de activos
			chatUsers.remove(chatUser);
			//Vaciamos los mensajes
			usersMessages.remove(chatUser);
			//Limpiamos los usuarios baneados
			bannedUsers.remove(chatUser);
			//Avisamos de que el usuario deja el chat
			sendMessagge(chatUser, "Ha abandonado el chat");
		}
	}

	/**
	 * Método para enviar un mensaje a todos los usuarios activos salvo los baneados
	 * que no llegarán al usuario que los haya baneado
	 * 
	 * @param chatUser usuario que envia el mensaje
	 * @param message mensaje que envia el usuario
	 */
	public void sendMessagge(String chatUser, String message) {
		//Para cada usuario activo
		for (String nickname : usersMessages.keySet()) {
			//Si el usuario que envía está baneado por el usuarrio actual no se manda el mensaje
			if (!bannedUsers.get(nickname).contains(chatUser)) {
				//Mandamos el mensaje con la marca de tiempo y remitente
				usersMessages.get(nickname).add(sdf.format(new Date())+" "+chatUser + ": " + message);
			}
		}
	}

	/**
	 * Metodo que devuelve los mensajes almacenados de un usuario activo
	 * 
	 * @param chatUser usuario del que se solicitan los mensajes
	 * @return listado de mensajes en forma de texto
	 */
	public String getMessagesFromNickname(String chatUser) {
		//Inicializamos el mensaje a devolver
		String messagesText = "";
		//Obtenemos los mensajes recibidos por el usuario
		ArrayList<String> messages = usersMessages.get(chatUser);
		//Por cada mensaje añadimos una línea de texto acabada en un cambio de línea HTML
		for (int i = 0; i < messages.size(); i++) {
			messagesText = messagesText + messages.get(i) + ("<br>");
		}
		return messagesText;
	}

	/**
	 * Método para banear o desbanear a un usuario, un usuario baneado no puede enviar mensajes
	 * al usuario que lo ha baneado pero puede leer los que envia el usuario
	 * 
	 * @param chatUser usuario que banea o desbanea
	 * @param userSelected usuario a banear o desbanear
	 * @param ban true si queremos banear o false si queremos desbanear
	 */
	public void banUnban(String chatUser, String userSelected, Boolean ban) {
		//Comprobamos que el usuario que banea/desbanea y el elegido son diferentes
		if(chatUser.equals(userSelected)) {
			sendMessagge(chatUser, "No puede banearse/desbanearse a sí mismo");
		}else {
			//Si queremos banear
			if (ban) {
				//Si no estaba en la lista de baneados del usuario lo añadimos 
				if (!bannedUsers.get(chatUser).contains(userSelected)) {
					bannedUsers.get(chatUser).add(userSelected);
				}
				//Avisamos a los usuarios del ban
				sendMessagge(chatUser, "Ha baneado al usuario: " + userSelected);
			}
			//Si queremos desbanear
			else {
				//Si estaba en la lista de baneados del usuario lo quitamos
				if (bannedUsers.get(chatUser).contains(userSelected)) {
					bannedUsers.get(chatUser).remove(userSelected);
				}
				//Avisamos a los usuarios del desbaneo
				sendMessagge(chatUser, "Ha desbaneado al usuario: " + userSelected);
			}
		}
	}
}