package com.red.cmds;

import com.red.*;

import java.io.UnsupportedEncodingException;

public class PortHack extends Command {
    public PortHack() {
        super.name = "porthack";
        super.description = "Ultimate Hacker Weapon that Used to Break to any open port";
        try {
            super.commmandKey = "ima hacker 228 imaimakinda1337".getBytes("UTF-16").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.common = true;
    }

    @Override
    public void trigger(String[] args) {
        if (args.length < 2) {
            trigger();
        } else {
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            Computer computer = DataManager.findComputer(ip);

            computer.breakThrough(port);
        }
    }

    @Override
    public void trigger() {
        Output.append("Not enough arguments!\n");
    }

}
