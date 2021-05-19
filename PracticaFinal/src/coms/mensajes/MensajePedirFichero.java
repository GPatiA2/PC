package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;

public class MensajePedirFichero extends MensajeServer {
	
	String ficheroPedido;
	
	public MensajePedirFichero(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, String ficheroPedido) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.ficheroPedido = ficheroPedido;
	}

	@Override
	public void accion(Servidor s) {
		// TODO Auto-generated method stub
		s.pedirFichero(ficheroPedido, this.origen);
	}

}
