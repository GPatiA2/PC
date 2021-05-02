import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ej1.SyncMonitorEj122;

public class practica4Ej13 {

	
	int productores;
	int consumidores;
	Almacen a;
	List<Thread> l;
	volatile SyncMonitorEj13 m;
	int t;
	
	public practica4Ej13(int p, int c, int tam) {
		productores = p;
		consumidores = c;
		l = new ArrayList<Thread>();
		m = new SyncMonitorEj13(tam);
		this.t = tam;
	}
	
	public static void main(String[] args) {
		int productores = Integer.parseInt(args[0]);
		int consumidores = Integer.parseInt(args[1]);
		int acciones = Integer.parseInt(args[2]);
		int tamanio = Integer.parseInt(args[3]);
		practica4Ej13 p32 = new practica4Ej13(productores, consumidores, tamanio); 
		p32.execute(productores, consumidores, acciones);
	}

	public void execute(int productores, int consumidores, int acciones) {
		for(int i = 0; i < productores; i++) {
			l.add(new prodThread(m,acciones));
		}
		int aCons = (int)Math.ceil(Double.valueOf((acciones*productores))/consumidores);
		System.out.println(aCons);
		for(int i = 0; i < consumidores; i++) {
			l.add(new consThread(m,aCons));
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
		SyncMonitorEj13 m;
		int v;
		Random r;
		
		public prodThread(SyncMonitorEj13 m, int v) {
			this.m = m;
			c = 0;
			this.v = v;
			r = new Random();
		}
		
		public void run() {
			for(c = 0; c < v; c++) {
				int t1 = Math.abs(r.nextInt()) % t;
				int[] p = new int[t1];
				for(int i = 0; i < t1; i++) {
					p[i] = Math.abs(r.nextInt()) % t;
					System.out.println("Numero es " + p[i]);
				}
				m.entrar_producir(t1, p);
			}
		}
	}
	
	public class consThread extends Thread{
		SyncMonitorEj13 m;
		int v;
		Random r;
		
		public consThread(SyncMonitorEj13 m, int v) {
			this.m = m;
			this.v = v;
			r = new Random();
		}
		
		public void run() {
			for(int i = 0; i < v; i++) {
				int t1 = Math.abs(r.nextInt()) % t;
				int[] p = new int[t1];
				for(int j = 0; j < t1; j++) {
					p[j] = 0;
				}
				p = m.entrar_consumir(t1);
				for(int j = 0; j < t1; j++) {					
					System.out.println(p[j]);
				}
				
			}
		}
	}

}
