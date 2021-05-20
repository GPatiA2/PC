package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;


// Mensajes que recibe el servidor
public abstract class MensajeServer extends Mensaje {

	public MensajeServer(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void accion(Servidor s, int puerto);

}
