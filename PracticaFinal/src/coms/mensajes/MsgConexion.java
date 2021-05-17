package coms.mensajes;

import java.net.InetAddress;

import coms.oyentes.OyenteCliente;
import servidor.Servidor;

public class MsgConexion extends MensajeServer {

	public MsgConexion(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void accion(Servidor s) {
		// TODO Auto-generated method stub
		s.aceptarConexion(this.origen);
	}

}
