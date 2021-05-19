package coms.mensajes;

import java.io.Serializable;
import java.net.InetAddress;

import coms.UserInfo;

public abstract class Mensaje implements Serializable{
	
	UserInfo origen;
	UserInfo destino;

	
	public Mensaje(UserInfo from, UserInfo to){
		origen = from;
		destino = to;
	}
	
	public Mensaje(String idfrom, InetAddress ipfrom, String idto, InetAddress ipto) {
		origen = new UserInfo(idfrom, ipfrom);
		destino = new UserInfo(idto, ipto);
	}
	
	public UserInfo getOrigen() { return origen; }
	public UserInfo getDestino() { return destino; }
	
}
