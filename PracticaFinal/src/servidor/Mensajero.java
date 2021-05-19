package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coms.FileInfo;
import coms.UserInfo;
import coms.oyentes.OyenteCliente;

public class Mensajero {
	
	Map<InetAddress, ObjectOutputStream> canales;
	Map<String, InetAddress> usuarios;
	
	public Mensajero() {
		canales = new HashMap<InetAddress, ObjectOutputStream>();
		usuarios = new HashMap<String, InetAddress>();
	}

	public void registrar(InetAddress inetAddress, ObjectOutputStream oc) {
		// TODO Auto-generated method stub
		canales.put(inetAddress, oc);
	}

	public void guardaUsername(String id, InetAddress ip) {
		// TODO Auto-generated method stub
		usuarios.put(id, ip);
	}

	public void enviaListaFicheros(List<FileInfo> l, UserInfo origen) throws IOException {
		// TODO Auto-generated method stub
		canales.get(origen.getIP()).writeObject(l);
	}
	
}
