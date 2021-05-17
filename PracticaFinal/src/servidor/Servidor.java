package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import coms.UserInfo;
import coms.oyentes.OyenteCliente;

public class Servidor {
	
	int puerto;
	InetAddress myIp;
	ServerSocket ss;
	
	List<OyenteCliente> oyentes;
	
	
	public static void main(String args[]) throws IOException {
		InetAddress myIp = InetAddress.getLocalHost();
		int port = Integer.parseInt(args[0]);
		
		ServerSocket ss = new ServerSocket(port);
		
		Servidor s = new Servidor(port, myIp, ss);
		s.run();
	}
	
	public Servidor(int puerto, InetAddress ip, ServerSocket serversocket) {
		this.puerto = puerto;
		myIp = ip;
		ss = serversocket;
	}	
	
	public void run() {
		while(true) {
			try {
				Socket s = ss.accept();
				ObjectInputStream serverInput = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream serverOutput = new ObjectOutputStream(s.getOutputStream());
				OyenteCliente oc = new OyenteCliente(this, serverInput, serverOutput);
				
				Thread t = new Thread() {
					public void run(OyenteCliente o) {
						o.run();
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
	public void aceptarConexion(UserInfo origen) {
		// TODO Auto-generated method stub
		
	}

	// Enviarle la lista de usuarios al solicitante
	public void enviarListaUsuarios(UserInfo origen) {
		// TODO Auto-generated method stub
		
	}
	
	// Desregistrar al usuario que envia el mensaje
	public void cerrarConexión(UserInfo origen) {
		// TODO Auto-generated method stub
		
	}
	
	// Indicarle al usuario que el origen ha pedido el fichero ficheroPedido
	public void pedirFichero(String usuario, String ficheroPedido, UserInfo origen) {
		// TODO Auto-generated method stub
		
	}
	
	

}
