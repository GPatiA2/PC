package servidor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import coms.FileInfo;
import coms.UserInfo;

public class Archivador {
	
	Map<String, Set<String>> ficheroAPropietario;
	Map<String, Set<String>> propietarioAFichero;
	List<FileInfo> listado;
	
	RWMonitor m;
	
	
	public Archivador() {
		ficheroAPropietario =  new HashMap<String, Set<String>>();
		propietarioAFichero = new HashMap<String, Set<String>>();
		
		m = new RWMonitor();
		listado = new ArrayList<FileInfo>();
	}

	public List<FileInfo> recuperaLista() {
		m.requestRead();
		List<FileInfo> l = listado;
		m.releaseRead();
		return l;
	}

	public void almacena(List<String> nombresficheros, UserInfo ui) {
		// TODO Auto-generated method stub
		m.requestWrite();
		for(String s : nombresficheros) {
			
			if(ficheroAPropietario.containsKey(s)) {
				ficheroAPropietario.get(s).add(ui.getId());
			}
			else {
				Set<String> c = new HashSet<String>();
				c.add(ui.getId());
				ficheroAPropietario.put(s,c);
			}
			if(propietarioAFichero.containsKey(ui.getId())) {
				propietarioAFichero.get(ui.getId()).add(s);
			}
			else {
				Set<String> fich = new HashSet<String>();
				fich.add(s);
				propietarioAFichero.put(ui.getId(), fich);
			}
			
			FileInfo fi = new FileInfo(ui, s);
			listado.add(fi);
		}
		m.releaseWrite();
	}

	public void elimina(String id) {
		// TODO Auto-generated method stub
		m.requestWrite();
		List<FileInfo> quitarDeListado = new ArrayList<FileInfo>();
		List<String> quitarDeFicheroAPropietario = new ArrayList<String>();
		
		for(FileInfo fi : listado) {
			if(fi.getUser().getIP() == ip) {
				quitarDeListado.add(fi);
				quitarDeFicheroAPropietario.add(fi.getFileName());
			}
		}
		
		for(FileInfo fi : quitarDeListado) { listado.remove(fi); }
		for(String s: quitarDeFicheroAPropietario) { ficheroAPropietario.get(s).remove(ip); }
		propietarioAFichero.remove(ip);
		
		m.releaseWrite();
		
	}

	public InetAddress buscaPropietario(String ficheroPedido) {
		// TODO Auto-generated method stub
		m.requestRead();
		
		Set<InetAddress> s = ficheroAPropietario.get(ficheroPedido);
		Iterator<InetAddress> it = s.iterator();
		InetAddress ip = it.next();
		
		m.releaseRead();
		
		return ip;
	}
	
	
	
}
