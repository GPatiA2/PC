package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;

public class MensajePreparadoClienteServidor extends MensajeServer {
	// Puerto en el que se ha preparado el cliente para hacer de servidor
	int puerto;
	// IP del cliente que esta preparado para hacer de servidor
	InetAddress ip;
	// Id del usuario al que hay que comunicar que este cliente ya esta preparado
	String idreceptor;
	
	public MensajePreparadoClienteServidor(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, int puerto, InetAddress ip, String idreceptor) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.puerto = puerto;
		this.ip = ip;
		this.idreceptor = idreceptor;
	}

	@Override
	public void accion(Servidor s) {
		// TODO Auto-generated method stub
		s.enviarPeerPreparado(idreceptor, ip, puerto);
	}

}
