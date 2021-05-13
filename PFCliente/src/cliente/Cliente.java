package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	
	int puerto;
	InetAddress myip;
	InetAddress ipServer;
	Socket s;
	
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public static void main(String args[]) throws IOException {
		InetAddress ipS = null;
		InetAddress ip = null;
		try {
			ipS = InetAddress.getByName(args[0]);
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Error con la IP del servidor");
			e.printStackTrace();
		}
		
		int port = Integer.parseInt(args[1]);
		
		
		Cliente c = new Cliente(ipS, port, ip);
		c.run();
	}
	
	
	public Cliente(InetAddress ipS, int port, InetAddress myIp) throws IOException  {
		puerto = port;
		ipServer = ipS;
		myip = myIp;
		
		s = new Socket(ipServer, puerto);
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
	}
	
	
	public void run() throws IOException {
		Thread os = new OyenteServidor(in, out);
		os.start();
	}
}
