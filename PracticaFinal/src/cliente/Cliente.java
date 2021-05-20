package cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import cliente.gui.MainWindow;
import cliente.gui.ObserverCliente;
import coms.FileInfo;
import coms.UserInfo;
import coms.mensajes.MensajeCerrarConexion;
import coms.mensajes.MensajeConexion;
import coms.mensajes.MensajeListaUsuarios;
import coms.mensajes.MensajePedirFichero;
import coms.mensajes.MensajePreparadoClienteServidor;
import coms.oyentes.OyenteServidor;

// en bin -> java -cp . cliente.Cliente <id> <ipserver> <puertoserver>
public class Cliente implements Observable<ObserverCliente>{
	
	InetAddress serverip;
	int serverPort;

	InetAddress myIP;
	String id;
	OyenteServidor os;
	Socket s;
	
	boolean conectado;
	
	List<String> fileIds;
	
	ObjectOutputStream toServer;
	ObjectInputStream fromServer;
	
	List<FileInfo> usuarioFichero;
	List<ObserverCliente> observers;
	
	int ultimoPuerto;
	
	public static void main(String args[]) {
		
		InetAddress ipserver = null;
		InetAddress ipthis = null;
		try {
			ipserver = InetAddress.getByName(args[1]);
			ipthis = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int ps = Integer.parseInt(args[2]);
		String id = args[0];
		
		System.out.println("Argumentos parseados");
		
		Cliente c = new Cliente(ipserver, ps, id, ipthis);
		
		System.out.println("Cliente creado");
		
		
		
		Controller ctrl = new Controller(c);
		
		new MainWindow(ctrl);
		
		
		try {
			c.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Oyente Creado");
	}
	

	public Cliente(InetAddress sip, int sport, String id, InetAddress myIp) {
		serverip = sip;
		serverPort = sport;
		this.id = id;
		this.myIP = myIp;
		observers = new ArrayList<ObserverCliente>();
		fileIds = cargarNombresFicheros();
		conectado = false;
		usuarioFichero = new ArrayList<FileInfo>();
		ultimoPuerto = sport;
		
		System.out.println("Creando Socket");
		System.out.println("IP = " + serverip);
		System.out.println("PUERTO = " + serverPort);
	
	}
	
	private void init() throws IOException {
		s = new Socket(serverip, serverPort);
		System.out.println("Socket creado");
		toServer = new ObjectOutputStream(s.getOutputStream());
		System.out.println("Canal de salida del cliente obtenido");
		if(toServer != null) {
			System.out.println("Salida no es null");
		}
		fromServer = new ObjectInputStream(s.getInputStream());
		System.out.println("Canal de entrada al cliente obtenido");
		if(fromServer != null) {
			System.out.println("Entrada no es null");
		}
		Thread os = new OyenteServidor(this, fromServer, toServer);
		os.start();
		
		enviarMensajeConexion(serverip);
	}
	
	private ArrayList<String> cargarNombresFicheros(){
		File dir = new File(id);
		ArrayList<String> nombresFicheros = new ArrayList<String>();
		if(!dir.mkdir()) {
			for(File fichero : dir.listFiles()) {
				nombresFicheros.add(fichero.getName());
			}
		}
		
		return nombresFicheros; 
	}
	
	
	
	// Mostrar confirmación de conexion
	public void confirmarConexion() {
		// TODO Auto-generated method stub
		this.conectado = true;
		for(ObserverCliente ob : observers) {
			ob.alRecibirConfirmacionConectar();
		}
	}
	
	// Crear un proceso para empezar a emitir el fichero de nombrefichero 
	public void emitirFichero(String nombreFichero, int ps) {
		// TODO Auto-generated method stub
		for(ObserverCliente ob : observers) {
			ob.alRecibirPeticionDeEmision(nombreFichero);
		}
		
		int puertoEmision = ultimoPuerto + 20;
		ultimoPuerto += 20;
		
		EmisorFicheros ef = new EmisorFicheros(nombreFichero,puertoEmision, id);
		ef.start();
		
		try {
			toServer.writeObject(new MensajePreparadoClienteServidor(id, myIP, "Server" , serverip, puertoEmision, ps, nombreFichero));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha podido enviar el peerPreparado al servidor con destino servidor");
			e.printStackTrace();
		}
	}
	
	// Crear un proceso para recibir el archivo que está enviando el cliente con la ip y el puerto
	public void conectarConClienteEmisor(InetAddress ip, int puerto, String filename) {
		// TODO Auto-generated method stub
		ReceptorFicheros rf = new ReceptorFicheros(ip, puerto, filename, id);
	}
	
	// Imprimir adios y salir
	public void cerrarConexion() {
		// TODO Auto-generated method stub
		this.conectado = false;
		MensajeCerrarConexion msjcc = new MensajeCerrarConexion(id, myIP, "server", serverip);
		try {
			this.toServer.writeObject(msjcc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al mandar mensaje cerrar conexion");
			e.printStackTrace();
		}
	}


	@Override
	public void addObserver(ObserverCliente o) {
		// TODO Auto-generated method stub
		observers.add(o);
		o.alRegistrarse(conectado, usuarioFichero, new UserInfo(id, myIP));
	}


	@Override
	public void removeObserver(ObserverCliente o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}


	public void enviarMensajeConexion(InetAddress ip) {
		// TODO Auto-generated method stub
		MensajeConexion msgc = new MensajeConexion(this.id, this.myIP, "server", ip, this.fileIds);
		try {
			this.toServer.writeObject(msgc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al enviar msg de conexion");
		}
	}


	public void pedirTabla() {
		// TODO Auto-generated method stub
		MensajeListaUsuarios msglu = new MensajeListaUsuarios(this.id, myIP, "server", this.serverip);
		try {
			this.toServer.writeObject(msglu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al enviar mensaje de pedirTabla");
			e.printStackTrace();
		}
	}


	public void pedirFichero(String s) {
		// TODO Auto-generated method stub
		MensajePedirFichero msgpf = new MensajePedirFichero(this.id, myIP, "server", this.serverip, s);
		try {
			this.toServer.writeObject(msgpf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al enviar mensaje pedirFichero");
			e.printStackTrace();
		}
	}


	public void muestraUserList(List<FileInfo> l) {
		// TODO Auto-generated method stub
		usuarioFichero = l;
		for(ObserverCliente ob : observers) {
			ob.alRecibirTabla(l);
		}
	}

}
