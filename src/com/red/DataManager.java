package com.red;

import javax.naming.spi.DirectoryManager;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class DataManager extends Thread {
    public static ArrayList<Command> commands = new ArrayList<>();
    public static ArrayList<Computer> computers = new ArrayList<>();
    public static Computer playerComputer;
    public static Computer connectedTo;


    private static String directoryPath;
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

    public static Object[] returnData(SerializableType type) {
        try {
            FileInputStream fileInputStream = new FileInputStream(directoryPath + "/"
                                                                  + type.toString() + ".dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object[] objects = new Object[1024];
            for (int i = 0; i < objects.length; i++) {
                try {
                    Object object = objectInputStream.readObject();
                    objects[i] = object;
                } catch (EOFException e) {
                    break;
                }
            }
            objectInputStream.close();
            return objects;
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void writeData(Object[] objects, SerializableType type) {
        String path = directoryPath + "/" + type.toString() + ".dat";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Object object: objects) {
                objectOutputStream.writeObject(object);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataManager(String path) {
        directoryPath = path;
        File file = new File(path);
        if (!file.exists()) file.mkdir();
    }

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public static Computer findComputer(String ip) {
        Computer toReturn = null;
        for (Computer pc: computers) {
            if (ip.equalsIgnoreCase(pc.ip)) {
                toReturn = pc;
                break;
            }
        }
        return toReturn;
    }

    private static int newPort(Random random, int[] ports) {
        int x = allPorts[random.nextInt(allPorts.length-1)];
        boolean contains = false;
        for (int port: ports) {
            if (x == port) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            return x;
        } else {
            return newPort(random, ports);
        }
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
            int speed = 0;
            if (cl != 0) speed = random.nextInt(speedMax*cl)+speedMin;
            else speed = random.nextInt(speedMax)+speedMin;

            int length = random.nextInt(portsN)+1;
            int[] ports = new int[length];
            for (int port = 0; port < length; port++) {
                ports[port] = newPort(random, ports);
            }

            Computer computer = new Computer(fw, cl, speed, cpu, gpu, distance+delta, ports);
            computers.add(computer);
            toReturn[i] = computer;
        }

        return toReturn;
    }

    public static Computer[] generateComputers(ComputerType type, int n, String customDataString) {
        Random random = new Random();
        switch (type) {
            case CORPORATION:
                return generateSolo(1, 99, 10, n,
                        10000, 10000, 1000, 500, 1000, 500,
                        5);

            case DISTRICT:
                return generateSolo(0, 5, 5, n,
                        6000, 6000, 500, 250, 500, 50,
                        3);

            case GOVERNMENT:
                return generateSolo(50, 249, 100, n,
                        20000, 5000, 7000, 500, 10000, 1000,
                        6);

            case HACKER:
                return generateSolo(50, 249, 100, n,
                        7000, 4000, 7000, 100, 2000, 1000,
                        7);

            default: // Custom
                String[] customData = customDataString.split(";");
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
