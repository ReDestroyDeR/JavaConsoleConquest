package com.red.cmds;

import com.red.Command;

public class PortHack extends Command {
    public PortHack() {
        super.name = "porthack";
        super.description = "Ultimate Hacker Weapon that Used to Break to any open port";
        super.commmandKey = "ima hacker 228 imaimakinda1337".getBytes();
        super.common = false;
    }

}
