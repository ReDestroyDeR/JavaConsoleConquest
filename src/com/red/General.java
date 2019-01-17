package com.red;

import com.red.cmds.Connect;
import com.red.cmds.Help;

import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import java.io.File;

public class General {

    public static String path;
    public static Input input;
    public static Output output;

    public static void main(String[] args) {
        // Directory selection
        System.out.println("Select game output directory");
        do {
            path = directoryPrompt();
        } while (path == null);
        path += "/RicardoConquest/";
        try {
            File f = new File(path);
            if (!f.exists()) {
                boolean b = f.mkdir();
                if (!b) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // I/O Initialization
        input = new Input();
        output = new Output();
        input.start();
        output.start();

        // Data initialization
        DataManager dm = new DataManager(path);

        Help help = new Help();
        Connect connect = new Connect();

        dm.registerCommand(help);
        dm.registerCommand(connect);

        // Tutorial initialization
        Tutorial t = new Tutorial();
        t.tutor();
    }

    private static String directoryPrompt() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

}
