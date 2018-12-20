package com.red;

import javax.naming.spi.DirectoryManager;
import javax.swing.*;
import java.io.File;

/**
 * Created by student5 on 20.12.18.
 */
public class General {

    public static String path;

    public static void main(String[] args) {
        System.out.println("Select game output directory");
        do {
            path = directoryPrompt();
        } while (path == null);
        path += "/RicardoConquest/";
        try {
            File f = new File(path);
            boolean b = f.mkdir();
            if (!b) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String directoryPrompt() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(new JPanel()) == fc.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

}
