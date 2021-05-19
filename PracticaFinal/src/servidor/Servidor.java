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

public class Servidor {
	
	int puerto;
	InetAddress myIp;
	ServerSocket ss;
	
	Archivador archivador;
	
	Mensajero mensajero;
	
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
		mensajero = new Mensajero();
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
				OyenteCliente oc = new OyenteCliente(this, serverInput, serverOutput);
				mensajero.registrar(s.getInetAddress(), serverOutput);
				
				Thread t = new Thread() {
					public void run() {
						oc.run();
					}
				};
				
				t.run();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Enviar un mensaje a idreceptor de que la ip le esta esperando en el puerto
	public void enviarPeerPreparado(String idreceptor, InetAddress ip, int puerto) {
		// TODO Auto-generated method stub
	
	}
	
	// Registrar al usuario en las tablas y enviarle confirmacion
	public void aceptarConexion(UserInfo origen, List<String> nombresficheros) {
		// TODO Auto-generated method stub
		mensajero.guardaUsername(origen.getId(), origen.getIP());
		archivador.almacena(nombresficheros, origen);
	}

	// Enviarle la lista de usuarios al solicitante
	public void enviarListaUsuarios(UserInfo origen) {
		// TODO Auto-generated method stub
		List<FileInfo> l = archivador.recuperaLista();
		try {
			mensajero.enviaListaFicheros(l, origen);			
		}
		catch (Exception e){
			System.out.println("Error al enviar la lista de ficheros");
			e.printStackTrace();
		}
	}
	
	// Desregistrar al usuario que envia el mensaje
	public void cerrarConexión(UserInfo origen) {
		// TODO Auto-generated method stub
		
	}
	
	// Buscar quien tiene ficheroPedido y decirle que origen se lo esta pidiendo
	public void pedirFichero(String ficheroPedido, UserInfo origen) {
		// TODO Auto-generated method stub
		
	}
	
	

}
