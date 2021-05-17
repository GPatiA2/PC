package coms.mensajes;

import java.net.InetAddress;

import cliente.Cliente;

public class MensajeConfirmacionCerrarConexion extends MensajeCliente {

	public MensajeConfirmacionCerrarConexion(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accion(Cliente c) {
		// TODO Auto-generated method stub
		c.cerrarConexion();
	}

}
