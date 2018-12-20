package com.red;

import java.util.Scanner;

/**
 * Created by student5 on 20.12.18.
 */
public class Input {

    private static Scanner scanner = new Scanner(System.in);

    public static String getInput() {
        System.out.print("* ");
        return scanner.next();
    }
}
