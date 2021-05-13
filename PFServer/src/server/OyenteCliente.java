package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class OyenteCliente extends Thread {
	
	ObjectInputStream is;
	ObjectOutputStream os;
	
	public OyenteCliente(ObjectInputStream in, ObjectOutputStream out) {
		is = in;
		os = out;
	}
	
	public void run() {
		while(true) {
			String m = "";
			try {
				m = (String) is.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Mensaje de tipo no admitido");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				os.writeObject(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
