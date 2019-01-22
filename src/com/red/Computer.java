package com.red;

import com.red.cmds.Cd;
import sun.security.rsa.RSAKeyPairGenerator;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Computer implements Serializable {
    public String ip;
    public VirtualDirectory[] fileSystem;

    private VirtualDirectory directory;
    private String previousFullPath;
    private String currentFullPath;
    private int fireWall;
    private int corporationLevel;
    private int internetSpeed;
    private int cpuPowerLevel;
    private int gpuPowerLevel;
    private int distance;
    private int batch; // To scan for other PC's with the same batch number

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
        } else {
            ports = defaultPortsS;
        }

        // File System Setup
        fileSystem = new VirtualDirectory[2];
        fileSystem[0] = new VirtualDirectory("system", null);
        fileSystem[1] = new VirtualDirectory("home", null);;
        VirtualDirectory programs = new VirtualDirectory("programs", fileSystem[1]);

        VirtualFile systemCore = new VirtualFile(fileSystem[0], "systemCore.sys", new String[]
                {"System Core", "File", "Text"});

        // Default command registration
        for (Command command: DataManager.commands) {
            if (command.common) {
                addNewCommand(command);
            }
        }
    }

    public int getInternetSpeed() {
        return internetSpeed;
    }

    public int getDistance() {
        return distance;
    }

    private void portDefence(int port, int index) {
        if (port != 0) {
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
        } else {
            Output.append("Whoopsie.. Wrong port");
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

    public VirtualDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(VirtualDirectory virtualDirectory) {
        directory = virtualDirectory;
    }

    // Cosemetics
    public String getPreviousFullPath() {
        return previousFullPath;
    }

    public String getCurrentFullPath() {
        return currentFullPath;
    }

    public void setFullPath(String path) {
        previousFullPath = currentFullPath;
        currentFullPath = path;
    }

    // For user input
    public VirtualDirectory changeDirectory(String path) {
        if (path.equalsIgnoreCase("/")) {
            return null;
        } else if (path.endsWith("/")) {
            path = path.substring(0, path.length()-1);
        }
        return accessFileSystem(path);
    }

    // For code input
    public VirtualDirectory changeDirectory(VirtualDirectory parent) {
        String path = String.format("%s", parent.getName());

        while (parent.getParent() != null) {
            path += String.format("%s/", parent.getParent().getName());
            parent = parent.getParent();
        }
        return accessFileSystem(path);
    }

    private VirtualDirectory accessFileSystem(String path) {
        String[] directoryNames = path.split("/");
        VirtualDirectory actualParent = null;

        // Finding algorithm
        for (VirtualDirectory virtualDirectory: fileSystem) {
            if (directoryNames[0].equalsIgnoreCase(virtualDirectory.getName())) {
                actualParent = virtualDirectory;
                for (int passes = 1; passes < directoryNames.length; passes++) {
                    for (VirtualDirectory child: actualParent.getChildren()) {
                        if (directoryNames[passes].equalsIgnoreCase(child.getName())) {
                            actualParent = child;
                            break;
                        }
                    }
                }
                break;
            }
        }
        return actualParent;
    }

    public boolean connect() {
        if (broken) {
            DataManager.connectedTo = this;
            return true;
        } else {
            if (ports != null) {
                int portsN = 0;
                if (fireWall == 0) {
                    portsN = 0;
                }
                else if (fireWall < 5) {
                    portsN = 2;
                }
                else if (fireWall > 10) {
                    portsN = 3;
                }
                else if (fireWall > 20) {
                    portsN = ports.length-1;
                }
                if (portsN == 0) {
                    broken = true;
                    DataManager.connectedTo = this;
                    return true;
                }

                // Port list output here
                Output.append("Can't connect. Port list:\n");
                for (int port: ports) {
                    if (port != 0) {
                        Output.append(String.format("* %s\n", port));
                    } else {
                        Output.append("* Port broken!\n");
                        portsN--;
                    }
                }
            } else {
                broken = true;
                DataManager.connectedTo = this;
                return true;
            }
        }
        return false;
    }

    public void addNewCommand(Command command) { // This is for felling that computers are kinda natural
        byte[] commmandKey = command.commmandKey;
        VirtualDirectory programs = accessFileSystem("home/programs/");
        VirtualDirectory system = accessFileSystem("system/");
        VirtualFile commandFile;
        if (command.common) {
            commandFile = new VirtualFile(system,
                    command.name + ".sh", Arrays.toString(commmandKey).split("\n"));
        } else {
            commandFile = new VirtualFile(programs,
                    command.name + ".sh", Arrays.toString(commmandKey).split("\n"));
        }
    }

}
