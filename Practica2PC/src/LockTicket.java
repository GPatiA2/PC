import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket implements Lock {
	
	volatile int next;
	
	volatile AtomicInteger num;
	
	volatile int turn[];
	
	int num_procesos;
	
	public LockTicket(int n_procesos) {
		num_procesos = n_procesos+1;
		turn = new int[num_procesos];
		next = 1;
		num = new AtomicInteger(1);
		
		for(int i = 0; i < num_procesos; i++) {
			turn[i] = 0;
		}
	}
	
	@Override
	public void takeLock(int id) {
		// TODO Auto-generated method stub
		turn[id] = num.getAndAdd(1);
		while(turn[id] != next) {}
	}

	@Override
	public void releaseLock(int id) {
		// TODO Auto-generated method stub
		next = next+1;
	}

}
