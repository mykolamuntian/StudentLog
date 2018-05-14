package studentlog.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;

public class StudentsGroup extends TreeItem {
	
	private String name;
	
	private String parent;
	
//	private List<TreeItem> children;
	
	transient private ListenerList listeners;
	
//	private String fileName = "students.txt";

	public StudentsGroup(String parent, String name) {
		this.parent = parent;
		this.name = name;
//		initialStudentsGroup(fileName);
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getParent() {
		return parent;
	}

	public void rename(String newName) {
		this.name = newName;
		fireStudentsChanged(null);
	}

	public void addEntry(TreeItem childe) {
		TreeModel.getInstance().getTreeItemsMap().get(parent).add(childe);
	}

	public void removeEntry(TreeItem childe) {
		TreeModel.getInstance().getTreeItemsMap().get(parent).remove(childe);
		fireStudentsChanged(null);
	}

	public List<TreeItem> getChildren() {
		return TreeModel.getInstance().getTreeItemsMap().get(this.getName());
	}
	
	public void addStudentsListener(IStudentsListener listener) {
		
		if (parent != null) {
			TreeModel.getInstance().getTreeItemsMap().get(parent).addStudentsListener(listener);
		}
		else {
			if (listeners == null)
				listeners = new ListenerList();
			listeners.add(listener);
		}
	}
	
	public void removeStudentsListener(IStudentsListener listener) {
		if (parent != null) {
			parent.removeStudentsListener(listener);
		}
		else {
			if (listeners != null) {
				listeners.remove(listener);
				if (listeners.isEmpty())
					listeners = null;
			}
		}
	}

	protected void fireStudentsChanged(StudentsEntry entry) {
		if (parent != null) {
			parent.fireStudentsChanged(entry);
		} else {
			if (listeners == null) {
				return;
			}
			Object[] rls = listeners.getListeners();
			for (int i = 0; i < rls.length; i++) {
				IStudentsListener listener = (IStudentsListener) rls[i];
				listener.studentsChanged(this, entry);
			}
		}
	}
	
	
//	public void initParent() {
//		if(this.getChildren().size()>0) {
//			StudentsGroup parent = this;
//			for(TreeItem treeItem:parent.getChildren()) {
//				if(treeItem instanceof StudentsGroup) {
//					((StudentsGroup) treeItem).initParent();
//				}
//				treeItem.setParent(parent);
//			}
//		}
//	}
//	private void initialStudentsGroup(String fileName) {
//		
//		
//	}
	
//	public void setEntries(List<Student> entries) {
//		this.entries = entries;
//	}
}
