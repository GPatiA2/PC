
public class LockBakery implements Lock {
	
	volatile int turn[];
	int np;
	
	public LockBakery(int num_procesos) {
		np = num_procesos+1;
		turn = new int[np];
		for(int i = 0; i < np; i++) {
			turn[i] = 0;
		}
	}
	
	@Override
	public void takeLock(int id) {
		// TODO Auto-generated method stub
		turn[id] = 1;
		int max = -1;
		for(int k = 1; k < np; k++) {
			max = (turn[k] > max)? turn[k] : max;
		}
		max++;
		turn[id] = max;
		
		for(int i = 1; i < np; i++) {
			if(i != id) {
				while(turn[i] != 0 && resuelveEmpate(id, turn[id], i, turn[i])) {}
			}
		}
	}

	@Override
	public void releaseLock(int id) {
		// TODO Auto-generated method stub
		turn[id] = 0;
	}
	
	public boolean resuelveEmpate (int id1, int turn1, int id2, int turn2){
		boolean b = (turn1 > turn2 || turn1 == turn2 && id1 > id2)? true : false;
		return b;
	}

}
