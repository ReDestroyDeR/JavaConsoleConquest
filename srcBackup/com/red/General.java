package com.red;

import com.red.cmds.*;

import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class General {

    public static String path;
    public static Input input;
    public static Output output;

    public static void main(String[] args) {
        // Directory selection
        System.out.print("Initializing game directory");
        do {
            System.out.print(" .");
            try {
                System.out.print(".");
                path = General.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            } catch (URISyntaxException e) {
                System.out.print(" FAILED!");
                e.printStackTrace();
                System.exit(400);
            }
        } while (path == null);
        System.out.println(". DONE!");
        path += "/RicardoConquest/";

        // I/O Initialization
        input = new Input();
        output = new Output();
        input.start();
        output.start();

        // Data initialization
        DataManager dm = new DataManager(path);

        Help help = new Help();
        Connect connect = new Connect();
        Scp scp = new Scp();
        Cd cd = new Cd();
        Cat cat = new Cat();
        Ls ls = new Ls();
        InternalStatus internalStatus = new InternalStatus();

        dm.registerCommand(help);
        dm.registerCommand(connect);
        dm.registerCommand(cd);
        dm.registerCommand(cat);
        dm.registerCommand(scp);
        dm.registerCommand(ls);
        dm.registerCommand(internalStatus);

        // Tutorial initialization
        DataManager.playerComputer = DataManager.generateComputers(ComputerType.CUSTOM, -1, "100;100;5000;50000;50000;0;")[0];
        DataManager.connectedTo = DataManager.playerComputer;
        DataManager.writeData(new Object[] {DataManager.playerComputer}, SerializableType.computer);
        DataManager.writeData(DataManager.generateComputers(ComputerType.DISTRICT, 1, ""), SerializableType.computer);
        Output.append(((Computer) Objects.requireNonNull(DataManager.returnData(SerializableType.computer))[0]).ip + "\n");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Tutorial t = new Tutorial();
        t.tutor();
    }

}
