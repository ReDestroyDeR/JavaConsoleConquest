package com.red.cmds;

import com.red.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Ls extends Command {
    public Ls() {
        super.name = "ls";
        super.description = "View directory contents";
        try {
            super.commmandKey = "DrrrDrrrDrrrRoundDirectoryConentViewer247365".getBytes("UTF-16").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.common = true;
    }

    @Override
    public void trigger(String[] args) {
        Computer computer = DataManager.connectedTo;
        VirtualDirectory checkDir = null;
        if (args[0].equalsIgnoreCase("..")) {
            if (computer.getDirectory() != null) {
                VirtualDirectory current = computer.getDirectory().getParent();
                if (current != null && current.getParent() != null) {
                    checkDir = computer.changeDirectory(current.getParent());
                }
            }
        } else {
            checkDir = computer.changeDirectory(args[0]);
        }

        check(checkDir);
    }

    @Override
    public void trigger() {
        Computer computer = DataManager.connectedTo;
        check(computer.getDirectory());
    }

    private void check(VirtualDirectory target) {
        if (target != null) {
            Output.append(String.format("Contents of directory %s : %s\n",
                    target.getName(), DataManager.connectedTo.ip));
            boolean empty = true;
            for (VirtualFile virtualFile: target.getContents()) {
                if (virtualFile != null) {
                    Output.append(String.format("* %s\n", virtualFile.fileName));
                    empty = false;
                }
            }
            for (VirtualDirectory virtualDirectory: target.getChildren()) {
                if (virtualDirectory != null) {
                    Output.append(String.format("[DIR] %s\n", virtualDirectory.getName()));
                    empty = false;
                }
            }

            if (empty) {
                Output.append("Directory is empty\n");
            }

        } else {
            Output.append(String.format("Contents of directory root : %s\n", DataManager.connectedTo.ip));
            for (VirtualDirectory virtualDirectory: DataManager.connectedTo.fileSystem) {
                if (virtualDirectory != null) {
                    Output.append(String.format("[DIR] %s\n", virtualDirectory.getName()));
                }
            }
        }
    }
}
