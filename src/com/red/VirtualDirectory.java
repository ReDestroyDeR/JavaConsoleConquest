package com.red;

import java.io.Serializable;
import java.util.Arrays;

public class VirtualDirectory implements Serializable {

    private final int limit = 255;
    public final String alphabet = "0 1 2 3 4 5 6 7 8 9 " +
                                   "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z " +
                                   "a b c d e f g h i k k l m n o p q r s t u v w x y z";

    private String name;
    private VirtualFile[] contents;

    private VirtualDirectory parent;
    private VirtualDirectory[] children;

    public VirtualDirectory(String dirName, VirtualDirectory dirPlace) {
        if (dirPlace != null) {
            name = dirName;
        } else {
            name = dirName;
        }
        parent = dirPlace;
        children = new VirtualDirectory[limit];
        contents = new VirtualFile[limit];
        if (parent != null) {
            parent.addChildren(this);
        }
    }

    public void addChildren(VirtualDirectory vdir) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] == null) {
                children[i] = vdir;
                return;
            }
        }
    }

    public void removeChildren(VirtualDirectory vdir) {
        for (int i = 0; i < children.length; i++) {
            if (children[i].equals(vdir)) {
                children[i] = null;
                return;
            }
        }
    }

    public boolean add(VirtualFile vfile) {
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null) {
                contents[i] = vfile;
                break;
            }
        }
        return Arrays.asList(contents).contains(vfile);
    }

    public boolean remove(VirtualFile vfile) {
        for (int i = 0; i < contents.length; i++) {
            if (vfile.equals(contents[i])) {
                contents[i] = null;
                break;
            }
        }
        return !Arrays.asList(contents).contains(vfile);
    }

    public VirtualFile[] getContents() {
        return contents;
    }

    public String getName() {
        return name;
    }

    public VirtualDirectory getParent() {
        return parent;
    }

    public VirtualDirectory[] getChildren() {
        return children;
    }

}
