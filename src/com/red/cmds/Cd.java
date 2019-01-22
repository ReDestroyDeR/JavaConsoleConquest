package com.red.cmds;

import com.red.*;

import java.io.UnsupportedEncodingException;

public class Cd extends Command {
    public Cd() {
        super.name = "cd";
        super.description = "Change current directory";
        try {
            super.commmandKey = "RTELPRTetpeRPEOTETEOTOEROEOROETOPLFEFteleport".getBytes("UTF-16").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.common = true;
    }

    @Override
    public void trigger(String[] args) {
        Computer computer = DataManager.connectedTo;
        VirtualDirectory changeTo = null;

        if (args[0].equalsIgnoreCase("..")) {
            VirtualDirectory parent = computer.getDirectory();
            if (parent != null && parent.getParent() != null) {
                changeTo = computer.changeDirectory(parent.getParent());
            }
        } else {
            changeTo = computer.changeDirectory(args[0]);
        }

        StringBuilder path = new StringBuilder();
        for (String arg: args) {
            path.append(arg).append("/");
        }
        if (changeTo != null) {
            computer.setFullPath(path.toString());
        } else {
            computer.setFullPath(null);
        }

        computer.setDirectory(changeTo);
        String previous = computer.getPreviousFullPath();
        String current = computer.getCurrentFullPath();
        if (previous == null || previous.equalsIgnoreCase("..")) {
            previous = "root";
        }
        if (current == null || current.equalsIgnoreCase("..")) {
            current = "root";
        }
        Output.append(String.format("%s -> %s\n", previous, current));
    }

    @Override
    public void trigger() {
        Output.append("Not enough arguments!\n");
    }
}
