package cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.List;

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
			System.out.println("Contenido del ficheroe es");
			List<String> l = Files.readAllLines(f.toPath());
			for(String s : l) {
				System.out.println(s);
			}
			contenido = Files.readAllBytes(f.toPath());
			System.out.println("Fichero pedido cargado en memoria");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha podido leer el fichero de " + id + File.separator + nombref);
			e.printStackTrace();
		}
		
		try (ServerSocket ss = new ServerSocket(puerto); Socket s =  ss.accept();) {
			System.out.println("ServerSocket creado y conexion aceptada");
			ObjectOutputStream toPeer = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fromPeer = new ObjectInputStream(s.getInputStream());
			System.out.println("Canales obtenidos");
			
			toPeer.writeObject(contenido);
			System.out.println("Fichero enviado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al crear el server-socket en el puerto " + puerto);
			e.printStackTrace();
		}		
		
	}
	
}
