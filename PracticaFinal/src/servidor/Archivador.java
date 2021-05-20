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

/**
 * Esta clase se encarga de almacenar la informacion sobre los ficheros que tiene cada cliente conectado
 * Para manejar los problemas de concurrencia, se ha implementado el monitor visto en clase para lectores escritores.
 * 
 * @author Guille
 *
 */
public class Archivador {
	
	Map<String, Set<Integer>> ficheroAPropietario;
	Map<Integer, Set<String>> propietarioAFichero;
	List<FileInfo> listado;
	
	RWMonitor m;
	
	
	public Archivador() {
		ficheroAPropietario =  new HashMap<String, Set<Integer>>();
		propietarioAFichero = new HashMap<Integer, Set<String>>();
		
		m = new RWMonitor();
		listado = new ArrayList<FileInfo>();
	}

	public List<FileInfo> recuperaLista() {
		m.requestRead();
		List<FileInfo> l = listado;
		m.releaseRead();
		return l;
	}

	public void almacena(List<String> nombresficheros, UserInfo ui, int puerto) {
		// TODO Auto-generated method stub
		m.requestWrite();
		for(String s : nombresficheros) {
			
			if(ficheroAPropietario.containsKey(s)) {
				ficheroAPropietario.get(s).add(puerto);
			}
			else {
				Set<Integer> c = new HashSet<Integer>();
				c.add(puerto);
				ficheroAPropietario.put(s,c);
			}
			if(propietarioAFichero.containsKey(puerto)) {
				propietarioAFichero.get(puerto).add(s);
			}
			else {
				Set<String> fich = new HashSet<String>();
				fich.add(s);
				propietarioAFichero.put(puerto, fich);
			}
			
			FileInfo fi = new FileInfo(ui, s, puerto);
			listado.add(fi);
		}
		m.releaseWrite();
	}

	public void elimina(int puerto) {
		// TODO Auto-generated method stub
		m.requestWrite();
		List<FileInfo> quitarDeListado = new ArrayList<FileInfo>();
		List<String> quitarDeFicheroAPropietario = new ArrayList<String>();
		
		for(FileInfo fi : listado) {
			if(fi.getPuertoUser() == puerto) {
				quitarDeListado.add(fi);
				quitarDeFicheroAPropietario.add(fi.getFileName());
			}
		}
		
		for(FileInfo fi : quitarDeListado) { listado.remove(fi); }
		for(String s : quitarDeFicheroAPropietario) { ficheroAPropietario.get(s).remove(puerto); }
		propietarioAFichero.remove(puerto);
		
		m.releaseWrite();
		
	}

	public int buscaPropietario(String ficheroPedido) {
		// TODO Auto-generated method stub
		m.requestRead();
		
		Set<Integer> s = ficheroAPropietario.get(ficheroPedido);
		Iterator<Integer> it = s.iterator();
		Integer p = it.next();
		
		m.releaseRead();
		
		return p;
	}
	
	
	
}
