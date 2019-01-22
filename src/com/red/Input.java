package com.red;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

public class Input implements Runnable {

    public static String lastInput;

    public Input() {
        System.out.println("Input object has been created");
        lastInput = "";
    }

    private static Thread thread; // Check for active thread

    public void start() {
        if (thread == null) { // Actual check is here
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Input thread has been created");
        getInput();
        try {
            // KOSTIL found! Crashes if first input is empty.
            System.setIn(new ByteArrayInputStream("Anti crash text".getBytes("UTF-8")));
            System.setIn(System.in);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Scanner in = new Scanner(System.in);

    @SuppressWarnings("InfiniteRecursion")
    private void getInput() {
        lastInput = in.nextLine();
        String[] args = lastInput.split(" ");
        if (args.length == 0) {
            return;
        }

        // Command handling mechanism
        VirtualDirectory system = DataManager.playerComputer.changeDirectory("system/");
        VirtualDirectory programs = DataManager.playerComputer.changeDirectory("home/programs");

        String commandCode;
        for (Command command: DataManager.commands) {
            if (command.name.equalsIgnoreCase(args[0])) {
                commandCode = command.commmandKey;
                boolean found = false;
                for (VirtualFile virtualFile: system.getContents()) {
                    if (virtualFile == null) {
                        continue;
                    }
                    String[] lines = commandCode.split("\n");
                    for (int i = 0; i < virtualFile.lines.length; i++) {
                        if (virtualFile.lines[i].equalsIgnoreCase(lines[i])) {
                            found = true;
                        } else {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (!found) {
                    for (VirtualFile virtualFile: programs.getContents()) {
                        if (virtualFile == null) {
                            continue;
                        }
                        String[] lines = commandCode.split("\n");
                        for (int i = 0; i < virtualFile.lines.length; i++) {
                            if (virtualFile.lines[i].equalsIgnoreCase(lines[i])) {
                                found = true;
                            } else {
                                found = false;
                                break;
                            }
                        }
                    }
                }
                if (found) {

                    // Ping simulation
                    if (!command.name.equalsIgnoreCase("connect")) {
                        int ping = DataManager.connectedTo.getDistance()/DataManager.playerComputer.getInternetSpeed()*10;
                        try {
                            Thread.sleep(ping);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (args.length > 1) {
                        String[] realArgs = new String[args.length-1];
                        System.arraycopy(args, 1, realArgs, 0, args.length-1);
                        command.trigger(realArgs);
                    } else {
                        command.trigger();
                    }
                    break;
                }
            }
        }



        if (Output.getPause()) {
            Output.setPause(false);
        }
        getInput(); // Recursion
    }
}
