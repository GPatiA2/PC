package coms;

import java.io.Serializable;

public class FileInfo implements Serializable{
	
	UserInfo ui;
	String filename;
	int puertoUser;
	
	public FileInfo(UserInfo u, String f, int p) {
		ui = u;
		filename = f;
		puertoUser = p;
	}
	
	public String getFileName() { return filename; }
	public UserInfo getUser() { return ui; }
	public int getPuertoUser() { return puertoUser; }
}
