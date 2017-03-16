import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 14Ermoshkina on 17.02.2017.
 */
public class Session implements Runnable {
    private Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    ;

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            String message = dataInputStream.readUTF();
            System.out.println(message);

            message = dataInputStream.readUTF();
            while (!message.equals("QUIT")) {
                System.out.println(message);
                message = dataInputStream.readUTF();
            }
            Server.decrementNumberOfSessions();
        } catch (IOException e) {
        }
    }

}
