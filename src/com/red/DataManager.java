package com.red;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DataManager {
    public static ArrayList<Command> commands = new ArrayList<>();
    public static ArrayList<Computer> computers = new ArrayList<>();
    public static Computer playerComputer;
    public static Computer connectedTo;
    private String path;
    private final static int[] allPorts =
            {
                    20, // FTP
                    22, // SSH
                    25, // SMTP
                    80, // HTTP
                    81, // HTTP
                    115, // SFTP
                    443, // HTTPS
                    4899, // SQL
                    5432, // SQL
                    8080 // HTTP_alt
            };

    public DataManager(String Gpath) {
        path = Gpath;
    }

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public static Computer findComputer(String ip) {
        for (Computer pc: computers) {
            if (ip == pc.ip) {
                return pc;
            }
        }
        return playerComputer;
    }

    private static Computer[] generateSolo(int clMin, int clMax, int fwMax, int n,
                                           int cpuMax, int gpuMax, int cpuMin, int gpuMin,
                                           int speedMax, int speedMin, int portsN) {
        Random random = new Random();
        int distance = random.nextInt(1000000)+1000;
        int cl = random.nextInt(clMax)+clMin;
        int fw = random.nextInt(fwMax)+cl*2;
        Computer[] toReturn = new Computer[n];

        for (int i = 0; i < n; i++) {
            int cpu = random.nextInt(cpuMax)+cl*10+cpuMin;
            int gpu = random.nextInt(gpuMax)+cl*10+gpuMin;
            boolean minusDelta = random.nextBoolean();
            int delta = random.nextInt(1000);
            if (minusDelta) delta = -delta;
            int speed = random.nextInt(speedMax*cl)+speedMin;
            int[] ports = new int[random.nextInt(portsN)+1];
            System.arraycopy(allPorts, 0, ports, 0, ports.length);

            Computer computer = new Computer(fw, cl, speed, cpu, gpu, distance+delta, ports);
            computers.add(computer);
            toReturn[i] = computer;
        }

        return toReturn;
    }

    public static Computer[] generateComputers(String type, int n) {
        Random random = new Random();
        switch (type) {
            case "Corporation":
                return generateSolo(1, 99, 10, random.nextInt(7)+3,
                        10000, 10000, 1000, 500, 1000, 500,
                        random.nextInt(5)+4);

            case "District":
                return generateSolo(0, 5, 5, random.nextInt(7)+3,
                        6000, 6000, 500, 250, 500, 50,
                        random.nextInt(3)+2);

            case "Government":
                return generateSolo(50, 249, 100, random.nextInt(1)+2,
                        20000, 5000, 7000, 500, 10000, 1000,
                        random.nextInt(3)+1);

            case "Hacker":
                return generateSolo(50, 249, 100, 1,
                        7000, 4000, 7000, 100, 2000, 1000,
                        random.nextInt(3)+1);

            default: // Custom
                String[] customData = type.split(";");
                int fw = Integer.parseInt(customData[0]);
                int cl = Integer.parseInt(customData[1]);
                int speed = Integer.parseInt(customData[2]);
                int cpu = Integer.parseInt(customData[3]);
                int gpu = Integer.parseInt(customData[4]);
                int distance = Integer.parseInt(customData[5]);
                int[] ports = new int[100];
                if (n == -1) {
                    ports[0] = 0;
                } else {
                    for (int c = 6; c < customData.length; c++) {
                        ports[c - 6] = Short.parseShort(customData[c]);
                    }
                }

                Computer computer = new Computer(fw, cl, speed, cpu, gpu, distance, ports);
                computers.add(computer);
                return new Computer[] {computer};
        }
    }


}
