package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coms.FileInfo;
import coms.UserInfo;
import coms.mensajes.MensajeConfirmacionListaUsuarios;
import coms.mensajes.MensajeEmitirFichero;
import coms.mensajes.MensajePreparadoServidorCliente;
import coms.oyentes.OyenteCliente;

public class Mensajero {
	
	Map<InetAddress, ObjectOutputStream> canales;
	Map<String, InetAddress> usuarios;
	InetAddress myIp;
	
	RWSemaforos m;
	
	public Mensajero(InetAddress myIp) {
		canales = new HashMap<InetAddress, ObjectOutputStream>();
		usuarios = new HashMap<String, InetAddress>();
		this.myIp = myIp;
		m = new RWSemaforos();
	}

	public void registrar(InetAddress inetAddress, ObjectOutputStream oc) {
		m.requestRead();
		canales.put(inetAddress, oc);
		System.out.println("Acabo de registrar a " + inetAddress.toString());
		m.releaseRead();
	}

	public void guardaUsername(String id, InetAddress ip) {
		m.requestWrite();
		usuarios.put(id, ip);
		System.out.println(usuarios.size());
		m.releaseWrite();
	}

	public void enviaListaFicheros(List<FileInfo> l, UserInfo origen)  {
		m.requestRead();
		System.out.println("Vengo buscando a " + origen.getIP().toString());
		ObjectOutputStream toCliente = canales.get(origen.getIP());
		m.releaseRead();
		
		try {
			toCliente.writeObject(new MensajeConfirmacionListaUsuarios("server", myIp, origen.getId(), origen.getIP(), l));
		} catch (IOException e) {

			System.out.println("No se ha podido enviar la lista de ficheros a " + origen.getIP());
			e.printStackTrace();
		}
	}

	public void pideFichero(InetAddress prop, String ficheroPedido, UserInfo origen) {
		m.requestRead();
		System.out.println("Voy a pedirle " + ficheroPedido + " a " + prop.toString() + " de parte de " + origen.getId() + " " + origen.getIP());
		ObjectOutputStream toCliente = canales.get(prop);
		m.releaseRead();
		
		try {
			toCliente.writeObject(new MensajeEmitirFichero("server", myIp, "cliente", prop, ficheroPedido, origen));
		} catch (IOException e) {

			System.out.println("No se ha podido enviar la peticion de emision de " + ficheroPedido + " a " + prop);
			e.printStackTrace();
		}
	}

	public void enviarPeerPreparado(InetAddress ipreceptor, InetAddress ip, int puerto, String filename) {
		m.requestRead();
		System.out.println("Voy a decirle a "+ ipreceptor + " que " + ip + " le esta esperando en " + puerto);
		ObjectOutputStream toCliente = canales.get(ipreceptor);
		m.releaseRead();
		
		try {
			toCliente.writeObject(new MensajePreparadoServidorCliente("Server", myIp, "cliente", ipreceptor, ip, puerto, filename));
		} catch (IOException e) {
			System.out.println("No se ha podido enviar los datos a " + ipreceptor + " de que " + ip + " le espera en " + puerto);
			e.printStackTrace();
		}
	}

	public void elimina(InetAddress ip) {
		// TODO Auto-generated method stub
		m.requestWrite();
		System.out.println("Voy a sacar a " + ip + " del registro");
		canales.remove(ip);
		m.releaseWrite();
	}
	
}
