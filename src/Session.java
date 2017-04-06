import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 14Ermoshkina on 17.02.2017.
 */
public class Session implements Runnable {
    private Socket socket;
    private final int id;

    public Session(Socket socket, int id) {
        this.id = id;
        this.socket = socket;
    }

    ;


    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            System.out.println(dataInputStream.readUTF());

            String message = "";
            while (!message.equals(":quit")) {
                message = dataInputStream.readUTF();
                System.out.println("Message from client #" + id + ": " + message);
                if (message.equals(":cn")) {
                    System.out.println(Server.getCurrentNumberOfSessions());
                }
            }
            System.out.println("Client #" + id + " is disconnected.");
            Server.decrementNumberOfSessions();

        } catch (IOException e) {

        }
    }
}
