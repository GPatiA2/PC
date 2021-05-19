package coms.mensajes;

import java.net.InetAddress;
import java.util.List;

import cliente.Cliente;
import coms.FileInfo;

public class MensajeConfirmacionListaUsuarios extends MensajeCliente {

	List<FileInfo> l;
	
	public MensajeConfirmacionListaUsuarios(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, List<FileInfo> l) {
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
