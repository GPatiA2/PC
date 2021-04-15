
public class VolatileInteger {
	private volatile Integer n;
	
	public VolatileInteger(int num) {
		n = Integer.valueOf(num);
	}
	
	public int intValue() {
		return n.intValue();
	}
	
	public String toString() {
		return n.toString();
	}
}
