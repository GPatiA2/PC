package coms.mensajes;

import java.net.InetAddress;

import servidor.Servidor;

public class MensajeCerrarConexion extends MensajeServer {

	public MensajeCerrarConexion(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accion(Servidor s, int puerto) {
		// TODO Auto-generated method stub
		s.cerrarConexión(this.origen, puerto);
	}

}
