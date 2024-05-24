# SistemasDistribuidos
Prácticas de Sistemas Distribuidos de la UBU de José Antonio Gutiérrez Delgado

Carpeta Tomcat y Glassfish
Añadidas capturas de funcionamiento de Tomcat y Glassfish

Carpeta ANTyMaven/ANTHelloWorld
Añadido proyecto HelloWorld con build.xml para ANT

Carpeta MavenHelloWorld/HelloWorldQuickStart
Añadido proyecto HelloWorld generado para Maven por comandos

Carpeta MavenHelloWorld/HelloWorldEclipseIntegrado
Añadido proyecto HelloWorld generado para Maven por integración en Eclipse

Carpeta MavenHelloWorld/HelloWorldClassic
Añadido proyecto HelloWorld generado en Eclipse y convertido a tipo Maven

Carpeta Socket Multihilo Blacklist
Añadido proyectos servidor clientes multihilo con blacklist de los puertos 55000 a 57000

Carpeta RMI-HolaMundo
Añadida modificación del holamundo con 3 objetos nuevos: el servidor dice hola, dice la hora, dice la fecha y dice adios

Carpeta CHAT
Añadido chat con sockets y baneo

Carpeta Practica2CharRMI
Añadido chat RMI con baneo de usuarios
Primero hacer un mvn package en el proyecto Practica2ChatRMI para que se genere el jar y se copien los ficheros al otro proyecto 
Después hacer un mvn package en el proyecto Practica2ChatRMI-Web
Lanzar glassfish
Copiar el war generado en la carpeta autodeploy de glassfish con el nombre Practica2CharRMI-Web.war
Lanzar en una consola registro.bat
Lanzar en otra consola servidor.bat
Lanzar por cada cliente que se desee una consola lanzando cliente.bat <nicname>

Carpeta Formulario/formulario
Añadido formularios html con validación y llamada a servlet eco
Hacer un mvn package en el proyecto formulario
Lanzar glassfish
Copiar el war generado en la carpeta autodeploy de glassfish con el nombre formulario.war
Abrir http://localhost:8080/formulario/formulario.html para el formulario con JS embebido
Abrir http://localhost:8080/formulario/formulario%20externo.html para el formulario con llamada a JS externo
Abrir http://localhost:8080/formulario/formulario%20builtin.html para el formulario con validación HTML5
