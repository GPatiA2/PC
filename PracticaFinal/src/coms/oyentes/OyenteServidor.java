package coms.oyentes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cliente.Cliente;
import coms.mensajes.Mensaje;
import coms.mensajes.MensajeCliente;

public class OyenteServidor extends Oyente {

	Cliente c;
	
	public OyenteServidor(Cliente c, ObjectInputStream in, ObjectOutputStream out) {
		super(in, out);
		this.c = c;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				MensajeCliente m = (MensajeCliente) in.readObject();
				m.accion(c);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
