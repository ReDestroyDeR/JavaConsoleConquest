package com.red.cmds;

import com.red.Command;
import com.red.Computer;
import com.red.DataManager;
import com.red.Output;

public class Scp extends Command {

    public Scp() {
        super.name = "scp";
        super.description = "download programs from remote host";
        super.commmandKey =  "SCP-137 DECONTAMINAITED hOlY moLLLy".getBytes();
        super.common = true;
    }

    @Override
    public void trigger(String[] args) {
        Output.append(String.format("Connecting to %s\n", args[0]));
        Computer computer = DataManager.findComputer(args[0]);
        if (computer.connect() && computer != null) {
            Output.append(String.format("Connected to %s\n", args[0]));
        } else {
            Output.append(String.format("Failed connecting. Connecting to home PC\n"));
            DataManager.connectedTo = DataManager.playerComputer;
        }
    }

    @Override
    public void trigger() {
        Output.append("Not enough arguments!\n");
    }
}
