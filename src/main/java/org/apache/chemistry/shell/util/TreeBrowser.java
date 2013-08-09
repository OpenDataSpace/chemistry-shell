package org.apache.chemistry.shell.util;

import org.apache.chemistry.opencmis.client.api.Folder;

public class TreeBrowser extends SimpleBrowser {

	private int depth;
	
	public TreeBrowser(Folder root, int depth) {
		super(root);
		this.depth = depth;
	}
	
	public TreeBrowser(Folder root) {
		this(root, -1);
	}
	

}
