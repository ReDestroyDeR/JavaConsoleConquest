package com.red.cmds;

import com.red.*;

public class Cat extends Command {
    public Cat() {
        super.name = "cat";
        super.description = "View file contents";
        super.commmandKey = "WowOWowoWOowowowoOWOOOOOOOOOOOOWkateWashere".getBytes();
        super.common = true;
    }

    @Override
    public void trigger(String[] args) {
        Computer computer = DataManager.connectedTo;

        VirtualFile target = null;
        for (VirtualFile virtualFile: computer.getDirectory().getContents()) {
            if (virtualFile == null) {
                continue;
            }
            if (virtualFile.fileName.equalsIgnoreCase(args[0])) {
                target = virtualFile;
                break;
            }
        }

        if (target != null) {
            Output.append(String.format("Contents of file %s\n", target.fileName));
            for (String line: target.lines) {
                Output.append(String.format("* %s\n", line));
            }
        } else {
            Output.append(String.format("File %s wasn't found\n", args[0]));
        }
    }

    @Override
    public void trigger() {
        Output.append("Not enough arguments!\n");
    }
}
