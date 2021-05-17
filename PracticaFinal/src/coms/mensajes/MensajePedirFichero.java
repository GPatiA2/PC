package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;

public class MensajePedirFichero extends MensajeServer {
	
	String ficheroPedido;
	String solicitado;
	
	public MensajePedirFichero(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, String ficheroPedido, String usuario) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.ficheroPedido = ficheroPedido;
		this.solicitado = usuario;
	}

	@Override
	public void accion(Servidor s) {
		// TODO Auto-generated method stub
		s.pedirFichero(solicitado, ficheroPedido, this.origen);
	}

}
