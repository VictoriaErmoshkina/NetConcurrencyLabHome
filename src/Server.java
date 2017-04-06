/**
 * Created by Виктория on 15.02.2017.
 */

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int currentNumberOfSessions = 0;
    private static final Object lock = new Object();


    public static void decrementNumberOfSessions() {
        synchronized (lock) {
            currentNumberOfSessions--;
            lock.notifyAll();
        }
    }

    public static int getCurrentNumberOfSessions() {
        synchronized (lock) {
            return currentNumberOfSessions;
        }
    }

    private static boolean isParsable(String input) {
        boolean parsable = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Wrong port format! Should be integer.");
            parsable = false;
        }
        return parsable;
    }

    public static void main(String[] args) {
        /* Input: a number of port
        *  To start: java Server port
        *  */
        try {
            final int maxNumberOfSessions = 5;
            int port;
            if (Server.isParsable(args[0])) {
                port = Integer.parseInt(args[0]);
            } else throw new IOException("Incorrect format of port!");
            if (port < 0)
                throw new IOException("Number of a port must be a positive number!");
            else if (port < 1024)
                throw new IOException("Numbers up to 1023 are reserved. Please, try another number.");
            else if (port > 65535)
                throw new IOException("Too big number!");

            int currentSessionID = 0;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(" --- Server is started up --- ");
            while (true) {
                Socket socket = serverSocket.accept();
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                synchronized (lock) {
                    while (currentNumberOfSessions >= maxNumberOfSessions) {
                        dataOutputStream.writeInt(1);
                        dataOutputStream.writeUTF("Number of sessions is too big. Please, wait.");
                        lock.wait();
                    }

                    currentNumberOfSessions++;
                    currentSessionID++;
                    Thread thread = new Thread(new Session(socket, currentSessionID));
                    thread.start();
                    dataOutputStream.writeInt(0);
                    dataOutputStream.writeUTF("Connection is established.");
                }

            }
        } catch (IOException e) {
            System.out.println("Incorrect number of port!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}