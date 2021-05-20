package cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ReceptorFicheros extends Thread {
	
	InetAddress ipemisor;
	String ficheroRecibido;
	int portEmisor;
	String miId;
	
	public ReceptorFicheros(InetAddress ipemisor,int portEmisor, String ficheroRecibido, String id) {
		this.ipemisor = ipemisor;
		this.portEmisor = portEmisor;
		this.ficheroRecibido = ficheroRecibido;
		miId = id;
	}
	
	public void run() {
		File f = new File(miId + File.separator + ficheroRecibido);
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Aqui casca
		try(Socket s = new Socket(ipemisor,portEmisor);
				FileOutputStream fout = new FileOutputStream(f, false)) {
			
			System.out.println("Socket de receptor creado");
			
			ObjectOutputStream toPeer = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fromPeer = new ObjectInputStream(s.getInputStream());
			
			System.out.println("Canales extraidos");
			
			byte[] cont = (byte[]) fromPeer.readObject();
			
			System.out.println("Bytes leidos");
			
			fout.write(cont);
			
			System.out.println("Fichero escrito");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
