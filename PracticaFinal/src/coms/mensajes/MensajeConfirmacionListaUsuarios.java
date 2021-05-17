package coms.mensajes;

import java.net.InetAddress;

import cliente.Cliente;

public class MensajeConfirmacionListaUsuarios extends MensajeCliente {

	UserList l;
	
	public MensajeConfirmacionListaUsuarios(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, Userlist l) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		this.l = l;
	}

	@Override
	public void accion(Cliente c) {
		// TODO Auto-generated method stub
		c.muestraUserList(l);
	}

}
