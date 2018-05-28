package edu.umb.cs.cs681.hw14;

import java.util.Date;

public class File extends FSElement{

	public File(Directory parent, String name, String owner, Date created, Date lastModified, int size, boolean isFile) {
		super(parent, name, owner, created, lastModified, size, isFile);
	}
}
