package com.red.cmds;

import com.red.Command;
import com.red.Computer;
import com.red.DataManager;
import com.red.Output;

public class Connect extends Command {

    public Connect() {
        super.name = "connect [ip]";
        super.description = "connect to the other computer";
        super.active = true;
    }

    @Override
    public void trigger(String[] args) {
        Output.append("Connecting to " + args[0]);
        Computer computer = DataManager.findComputer(args[0]);
        if (computer.connect()) {
            Output.append(String.format("Connected to %s", args[0]));
        } else {
            Output.append(String.format("Failed connecting. Connecting to home PC"));
            DataManager.connectedTo = DataManager.playerComputer;
        }
    }
}
