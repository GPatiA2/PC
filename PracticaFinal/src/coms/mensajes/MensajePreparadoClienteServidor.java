package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;

public class MensajePreparadoClienteServidor extends MensajeServer {
	
	int puerto;
	InetAddress ip;
	String idreceptor;
	
	public MensajePreparadoClienteServidor(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, int puerto, InetAddress ip, String idreceptor) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accion(Servidor s) {
		// TODO Auto-generated method stub
		s.enviarPeerPreparado(idreceptor, ip, puerto);
	}

}
