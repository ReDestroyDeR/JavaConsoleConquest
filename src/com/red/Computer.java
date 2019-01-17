package com.red;

import sun.security.rsa.RSAKeyPairGenerator;

import javax.xml.crypto.Data;
import java.util.Random;

public class Computer {
    public String ip;

    private int fireWall;
    private int corporationLevel;
    private int internetSpeed;
    private int cpuPowerLevel;
    private int gpuPowerLevel;
    private int distance;

    private int[] ports;
    private boolean player = false;
    private boolean broken = false;
    //private short[] possiblePorts;

    public Computer(int fireWallS, int corporationLevelS,
                    int internetSpeedS, int cpuPowerLevelS,
                    int gpuPowerLevelS, int distanceS, int[] defaultPortsS) {

        Random random = new Random();
        fireWall = fireWallS;
        corporationLevel = corporationLevelS;
        internetSpeed = internetSpeedS;
        cpuPowerLevel = cpuPowerLevelS;
        gpuPowerLevel = gpuPowerLevelS;
        distance = distanceS;
        ip = String.format("%s.%s.%s.%s", random.nextInt(213)+7, random.nextInt(213)+7,
                                          random.nextInt(213)+7, random.nextInt(213)+7);
        if (defaultPortsS[0] == 0) {
            player = true;
        }
    }

    //public void seekForPorts() {
        // TODO: Port seeking feature... but who cares. I'll do this later, maybe.
    //}

    private int defence() {
        if (fireWall > 0) {
            int rounds = Math.round((float) fireWall / 5);
            int symbolsPerIteration = DataManager.playerComputer.cpuPowerLevel / 1000;


        }
    }

    public String breakThrough(int port) {
        for (int p: ports) {
            if (port == p) {
                //TODO: defence();
            }
        }
        return null;
    }

    public void activateNewCommand(Command command) {
        command.active = true;
    }

}
