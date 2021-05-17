package coms.oyentes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import coms.UserInfo;

public class OyenteCliente extends Oyente {

	Servidor s;
	
	public OyenteCliente(Servidor s, ObjectInputStream in, ObjectOutputStream out) {
		super(in, out);
		this.s = s;
		// TODO Auto-generated constructor stub
	}



}
