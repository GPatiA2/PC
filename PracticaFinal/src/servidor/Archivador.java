package servidor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import coms.FileInfo;
import coms.UserInfo;

public class Archivador {
	
	Map<String, Set<InetAddress>> ficheroAPropietario;
	
	Semaphore s;
	
	List<FileInfo> listado;
	
	public Archivador() {
		ficheroAPropietario =  new HashMap<String, Set<InetAddress>>();
		s = new Semaphore(1);
		listado = new ArrayList<FileInfo>();
	}

	public List<FileInfo> recuperaLista() {
		// TODO Auto-generated method stub
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.release();
		return listado;
	}

	public void almacena(List<String> nombresficheros, UserInfo ui) {
		// TODO Auto-generated method stub
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String s : nombresficheros) {
			if(ficheroAPropietario.containsKey(s)) {
				ficheroAPropietario.get(s).add(ui.getIP());
			}
			else {
				Set<InetAddress> c = new HashSet<InetAddress>();
				c.add(ui.getIP());
				ficheroAPropietario.put(s,c);
			}
			FileInfo fi = new FileInfo(ui.getId(), s);
			listado.add(fi);
		}
		s.release();
	}
	
	
	
}
