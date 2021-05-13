package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class OyenteServidor extends Thread {
	
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public OyenteServidor(ObjectInputStream is, ObjectOutputStream os) {
		in = is;
		out = os;
	}
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		String s = sc.nextLine();
		
		while(s != "END") {
			try {
				out.writeObject(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String msg = "";
			try {
				msg = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(msg);
			
			s = sc.nextLine();
			
		}
		
	}
	
}
