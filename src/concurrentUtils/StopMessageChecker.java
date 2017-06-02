package concurrentUtils;

import app.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Виктория on 23.04.2017.
 */
public class StopMessageChecker implements Runnable {
    Socket socket;
    Client client;

    public StopMessageChecker(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        Thread stopMessageThread = new Thread(this);
        stopMessageThread.start();
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String message;
            while (true) {
                message = dataInputStream.readUTF();
                if (message.equals("Application is closed.")) {
                    this.client.stop();
                    break;
                } else if (message.startsWith("re")) {
                    message = dataInputStream.readUTF();
                    System.out.print(message);
                } else if (!message.equals("")) {
                    System.out.println(message);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
