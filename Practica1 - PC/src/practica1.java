import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class practica1 {

	public static void main(String[] args) {
		Random r = new Random();
		int n_threads = Integer.parseInt(args[0]);
		List<ThreadPractica> lThreads = new ArrayList<ThreadPractica>();
		for(int i = 0; i < n_threads; i++) {
			ThreadPractica tp = new ThreadPractica(r.nextInt(2000));
			lThreads.add(tp);
		}
		for(ThreadPractica tp : lThreads) {
			tp.start();
		}
		for(ThreadPractica tp: lThreads) {
			try {
				tp.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Todos acabados");
	}
	
	public static class ThreadPractica extends Thread {
		
		private int millis;
		
		public ThreadPractica(int millis) {
			this.millis = millis;
		}
		
		public void run() {
			long id = this.getId();
			System.out.print("Mi id es ");
			System.out.print(id);
			System.out.print("\n");
			try {
				sleep(this.millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("Vuelvo a poner mi id ");
			System.out.print(id);
			System.out.print("\n");
		}
	}

}
