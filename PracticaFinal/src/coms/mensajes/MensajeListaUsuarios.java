package coms.mensajes;

import java.net.InetAddress;

import coms.oyentes.OyenteCliente;
import servidor.Servidor;

public class MensajeListaUsuarios extends MensajeServer {

	public MensajeListaUsuarios(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accion(Servidor s, int puerto) {
		// TODO Auto-generated method stub
		s.enviarListaUsuarios(this.origen, puerto);
	}

}
