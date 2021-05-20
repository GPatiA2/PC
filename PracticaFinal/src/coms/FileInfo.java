package coms;

import java.io.Serializable;

public class FileInfo implements Serializable{
	
	UserInfo ui;
	String filename;
	
	public FileInfo(UserInfo u, String f) {
		ui = u;
		filename = f;
	}
	
	public String getFileName() { return filename; }
	public UserInfo getUser() { return ui; }
	
}
