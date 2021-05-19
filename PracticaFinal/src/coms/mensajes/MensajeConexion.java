package coms.mensajes;

import java.net.InetAddress;
import java.util.List;

import servidor.Servidor;

public class MensajeConexion extends MensajeServer {

	
	List<String> nombresficheros;
	
	public MensajeConexion(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto, List<String> nombres) {
		super(idfrom, ipfrom, idto, ipto);
		// TODO Auto-generated constructor stub
		nombresficheros = nombres;
	}


	@Override
	public void accion(Servidor s) {
		// TODO Auto-generated method stub
		s.aceptarConexion(this.origen, nombresficheros);
	}

}
