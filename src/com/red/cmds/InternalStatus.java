package com.red.cmds;

import com.red.Command;
import com.red.DataManager;
import com.red.Output;

public class InternalStatus extends Command {

    public InternalStatus() {
        super.name = "status";
        super.description = "Check connection and other statuses";
        super.commmandKey = "USEEMENROLLLLLLLLLwwwwaaing".getBytes();
        super.common = true;
    }

    @Override
    public void trigger() {
        Output.append("Internal status report\n");
        Output.append(String.format("Home-PC: %s\n", DataManager.playerComputer.ip));
        Output.append(String.format("Connected to: %s\n", DataManager.connectedTo.ip));
        Output.append(String.format("Ping: %s\n",
                DataManager.connectedTo.getDistance()/DataManager.playerComputer.getInternetSpeed()*10));
    }
}
