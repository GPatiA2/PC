package coms.mensajes;

import java.net.InetAddress;

import cliente.Cliente;

public class MensajePreparadoServidorCliente extends MensajeCliente{
	
	// Ip en la que esperan al receptor de este mensaje
	InetAddress ip;
	// Puerto en el que esperan al receptor de este mensaje
	int puerto;
	// Nombre del fichero que se va a recibir
	String filename;
	
	public MensajePreparadoServidorCliente(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, InetAddress ip, int puerto, String filename) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.puerto = puerto;
		this.filename = filename;
	}

	@Override
	public void accion(Cliente c) {
		// TODO Auto-generated method stub
		c.conectarConClienteEmisor(ip,puerto, filename);
	}

}
