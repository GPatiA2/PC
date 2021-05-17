package coms.oyentes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Oyente {
	
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public Oyente(ObjectInputStream in, ObjectOutputStream out) {
		this.in = in;
		this.out = out;
	}
}
