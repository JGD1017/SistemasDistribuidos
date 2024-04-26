set CLASSPATH=.\target\Practica2ChatRMI-0.0.1-SNAPSHOT.jar

java -Djava.security.policy=registerit.policy ^
-Djava.rmi.server.codebase=http://localhost:8080/Practica2ChatRMI-Web/ ^
es.ubu.lsi.server.ChatServerDynamic