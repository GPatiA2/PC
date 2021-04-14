import java.util.ArrayList;
import java.util.List;

public class practica1ej3 {
	
	int t;
	int M1[][];
	int M2[][];
	int M3[][];
	
	public practica1ej3(int tamanio) {
		t = tamanio;
		M1 = new int[tamanio][tamanio];
		M2 = new int[tamanio][tamanio];
		M3 = new int[tamanio][tamanio];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int tamanio = Integer.parseInt(args[0]);
		practica1ej3 p13 = new practica1ej3(tamanio);
		p13.execute();
	}
	
	public void execute() {
		rellenarMatrices();
		muestraMatrices();
		
		List<MultThread> lThreads = new ArrayList<MultThread>();
		for(int i = 0; i < t; i++) {
			lThreads.add(new MultThread(i,M1[i],M2));
		}
		for(MultThread mt : lThreads) {
			mt.start();
		}
		for(MultThread mt : lThreads) {
			try {
				mt.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < t ; i++) {
			for(int j = 0; j < t; j++) {
				System.out.print(M3[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	public class MultThread extends Thread {
		int[] f1;
		int[][] m2;
		int indice;
		public MultThread(int ind, int[] f1, int[][] m2) {
			indice = ind;
			this.f1 = f1;
			this.m2 = m2;
		}
		
		public void run() {
			for(int j = 0; j < t ; j++) {
				M3[indice][j] = 0;
				for(int k = 0; k < t; k++) {
					M3[indice][j] += f1[k] * m2[k][j];
				}
			}
		}
	}
	
	public void muestraMatrices() {
		for(int i = 0; i < t ; i++) {
			for(int j = 0; j < t; j++) {
				System.out.print(M1[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
		
		System.out.println("-----------");
		
		for(int i = 0; i < t ; i++) {
			for(int j = 0; j < t; j++) {
				System.out.print(M2[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
		System.out.println("-----------");
	}
	
	public void rellenarMatrices() {
		int n = 0;
		for(int i = 0; i < this.t; i++) {
			for(int j = 0; j < this.t; j++) {
				M1[i][j] = n;
				M2[i][j] = n+(t*t);
				n++;
			}
		}
	}

}
