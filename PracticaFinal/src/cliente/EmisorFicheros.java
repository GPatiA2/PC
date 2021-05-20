package cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class EmisorFicheros extends Thread {
	
	String nombref;
	int puerto;

	String id;
	
	public EmisorFicheros(String nombreFichero, int puertoEmision, String id) {
		nombref =  nombreFichero;
		puerto = puertoEmision;
		this.id = id;
	}

	public void run() {
		
		File f = new File("" + id + File.separator + nombref);
		byte[] contenido = null;
		try {
			contenido = Files.readAllBytes(f.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha podido leer el fichero de " + id + File.separator + nombref);
			e.printStackTrace();
		}
		
		try (ServerSocket ss = new ServerSocket(puerto); Socket s =  ss.accept();) {
			ObjectOutputStream toPeer = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fromPeer = new ObjectInputStream(s.getInputStream());
			toPeer.writeObject(contenido);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al crear el server-socket en el puerto " + puerto);
			e.printStackTrace();
		}		
		
	}
	
}
