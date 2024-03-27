Para lanzar el chat, desde la carpeta CHAT lanzar en la consola:
  mvn package -P server
Para generar el servidor y:
  mvn package -P client
Para generar el cliente, luego entrar en la carpeta target y lanzar la orden:
  java -jar es.ubu.lsi.server.ChatServerImpl.java
Con eso lanzamos el servidor y se pone a escuchar, luego en otra consola lanzamos un cliente:
  java -jar es.ubu.lsi.client.ChatClientImpl.java [IP/localhost] <username>
Podremos abrir tantas consolas extras como usuarios queramos
