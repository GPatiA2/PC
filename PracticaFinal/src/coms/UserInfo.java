package coms;

import java.io.Serializable;
import java.net.InetAddress;

public class UserInfo implements Serializable{
	String id;
	InetAddress ip;
	
	public UserInfo (String id, InetAddress ip) {
		this.id = id;
		this.ip = ip;
	}
	
	public InetAddress getIP() {
		return this.ip;
	}
	
	public String getId() {
		return this.id;
	}
}
