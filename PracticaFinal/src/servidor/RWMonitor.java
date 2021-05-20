package servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWMonitor {
	
	private ReentrantLock cerrojo;
	
	// Cola donde esperan para entrar a leer
	private Condition esperaLectura;
	
	// Cola donde esperan para entrar a escribir
	private Condition esperaEscritura;
	
	// Numero de lectores en el monitor
	private int nr;
	
	// Numero de escritores en el monitor
	private int nw;
	
	
	public RWMonitor() {
		cerrojo = new ReentrantLock(true);
		esperaLectura = cerrojo.newCondition();
		esperaEscritura = cerrojo.newCondition();
		
		nr = 0;
		nw = 0;
	}
	
	public void requestRead() {
		cerrojo.lock();
		while(nw > 0) {
			try {
				// Cuando me despierten de aqui voy a volver a la cola ENTRY , y nada me 
				//   garantiza que al volver a entrar al monitor no vaya a haber un writer dentro
				//   tambien, asi que tengo que recomprobar condicion
				esperaLectura.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nr = nr + 1;
		cerrojo.unlock();
	}
	
	public void releaseRead() {
		cerrojo.lock();
		nr = nr - 1;
		if(nr == 0) {
			esperaEscritura.signal();
		}
		cerrojo.unlock();
	}
	
	public void requestWrite() {
		cerrojo.lock();
		while(nr > 0 || nw > 0) {
			try {
				// Por lo mismo que antes, tengo que esperarme aqui a que me despierten y cuando vuelva
				//   a entrar, nada me garantiza que no vaya a haber alguien mas en el monitor, asi que 
				//   tengo que volver a mirar la condicion
				esperaEscritura.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nw = nw + 1;
		cerrojo.unlock();
	}
	
	public void releaseWrite() {
		cerrojo.lock();
		nw = nw - 1;
		// Elijo despertar a un escritor y a todos los lectores y que peleen por entrar
		esperaEscritura.signal();
		esperaLectura.signalAll();
		cerrojo.unlock();
	}
	
}
