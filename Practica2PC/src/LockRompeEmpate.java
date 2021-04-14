
public class LockRompeEmpate implements Lock {
	
	volatile private int in[];
	volatile private int last[];
	
	int n_procesos;
	
	public LockRompeEmpate(int n_procesos) {
		in = new int[n_procesos+1];
		last = new int[n_procesos+1];
		this.n_procesos = n_procesos;
		
		for(int i = 0; i < n_procesos+1; i++) {
			in[i] = 0;
			last[i] = 0;
		}
	}
	
	@Override
	public void takeLock(int id) {
		// TODO Auto-generated method stub
		for(int j = 1 ; j < n_procesos+1 ; j++) {
			in[id] = j;
			last[j] = id;
			for(int k = 1; k < n_procesos+1; k++) {
				if(k != id) {
					while(in[k] >= in[id] && last[j] == id) {}
				}
			}
		}
	}

	@Override
	public void releaseLock(int id) {
		// TODO Auto-generated method stub
		in[id] = 0;
	}

}
