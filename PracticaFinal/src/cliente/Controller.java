package cliente;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cliente.gui.ObserverCliente;

public class Controller {
	
	Cliente c;
	
	public Controller(Cliente c) {
		this.c = c;
	}
	
	public void addObserver(ObserverCliente o) {
		c.addObserver(o);
	}
	
	public void removeObserver(ObserverCliente o) {
		c.removeObserver(o);
	}
	
	public void enviarMensajeConexion(String ip) {
		try {
			c.enviarMensajeConexion(InetAddress.getByName(ip));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("La IP introducida no es correcta");
			e.printStackTrace();
		}
	}

	public void pedirTabla() {
		// TODO Auto-generated method stub
		c.pedirTabla();
	}

	public void pedirFichero(String s) {
		// TODO Auto-generated method stub
		c.pedirFichero(s);
	}

	public void desconectar() {
		// TODO Auto-generated method stub
		c.cerrarConexion();
	}
	
}
