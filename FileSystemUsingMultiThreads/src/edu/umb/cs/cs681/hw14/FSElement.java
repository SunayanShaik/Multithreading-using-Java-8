package edu.umb.cs.cs681.hw14;

import java.util.Date;
import java.util.Iterator;

public class FSElement {

	private String name, owner;
	private Date created, lastModified;
	private int size;
	private boolean isFile;
	private Directory parent;

	public FSElement(Directory parent, String name, String owner, Date created, Date lastModified, int size, boolean isFile) {
		this.parent = parent;
		this.name = name;
		this.owner = owner;
		this.created = created;
		this.lastModified = lastModified;
		this.size = size;
		this.isFile = isFile;
	}

	public Directory getParent() {
		return this.parent;
	}

	public boolean isFile() {
		return this.isFile;
	}

	public int getSize() {
		if (this.isFile()) {
			return this.size;
		}
		
		if (this instanceof Link) {
			return ((Link)this).getTargetSize();
		}
		
		int directorySize=0;
		Iterator<FSElement> itr = ((Directory)this).getChildren().iterator();
		while(itr.hasNext()) {
			FSElement child = (FSElement) itr.next();
			if (!(child instanceof Link)) {
				directorySize = directorySize + child.getSize();
			}
		}
		return directorySize;
	}
	
	public String getFullPath() {
		if(this.parent == null) {
			return "/" + this.getName(); 
		}
		return this.parent.getFullPath() + "/" + this.getName();
	}
	
	public String getName() {
		return name;
	}

}
