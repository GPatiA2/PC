package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import coms.FileInfo;
import coms.UserInfo;
import coms.oyentes.OyenteCliente;

// en bin -> java -cp . servidor.Servidor <puerto>
/**
 * Clase que implementa el servidor de la aplicacion
 * @author Guille
 */
public class Servidor {
	/**
	 * Puerto en el que escucha el servidor
	 */
	int puerto;
	/**
	 * Ip del servidor
	 */
	InetAddress myIp;
	/**
	 * ServerSocket por el que escucha conexiones
	 */
	ServerSocket ss;
	
	/**
	 * Encargado de almacenar la informacion de los ficheros en el sistema
	 */
	Archivador archivador;
	/**
	 * Encargado de enviar mensajes a los diferentes clientes conectados
	 */
	Mensajero mensajero;
	/**
	 * Almacenar los oyentes a cada cliente conectados a un puerto
	 */
	Map<Integer, OyenteCliente> oyentes;
	
	public static void main(String args[]) throws IOException {
		int port = Integer.parseInt(args[0]);
		
		Servidor s = new Servidor(port);
		s.run();
	}
	
	/**
	 * Constructor. Recibe un puerto por el que escuchar y atender conexiones
	 * @param puerto Entero que representa el puerto por el que atender nuevas conexiones
	 */
	public Servidor(int puerto) {
		this.puerto = puerto;
		try {
			myIp = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error al encontrar mi ip");
			e1.printStackTrace();
		}
		try {
			ss = new ServerSocket(puerto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al montar el serversocket");
			e.printStackTrace();
		}
		
		oyentes = new HashMap<Integer,OyenteCliente>();
		mensajero = new Mensajero(myIp);
		archivador = new Archivador();
		System.out.println("IP = " + myIp.toString() + " puerto = " + puerto);
	}	
	
	/**
	 * Metodo que atiende nuevas conexiones.
	 * Genera un socket por cada conexion nueva, extrae los canales del socket y crea un proceso para atender
	 * peticiones de esa conexion.
	 * Indica al mensajero que guarde una asociacion del puerto por el que se realiza la conexion con el canal de salida al cliente conectado
	 * Guarda también una asociacion en el mapa oyentes entre el puerto y el oyente que atiende al cliente de ese puerto
	 */
	public void run() {
		while(true) {
			try {
				System.out.println("Esperando conexion por serversocket");
				Socket s = ss.accept();
				ObjectInputStream serverInput = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream serverOutput = new ObjectOutputStream(s.getOutputStream());
				
				InetAddress ipconectada = s.getInetAddress();
				int puerto = s.getPort();

				OyenteCliente oc = new OyenteCliente(this, serverInput, serverOutput, puerto);
				
				System.out.println("Se acaba de conectar " + s.getInetAddress());
				oyentes.put(puerto, oc);
				mensajero.registrar(puerto, serverOutput);
				
				oc.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Enviar un mensaje al cliente que ha realizado la peticion de un fichero con los datos para conectarse al cliente que lo emite
	 * @param puertoreceptor Puerto por el que el cliente emisor escucha nuevas conexiones
	 * @param ip La IP del cliente que emite el fichero
	 * @param puerto Puerto por el que el cliente que recibe el fichero esta conectado al servidor
	 * @param filename Nombre del fichero que se va a enviar del cliente emisor al receptor
	 */
	public void enviarPeerPreparado(int puertoreceptor, InetAddress ip, int puerto, String filename) {
		// TODO Auto-generated method stub
		mensajero.enviarPeerPreparado(puertoreceptor, ip, puerto, filename);
	}
	
	/**
	 * Acepta la conexión de un cliente y almacena su informacion (lista de ficheros que tiene, ip y nombre, y el puerto en el que esta conectado)
	 * @param origen IP e ID del cliente que se conecta al servidor
	 * @param nombresficheros Nombres de los ficheros que tiene el cliente que se conecta
	 * @param puerto Puerto por el que se realiza la conexion
	 */
	// Registrar al usuario en las tablas y enviarle confirmacion
	public void aceptarConexion(UserInfo origen, List<String> nombresficheros, int puerto) {
		// TODO Auto-generated method stub
		mensajero.guardaUsername(origen.getId(), puerto);
		archivador.almacena(nombresficheros, origen, puerto);
	}

	/**
	 * Envia la lista de ficheros y los usuarios que los tienen al cliente que lo solicita
	 * @param origen ID e IP del usuario que pide la lista
	 * @param puerto Puerto por el que se comunica el cliente que realiza la peticion
	 */
	// Enviarle la lista de usuarios al solicitante
	public void enviarListaUsuarios(UserInfo origen, int puerto) {
		// TODO Auto-generated method stub
		List<FileInfo> l = archivador.recuperaLista();
		try {
			mensajero.enviaListaFicheros(l, origen, puerto);			
		}
		catch (Exception e){
			System.out.println("Error al enviar la lista de ficheros");
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina los datos del usuario que quiere cerrar la conexion del sistema
	 * @param origen IP e ID del cliente que quiere cerrar la conexion
	 * @param puerto2 Puerto por el que se comunica el cliente que pide cerrar la conexion
	 */
	// Desregistrar al usuario que envia el mensaje
	public void cerrarConexión(UserInfo origen, int puerto2) {
		// TODO Auto-generated method stub
		oyentes.get(puerto2).acabar();
		oyentes.remove(puerto2);
		archivador.elimina(puerto2);
		mensajero.elimina(puerto2);
	}
	
	/**
	 * Envia un mensaje a uno de los clientes que tienen el fichero pedido diciendole que el usuario que se comunica a traves del puerto que se pasa como parametro esta solicitando el fichero indicado
	 * @param ficheroPedido Nombre del fichero que uno de los clientes pide
	 * @param origen ID e IP del usuario que pide el fichero
	 * @param puerto Puerto por el que el usuario pide el fichero realiza la peticion
	 */
	// Buscar quien tiene ficheroPedido y decirle que origen (en el puerto) se lo esta pidiendo
	public void pedirFichero(String ficheroPedido, UserInfo origen, int puerto) {
		// TODO Auto-generated method stub
		int prop = archivador.buscaPropietario(ficheroPedido);
		mensajero.pideFichero(prop, ficheroPedido, origen, puerto);
	}
	
	

}
