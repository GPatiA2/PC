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

/**
 * Clase que implementa el cliente de la aplicacion
 * @author Guille
 *
 */
// en bin -> java -cp . cliente.Cliente <id> <ipserver> <puertoserver>
public class Cliente implements Observable<ObserverCliente>{
	
	/**
	 * Ip del servidor de la aplicacion
	 */
	InetAddress serverip;
	/**
	 * Puerto al que el cliente tiene que conectarse
	 */
	int serverPort;

	/**
	 * Ip de este cliente
	 */
	InetAddress myIP;
	/**
	 * Id de este cliente
	 */
	String id;
	/**
	 * Objeto que contiene los metodos necesarios para escuchar los mensajes del servidor
	 */
	OyenteServidor os;
	/**
	 * Socket por el que se realiza la comunicacion con el servidor
	 */
	Socket s;
	/**
	 * Indica si el cliente esta o no conectado al servidor
	 */
	boolean conectado;
	/**
	 * Lista de nombres de los ficheros que tiene el cliente
	 */
	List<String> fileIds;
	/**
	 * Canal de salida al servidor
	 */
	ObjectOutputStream toServer;
	/**
	 * Canal de entrada desde el servidor
	 */
	ObjectInputStream fromServer;
	/**
	 * Lista de ficheros totales en el sistema
	 */
	List<FileInfo> usuarioFichero;
	/**
	 * Observadores de este objeto. Se usan para actualizar la GUI
	 */
	List<ObserverCliente> observers;
	/**
	 * Ultimo puerto utilizado para enviar un fichero a otro cliente
	 */
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
	
	/**
	 * Constructor de la clase, recibe los datos necesarios para conectarse al servidor
	 * @param sip IP del servidor de la aplicacion
	 * @param sport Puerto por el que el servidor espera nuevas conexiones
	 * @param id ID de este cliente
	 * @param myIp IP de este cliente
	 */
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
	
	/**
	 * Intenta conectar a este cliente con el servidor de la aplicacion, extrae los canales para realizar la comunicacion del Socket y crea un hilo para atender y recibir
	 * los mensajes del servidor
	 * 
	 * Finalmente, envia una solicitud de conexion al servidor
	 * @throws IOException
	 */
	private void init() throws IOException {
		s = new Socket(serverip, serverPort);
		System.out.println("Socket creado");
		toServer = new ObjectOutputStream(s.getOutputStream());
		System.out.println("Canal de salida del cliente obtenido");
		if(toServer != null) {
			System.out.println("Canal de salida no es null");
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
	
	/**
	 * Carga la lista de nombres de fichero disponibles.
	 * Los ficheros deben estar en /bin/<id_cliente> antes de lanzar el cliente para ser cargados correctamente
	 * @return Una lista con los nombres de los ficheros de los que dispone este cliente.
	 */
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
	
	
	/**
	 * Realiza las operaciones necesarias para indicar a la GUI que la conexion se ha establecido exitosamente
	 */
	// Mostrar confirmación de conexion
	public void confirmarConexion() {
		// TODO Auto-generated method stub
		this.conectado = true;
		for(ObserverCliente ob : observers) {
			ob.alRecibirConfirmacionConectar();
		}
	}
	
	/**
	 * Realiza los preparativos necesarios para emitir el fichero que se pasa como parametro al cliente que se comunica por el
	 * servidor a través del puerto ps
	 * Crea un proceso encargado de enviar el fichero a traves del puerto correspondiente y lo envia
	 * @param nombreFichero Nombre del fichero que este cliente va a transmitir
	 * @param ps Puerto que utiliza el cliente receptor para comunicarse con el servidor 
	 */
	// Crear un proceso para empezar a emitir el fichero de nombrefichero al usuario en ps
	public void emitirFichero(String nombreFichero, int ps) {
		// TODO Auto-generated method stub
		for(ObserverCliente ob : observers) {
			ob.alRecibirPeticionDeEmision(nombreFichero);
		}
		
		int puertoEmision = ultimoPuerto + 20;
		ultimoPuerto += 20;
		
		System.out.println("Voy a emitir el " + nombreFichero + " por el puerto " + puertoEmision );

		EmisorFicheros ef = new EmisorFicheros(nombreFichero,puertoEmision, id);
		ef.start();
		
		try {
			toServer.writeObject(new MensajePreparadoClienteServidor(id, myIP, "Server" , serverip, ps, puertoEmision, nombreFichero));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha podido enviar el peerPreparado al servidor con destino servidor");
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea un proceso para recibir el fichero que se ha solicitado
	 * @param ip IP del emisor del fichero solicitado
	 * @param puerto Puerto por el que el cliente emisor atiende conexiones
	 * @param filename Nombre del fichero solicitado
	 */
	// Crear un proceso para recibir el archivo que está enviando el cliente con la ip y el puerto
	public void conectarConClienteEmisor(InetAddress ip, int puerto, String filename) {
		// TODO Auto-generated method stub
		System.out.println("Voy a recibir " + filename + " de " + ip + " en el puerto " + puerto);
		ReceptorFicheros rf = new ReceptorFicheros(ip, puerto, filename, id);
		rf.start();
	}
	
	/**
	 * Envia una solicitud de desconexion al servidor
	 */
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

	
	/**
	 * Envia un mensaje al servidor solicitando que se le registre en el sistema
	 * @param ip IP del servidor al que se va solicitar el registro
	 */
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

	/**
	 * Envia una peticion al servidor para que le envie la lista de ficheros y sus propietarios registrados en el sistema
	 */
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

	/**
	 * Envia al servidor una peticion para que solicite a uno de los clientes que tenga el fichero con el nombre que se pasa 
	 * como parametro
	 * @param s Nombre del fichero que se solicita
	 */
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

	/**
	 * Realiza las operaciones necesarias para almacenar y mostrar la lista de ficheros registrados en el sistema
	 * @param l Lista de ficheros registrados en el sistema
	 */
	public void muestraUserList(List<FileInfo> l) {
		// TODO Auto-generated method stub
		usuarioFichero = l;
		for(ObserverCliente ob : observers) {
			ob.alRecibirTabla(l);
		}
	}

}
