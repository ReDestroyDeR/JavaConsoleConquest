package com.red;

import java.io.Serializable;

public class VirtualFile implements Serializable {
    public VirtualDirectory path;
    public String fileName;
    public String[] lines;

    public VirtualFile(VirtualDirectory parent, String name, String[] contents) {
        path = parent;
        fileName = name;
        lines = contents;
        parent.add(this);
    }
}
