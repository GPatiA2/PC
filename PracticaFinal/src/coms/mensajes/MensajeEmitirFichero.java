package coms.mensajes;

import java.net.InetAddress;

import cliente.Cliente;
import coms.UserInfo;

public class MensajeEmitirFichero extends MensajeCliente {
	// Nombre del fichero a emitir
	String nombreFichero;
	// El solicitante de la emision del fichero
	UserInfo solicitante;
	
	public MensajeEmitirFichero(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, String nombre, UserInfo sol) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		nombreFichero = nombre;
		solicitante = sol;
	}

	
	@Override
	public void accion(Cliente c) {
		// TODO Auto-generated method stub
		c.emitirFichero(nombreFichero, solicitante);
	}

}