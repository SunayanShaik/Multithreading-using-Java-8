package edu.umb.cs.cs681.hw14;

import java.util.ArrayList;
import java.util.Date;

public class Directory extends FSElement{
	private FSElement parent;
	private ArrayList<FSElement> children = new ArrayList<FSElement>();
	private ArrayList<Directory> drives = new ArrayList<>();
	
	public Directory(Directory parent, String name, String owner, Date created, Date lastModified, int size, boolean isFile) {
		super(parent, name, owner, created, lastModified, size, isFile);
	}
	
	public ArrayList<FSElement> getChildren() {
		return this.children;
	}
	
	public void appendChild(FSElement fsElement) {
		children.add(fsElement);
	}
	
	public void addChild(FSElement child, int index) {
		children.add(child);
	}
	
	public void addDrives(FSElement child) {
		if(child instanceof Directory) {
			drives.add((Directory) child);
		}
		
		if (child instanceof Link) {
			drives.add((Directory) child);
		}
		
		if (child instanceof File) {
			drives.add((Directory) child);
		}
	}
	
}
