package com.red;

import sun.security.rsa.RSAKeyPairGenerator;

import javax.xml.crypto.Data;
import java.util.Arrays;
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

    private void portDefence(int port, int index) {
        int playerSpeed = DataManager.playerComputer.internetSpeed *
                          DataManager.playerComputer.fireWall;
        int ownDefence = internetSpeed*fireWall*cpuPowerLevel;
        int t = ownDefence/playerSpeed;
        boolean fail = false;
        if (t > 60) fail = true;
        for (int i = 0; i < t; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Output.append(String.format("Breaking port %s. Time left - %s", port, t-i));
        }

        if (fail) {
            Output.append("Breakthrough failed. Disconnecting");
        } else {
            Output.append(String.format("Gained access to port %s", port));
            ports[index] = 0;
        }
    }

    private boolean firewallDefence() {
        if (fireWall > 0) {
            boolean fail = false;
            int itterupt = Math.round((float) DataManager.playerComputer.cpuPowerLevel / fireWall);
            if (itterupt > 60) fail = true;
            Output.append(String.format("Firewall detected! Continue in t-%s", itterupt));
            try {
                Thread.sleep(itterupt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (fail) {
                Output.append("Firewall breakthrough is failed. Disconnecting");
                return false;
            } else {
                Output.append("Firewall is down");
                fireWall = 0;
                return true;
            }
        } else {
            Output.append("Firewall is down");
            return true;
        }
    }

    public String breakThrough(int port) {
        for (int i = 0; i < ports.length; i++) {
            if (port == ports[i]) {
                portDefence(port, i);
            }
        }
        return "Wrong port - " + port;
    }
    public boolean connect() {
        if (broken) {
            DataManager.connectedTo = this;
            return true;
        } else {
            if (ports.length != 0) {
                int portsN = ports.length-2;
                int brokenN = 0;
                if (portsN < 0) portsN = 1;
                // Port list output here
                Output.append("Can't connect. Port list:");
                for (int port: ports) {
                    if (port != 0) {
                        Output.append(String.format("* %s", port));
                    } else {
                        Output.append("* Port broken!");
                        brokenN++;
                    }
                }
                if (brokenN >= portsN) {
                    broken = true;
                }
            } else {
                broken = true;
            }
        }
        return false;
    }

    public void activateNewCommand(Command command) {
        command.active = true;
    }

}
