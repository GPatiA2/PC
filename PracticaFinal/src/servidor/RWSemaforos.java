package servidor;

import java.util.concurrent.Semaphore;

/**
 * Solucion a los lectores escritores mediante semaforos vista en clase
 * @author Guille
 *
 */
public class RWSemaforos {
	
	// Semaforo para que esperen los lectores
	Semaphore r;
	// Semaforo para que esperen los escritores
	Semaphore w;

	// Semaforo para proteger las variables nr , nw , dr , dw
	Semaphore e;
	
	// Numero de lectores dentro
	int nr;
	// Numero de escritores dentro
	int nw;
	// Numero de lectores esperando
	int dr;
	// Numero de escritores esperando
	int dw;
	
	public RWSemaforos() {
		r = new Semaphore(0);
		w = new Semaphore(0);
		
		e= new Semaphore(1);
		nr = 0;
		nw = 0;
		dr = 0;
		dw = 0;
	}
	
	
	public void requestRead() {
		// TODO Auto-generated method stub
		try {
			// Intento coger el mutex de e para modificar variables compartidas
			e.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Si no hay escritores significa que puedo entrar a leer
		if(nw > 0) {
			// Si hay escritores
			// Soy un lector mas que espera
			dr = dr + 1;
			// Suelto el mutex e
			e.release();
			
			try {
				// Me pongo a esperar en r
				r.acquire();
				// Al despertarme me han pasado el testigo
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// Al llegar a este punto se tiene el mutex de e porque:
		//        - nw <= 0 asi que no he soltado e
		//        - nw > 0 y me despiertan pasandome el testigo
		
		// Ahora soy un lector mas dentro
		nr = nr + 1;
		// Si hay lectores esperando
		if(dr > 0) {
			// Despierto a uno y descuento uno de los que esperan
			dr = dr-1;
			r.release();
		}
		// Si no hay lectores esperando
		else {
			// Suelto e para que intente entrar quien quiera
			e.release();
		}
		
	}

	public void releaseRead() {
		// Cojo el mutex de e para modificar variables compartidas
		try {
			e.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Como he acabado de leer, ya no estoy dentro
		nr = nr - 1;
		// Si no hay lectores dentro y hay algun escritor esperando
		if(nr == 0 && dw > 0) {
			// Descuento 1 escritor esperando porque lo voy a despertar
			// Y le despierto --> Paso de testigo
			dw = dw - 1;
			w.release();
		}
		// Si no
		else {
			// Libero el mutex e y quien quiera que intente entrar
			e.release();
		}
	}

	public void requestWrite() {
		// Cojo el mutex e para modificar variables de sincronizacion
		try {
			e.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Si hay alguien dentro, sea quien sea, me espero en el semaforo de w
		if(nr > 0 || nw > 0) {
			// Me añado a los escritores que esperan
			dw = dw + 1;
			e.release();
			
			try {
				// Me pongo a esperar
				w.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// Al llegar a este punto tengo seguro el mutex e porque:
		//     - He podido entrar ->  No lo he soltado
		//     - Me han despertado de w con paso de testigo
		
		// Me sumo a los escritores que estan dentro
		nw = nw + 1;
		
		// Libero e para que intente entrar quien quiera
		e.release();
	}

	public void releaseWrite() {
		// Cojo el mutex e para modificar variables de sincronizacion
		try {
			e.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Estoy saliendo, asi que resto a los escritores que estan dentro
		nw = nw - 1;
		
		// Si hay escritores esperando, los priorizare para despertarlos -> Paso de testigo
		if(dw > 0) {
			// Resto uno a los escritores que esperan porque voy a despertar a uno
			dw = dw - 1;
			// Despierto a uno de ellos
			w.release();
		}
		// Si no hay escritores, miro si hay algun lector esperando -> Paso de testigo
		else if(dr > 0) {
			// Resto uno a los lectores que esperan porque voy a despertar a uno
			dr = dr - 1;
			// Despierto a uno de los lectores que esperan
			r.release();
		}
		else {
			// Si no hay nadie esperando, libero e y que intente entrar quien quiera
			e.release();
		}
	}

}
