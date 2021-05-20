package coms.oyentes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import coms.UserInfo;
import coms.mensajes.MensajeServer;
import servidor.Servidor;

public class OyenteCliente extends Oyente {

	Servidor s;
	boolean end;
	
	public OyenteCliente(Servidor s, ObjectInputStream in, ObjectOutputStream out) {
		super(in, out);
		this.s = s;
		end = false;
		// TODO Auto-generated constructor stub
	}

	public void run() {
		while(!end) {
			try {
				System.out.println("Recibiendo en servidor");
				MensajeServer ms;
				ms = (MensajeServer) in.readObject();
				ms.accion(s);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void acabar() {
		// TODO Auto-generated method stub
		end = true;
	}

}
