package com.red;

public class Output implements Runnable {

    private static boolean pause = false;
    private static StringBuffer buffer;
    private static StringBuffer bufferQueue;

    public Output() {
        System.out.println("Output object has been created");
    }

    private static Thread thread; // Check for active thread

    public void start() {
        if (thread == null) { // Actual check is here
            thread = new Thread(this);
            thread.start();
        }
    }

    public static void append(String string) {
        if (!pause) {
            buffer.append(string);
        } else {
            bufferQueue.append(string);
        }
    }

    public static void setPause(boolean pausel) {
        pause = pausel;
        if (!pause) {
            buffer.append(bufferQueue);
            bufferQueue.delete(0, bufferQueue.length());
        }
    }

    public static boolean getPause() {
        return pause;
    }

    @Override
    public void run() {
        System.out.println("Output thread has been created");
        buffer = new StringBuffer(2048);
        bufferQueue = new StringBuffer(2048);
        System.out.println("Output Buffers initialized");
        startSending();
    }

    @SuppressWarnings("InfiniteRecursion")
    private void startSending() {
        if (buffer.length() == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startSending();
        }

        int previous = 0;
        for (int cursor = 0; cursor < buffer.length(); cursor++) {
            if (buffer.charAt(cursor) == '\n') {
                System.out.print(buffer.substring(previous, cursor));
                previous = cursor++;
            }
        }

        // Cleaning
        System.out.println();
        buffer.delete(0, buffer.length());
        startSending();
    }
}
