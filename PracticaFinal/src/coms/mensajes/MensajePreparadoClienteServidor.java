package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;

public class MensajePreparadoClienteServidor extends MensajeServer {
	// Puerto en el que se ha preparado el cliente para hacer de servidor
	int puerto;
	// Ip del usuario al que hay que comunicar que este cliente ya esta preparado
	int puertoreceptor;
	// Nombre del fichero que va a emitir el cliente
	
	String filename;
	public MensajePreparadoClienteServidor(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, int puerto, int puertoreceptor, String filename) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.puerto = puerto;
		this.puertoreceptor = puertoreceptor;
	}

	@Override
	public void accion(Servidor s, int puerto) {
		// TODO Auto-generated method stub
		s.enviarPeerPreparado(puertoreceptor, super.origen.getIP(), puerto, filename);
	}

}
