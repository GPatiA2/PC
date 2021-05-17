package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket ss;
	int puerto;
	InetAddress ip;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int puerto = Integer.parseInt(args[0]);
		Server S;
		try {
			S = new Server(puerto);
			System.out.println(S.ip);
			S.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en el puerto");
			e.printStackTrace();
		}
	}
	
	public Server(int port) throws IOException {
		puerto = port;
		ss = new ServerSocket(puerto);
		ip = InetAddress.getLocalHost();
	}
	
	public void run() throws IOException {
		while(true) {
			Socket s = ss.accept();
			ObjectOutputStream serverOutput = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream serverInput = new ObjectInputStream(s.getInputStream());
			Thread c = new OyenteCliente(serverInput, serverOutput);
			
			c.start();
		}
	}

}
