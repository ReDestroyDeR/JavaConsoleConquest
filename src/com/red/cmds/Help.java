package com.red.cmds;

import com.red.Command;
import com.red.DataManager;
import com.red.Output;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Random;

public class Help extends Command {

    public Help() {
        super.name = "help";
        super.description = "sample";
        try {
            super.commmandKey =  "It's help command It's meant to be help Some more stupid data".getBytes("UTF-16").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.common = true;
    }

    @Override
    public void trigger() {
        Output.append("availiable commands\n");
        for (int i = 0; i < DataManager.commands.size(); i++) {
            Command cmd = DataManager.commands.get(i);
            Output.append(String.format("* %s - %s\n", cmd.name, cmd.description));
        }
    }
}
