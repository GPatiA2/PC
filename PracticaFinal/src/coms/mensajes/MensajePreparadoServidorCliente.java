package coms.mensajes;

import java.net.InetAddress;

import cliente.Cliente;

public class MensajePreparadoServidorCliente extends MensajeCliente{
	
	// Ip en la que esperan al receptor de este mensaje
	InetAddress ip;
	// Puerto en el que esperan al receptor de este mensaje
	int puerto;
	
	public MensajePreparadoServidorCliente(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, InetAddress ip, int puerto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.puerto = puerto;
	}

	@Override
	public void accion(Cliente c) {
		// TODO Auto-generated method stub
		c.conectarConClienteEmisor(ip,puerto);
	}

}
