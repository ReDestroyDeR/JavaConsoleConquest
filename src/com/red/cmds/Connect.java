package com.red.cmds;

import com.red.Command;
import com.red.Computer;
import com.red.DataManager;
import com.red.Output;

import javax.xml.crypto.Data;

public class Connect extends Command {

    public Connect() {
        super.name = "connect";
        super.description = "connect to the other computer";
        super.commmandKey =  "Internat is my life my dudes some@#@!#!@#@#!@#symbols".getBytes();
        super.common = true;
    }

    @Override
    public void trigger(String[] args) {
        Output.append(String.format("Connecting to %s\n", args[0]));
        Computer computer = DataManager.findComputer(args[0]);
        // Ping simulation
        try {
            Thread.sleep(computer.getDistance()/DataManager.playerComputer.getInternetSpeed()*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (computer.connect()) {
            Output.append(String.format("Connected to %s\n", args[0]));
        } else {
            Output.append(String.format("Failed connecting. Connecting to home PC\n"));
            DataManager.connectedTo = DataManager.playerComputer;
        }
    }

    @Override
    public void trigger() {
        Output.append("No arguments, connecting to Home-PC\n");
        Output.append(String.format("Connecting to %s\n", DataManager.playerComputer.ip));
        // Ping simulation
        try {
            Thread.sleep(DataManager.connectedTo.getDistance()/DataManager.playerComputer.getInternetSpeed()*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataManager.connectedTo = DataManager.playerComputer;

        Output.append(String.format("Connected to %s\n", DataManager.playerComputer.ip));
    }
}
