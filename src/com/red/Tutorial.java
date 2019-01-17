package com.red;

public class Tutorial {

    public static boolean active = true;

    public void tutor() {
        Output.append("\nTutorial\n\nHello comrade\n" +
                "You are a TRUE hacker\n" +
                "So now I'll give you some instructions by start\n" +
                "\n" +
                "Right now you do not have any instruments to do your work\n" +
                "So you'll get it by accessing our database server\n" +
                "\n\nWrite anything to continue...\n");

        Output.setPause(true);
        Output.append("\nRight now you can only access basic RIC.OS Commands\n" +
                      "Try writing Help in your terminal\n\n");
        //Input.waitForCommand("help");
    }
}
