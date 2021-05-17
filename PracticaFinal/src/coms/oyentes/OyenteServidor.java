package coms.oyentes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OyenteServidor extends Oyente {

	Cliente s;
	
	public OyenteServidor(Cliente c, ObjectInputStream in, ObjectOutputStream out) {
		super(in, out);
		this.c = c;
	}

}
