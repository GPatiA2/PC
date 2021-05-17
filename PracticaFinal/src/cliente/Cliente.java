package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import coms.UserInfo;
import coms.oyentes.OyenteServidor;

public class Cliente {
	
	InetAddress serverip;
	int serverPort;

	InetAddress myIP;
	String id;
	
	ObjectOutputStream toServer;
	
	public static void main(String args[]) throws IOException {
		
		InetAddress ipserver = InetAddress.getByName(args[1]);
		InetAddress ipthis = InetAddress.getLocalHost();
		int ps = Integer.parseInt(args[2]);
		String id = args[0];
		
		Socket s = new Socket(ipserver, ps);
		
		ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());
		ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
		
		Cliente c = new Cliente(ipserver, ps, id, ipthis);
		
		OyenteServidor os = new OyenteServidor(c, fromServer, toServer);
		Thread t = new Thread() {
			public void run(OyenteServidor o) {
				o.run();
			}
		};
		
		MainWindow mw = new MainWindow(c);
		
		t.start();
	}
	
	
	public Cliente(InetAddress sip, int sport, String id, InetAddress myIp) {
		serverip = sip;
		serverPort = sport;
		this.id = id;
		this.myIP = myIp;
	}
	
	// Mostrar confirmación de conexion
	public void confirmarConexion(UserInfo origen) {
		// TODO Auto-generated method stub
		
	}
	
	// Crear un proceso para empezar a emitir el fichero de nombrefichero 
	public void emitirFichero(String nombreFichero, UserInfo solicitante) {
		// TODO Auto-generated method stub
		
	}
	
	// Crear un proceso para recibir el archivo que está enviando el cliente con la ip y el puerto
	public void conectarConClienteEmisor(InetAddress ip, int puerto) {
		// TODO Auto-generated method stub
		
	}
	
	// Imprimir adios y salir
	public void cerrarConexion() {
		// TODO Auto-generated method stub
		
	}

}
