package org.apache.chemistry.shell.util;

import java.io.IOException;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Tree;

public class TreeBrowser extends SimpleBrowser {

	private boolean isGetDescendantsSupported = false;
	
	public TreeBrowser(Folder root, int depth) {
		this(root,depth,false);
	}
	
	public TreeBrowser(Folder root, int depth, boolean isGetDescendantsSupported) {
		super(root, depth);
		this.isGetDescendantsSupported = isGetDescendantsSupported;
	}
	
	public TreeBrowser(Folder root) {
		this(root, -1);
	}
	
	@Override
	public void browse() throws IOException{
		if(!isGetDescendantsSupported){
			super.browse();
		}else{
			browseTree();
		}
	}
	
	public void browseTree() throws IOException {
		dumpWithPath("", root);
		doBrowseTree("", this.root.getDescendants(depth));
	}
	
	
	protected void doBrowseTree(String tabs, List<Tree<FileableCmisObject>> currentTrees) throws IOException {
		long i = 1;
		long count = currentTrees.size();
		for (Tree<FileableCmisObject> child : currentTrees) {
			boolean isLastElement = (i == count);
			if (child.getItem() instanceof Folder) {
				Folder folder = (Folder) child.getItem();
				dumpWithPath(tabs + startingItem(isLastElement), folder);
				doBrowseTree(tabs + nextTabs(isLastElement), child.getChildren());
			} else {
				dumpWithPath(tabs + startingItem(isLastElement), child.getItem());
			}
			i++;
		}
	}
}
