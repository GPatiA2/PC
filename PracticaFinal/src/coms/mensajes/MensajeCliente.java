package coms.mensajes;

import java.net.InetAddress;

public class MensajeCliente extends Mensaje {

	public MensajeCliente(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void accion(Cliente o);

}
