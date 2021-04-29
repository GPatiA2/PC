import java.util.ArrayList;
import java.util.List;

public class practica4Ej12 {

	
	int productores;
	int consumidores;
	Almacen a;
	List<Thread> l;
	
	public practica4Ej12(int p, int c, int tam) {
		productores = p;
		consumidores = c;
		l = new ArrayList<Thread>();
		a = new AlmacenPractica(tam);
		
	}
	
	public static void main(String[] args) {
		int productores = Integer.parseInt(args[0]);
		int consumidores = Integer.parseInt(args[1]);
		int acciones = Integer.parseInt(args[2]);
		int tamanio = Integer.parseInt(args[3]);
		practica4Ej12 p32 = new practica4Ej12(productores, consumidores, tamanio); 
		p32.execute(productores, consumidores, acciones);
	}

	public void execute(int productores, int consumidores, int acciones) {
		for(int i = 0; i < productores; i++) {
			l.add(new prodThread(a,acciones));
		}
		int aCons = (int)Math.ceil(Double.valueOf((acciones*productores))/consumidores);
		System.out.println(aCons);
		for(int i = 0; i < consumidores; i++) {
			l.add(new consThread(a,aCons));
		}
		for(int i = 0; i < l.size(); i++) {
			l.get(i).start();
		}
		for(int i = 0; i < l.size(); i++) {
			try {
				l.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class prodThread extends Thread{
		
		int c;
		Almacen a;
		int v;
		
		public prodThread(Almacen a, int v) {
			this.a = a;
			c = 0;
			this.v = v;
		}
		
		public void run() {
			for(c = 0; c < v; c++) {
				a.almacenar(c);
			}
		}
	}
	
	public class consThread extends Thread{
		Almacen a;
		int v;
		public consThread(Almacen al, int v) {
			a = al;
			this.v = v;
		}
		
		public void run() {
			for(int i = 0; i < v; i++) {
				int cons = a.extraer();
				System.out.println(cons);
				
			}
		}
	}
	
	public class AlmacenPractica implements Almacen {
		
		volatile VolatileInteger buf[];
		volatile int ini; // Primera posicion legible del buffer
		volatile int fin; // Primera posicion libre del buffer
		int tam;
		
		volatile SyncMonitorEj12 m;
		
		volatile VolatileInteger ap[]; // Veces que se produce el elemento iesimo
		volatile VolatileInteger cons[]; // Veces que se consume el elemento iesimo
		
		public AlmacenPractica(int tam) {
			buf = new VolatileInteger[tam];
//			ap = new VolatileInteger[tam];
//			cons = new VolatileInteger[tam];
			
			this.tam = tam;
			ini = 0;
			fin = 0;
			
			m = new SyncMonitorEj12(tam);
			
//			for(int i = 0; i < tam; i++) {
//				ap[i] = new VolatileInteger(0);
//				cons[i] = new VolatileInteger(0);
//			}
			
		}
		
		@Override
		public void almacenar(int producto) {
			
			m.entrar_producir();
			
			buf[fin] = new VolatileInteger(producto);
			fin = (fin + 1) % tam;
			
//			System.out.println("Producido " + producto);
//			ap[producto] = new VolatileInteger(ap[producto].intValue() + 1);

			//			String p = "Producciones: ";
//			String c = "Consumiciones: ";
//			for(int j = 0; j < a.getTam(); j++) {
//				p += ap[j] + " ";
//				c += cons[j] + " ";
//			}
//			p += '\n';
//			c += '\n';
//			System.out.print(p);
//			System.out.print(c);
			
			
			m.salir_producir();
		}

		@Override
		public int extraer() {
			// TODO Auto-generated method stub
			m.entrar_consumir();
			
			VolatileInteger g = buf[ini];
			int v = g.intValue();
			buf[ini] = null;
			ini = (ini + 1) % tam;
			
			
//			System.out.println("Consumido " + v);
//			cons[v] = new VolatileInteger(cons[v].intValue() + 1);
//			String p = "Producciones: ";
//			String c = "Consumiciones: ";
//			for(int j = 0; j < a.getTam(); j++) {
//				p += ap[j] + " ";
//				c += cons[j] + " ";
//			}
//			p += '\n';
//			c += '\n';
//			System.out.print(p);
//			System.out.print(c);
			
			m.salir_consumir();
			
			return v;
		}

		@Override
		public boolean hayProducto() {
			// TODO Auto-generated method stub
			return true;
		}
		
		public int getTam() { return this.tam; }
	}
}
