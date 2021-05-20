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
public class Servidor {
	
	int puerto;
	InetAddress myIp;
	ServerSocket ss;
	
	Archivador archivador;
	Mensajero mensajero;
	
	Map<Integer, OyenteCliente> oyentes;
	
	public static void main(String args[]) throws IOException {
		int port = Integer.parseInt(args[0]);
		
		Servidor s = new Servidor(port);
		s.run();
	}
	
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
	
	// Enviar un mensaje a puerto de que se conecte a puertoreceptor
	public void enviarPeerPreparado(int puertoreceptor, InetAddress ip, int puerto, String filename) {
		// TODO Auto-generated method stub
		mensajero.enviarPeerPreparado(puertoreceptor, ip, puerto, filename);
	}
	
	// Registrar al usuario en las tablas y enviarle confirmacion
	public void aceptarConexion(UserInfo origen, List<String> nombresficheros, int puerto) {
		// TODO Auto-generated method stub
		mensajero.guardaUsername(origen.getId(), puerto);
		archivador.almacena(nombresficheros, origen, puerto);
	}

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
	
	// Desregistrar al usuario que envia el mensaje
	public void cerrarConexión(UserInfo origen, int puerto2) {
		// TODO Auto-generated method stub
		oyentes.get(puerto2).acabar();
		oyentes.remove(puerto2);
		archivador.elimina(puerto2);
		mensajero.elimina(origen.getIP());
	}
	
	// Buscar quien tiene ficheroPedido y decirle que origen (en el puerto) se lo esta pidiendo
	public void pedirFichero(String ficheroPedido, UserInfo origen, int puerto) {
		// TODO Auto-generated method stub
		int prop = archivador.buscaPropietario(ficheroPedido);
		mensajero.pideFichero(prop, ficheroPedido, origen, puerto);
	}
	
	

}
