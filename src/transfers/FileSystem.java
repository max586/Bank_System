package transfers;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.Iterator;
import java.util.Objects;
import java.util.Vector;

public class FileSystem implements TreeModel {
    private final Vector<TreeModelListener> treeModelListeners = new Vector<>();
    private final File fileRoot;

    public FileSystem(){
        fileRoot = new File(System.getProperty("user.home"));
    }
    @Override
    public Object getRoot() {
        return fileRoot;
    }

    @Override
    public Object getChild(Object parent, int index) {
        File dir = (File) parent;
        String[] children = dir.list();
        assert children != null;
        return new FileElement(dir,children[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        File fl = (File) parent;
        if (fl.isDirectory()) {
            String[] flList = fl.list();
            if (flList != null) return Objects.requireNonNull(fl.list()).length;
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        File fl = (File) node;
        return fl.isFile();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) newValue;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
        Object[] changedChildren = { targetFile };
        TreeModelEvent event = new TreeModelEvent(this, path.getParentPath(), changedChildrenIndices, changedChildren);
        Iterator<TreeModelListener> it = treeModelListeners.iterator();
        TreeModelListener listener = null;
        while (it.hasNext()) {
                listener = it.next();
                listener.treeNodesChanged(event);
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File dir = (File) parent;
        File fl = (File) child;
        String[] children = dir.list();
        int i = 0;
        if(children != null) {
            for (String c : children) {
                if (fl.getName().equals(c)) return i;
                ++i;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }
}

class FileElement extends File{
    public FileElement(File parent, String child) {
        super(parent, child);
    }
    public String toString() {
        return getName();
    }
}
