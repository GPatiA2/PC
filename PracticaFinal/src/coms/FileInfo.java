package coms;

import java.io.Serializable;

public class FileInfo implements Serializable{
	
	String user;
	String filename;
	
	public FileInfo(String u, String f) {
		user = u;
		filename = f;
	}
	
	public String getFileName() { return filename; }
	public String getUser() { return user; }
	
}
