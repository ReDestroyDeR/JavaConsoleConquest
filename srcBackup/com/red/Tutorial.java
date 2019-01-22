package com.red;

import java.util.Random;

public class Tutorial {

    private final String[] protips =
            {
                    "Seek for computers",
                    "Be a pro",
                    "Type fast to look like hacker",
                    "I'm in a gAmE WoooooooooWowWoo",
                    "stupid_punchline.txt",
                    "What have I forgot there?",
                    "Never follow the pro tips",
                    "You can read protip on the protip screen"
            };

    public void tutor() {
        Random random = new Random();
        Output.append("\nWrite help to get the list of commands\n");
        Output.append(String.format("Protip : %s\n", protips[random.nextInt(protips.length)]));
    }
}
