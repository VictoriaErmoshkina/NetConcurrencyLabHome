/**
 * Created by Виктория on 15.02.2017.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int currentNumberOfSessions = 0;
    private static final Object lock = new Object();

    public static void decrementNumberOfSessions() {
        synchronized (lock) {
            currentNumberOfSessions--;
        }
    }

    public static void main(String[] args) {
        try {
            int MAX_NUMBER_OF_SESSIONS = 5;
            int port;
            port = Integer.parseInt(args[0]);
            if (port < 0)
                throw new NullPointerException("Number of a port must be a positive number!");
            else if (port < 1024)
                throw new NullPointerException("Numbers up to 1023 are reserved. Please, try another number.");
            else if (port > 65535)
                throw new NullPointerException("Too big number!");

            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                synchronized (lock) {
                    if (currentNumberOfSessions < MAX_NUMBER_OF_SESSIONS) {
                        Thread thread = new Thread(new Session(socket));
                        thread.start();
                        dataOutputStream.writeInt(0);
                        dataOutputStream.writeUTF("Connection is established.");
                        currentNumberOfSessions++;
                    } else {
                        dataOutputStream.writeInt(1);
                        dataOutputStream.writeUTF("Number of sessions is too big. Please, try later.");
                    }
                }
            }
        } catch (IOException e) {
        }

    }
}