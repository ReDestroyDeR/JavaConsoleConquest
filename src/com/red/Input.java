package com.red;

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
    }

    private Scanner in = new Scanner(System.in);

    @SuppressWarnings("InfiniteRecursion")
    private void getInput() {
        lastInput = in.nextLine();

        // Command handling mechanism
        for (int i = 0; i < DataManager.commands.size(); i++) {
            Command c = DataManager.commands.get(i);
            if (!c.active) continue;
            if (c.name.equalsIgnoreCase(lastInput.toLowerCase())) {
                c.trigger();
                break;
            }
        }

        if (Output.getPause()) {
            Output.setPause(false);
        }
        getInput(); // Recursion
    }
}
