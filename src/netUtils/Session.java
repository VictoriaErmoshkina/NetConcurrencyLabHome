package netUtils;

import app.Server;
import java.io.*;
import java.net.Socket;

/**
 * Created by 14Ermoshkina on 17.02.2017.
 */
public class Session implements Runnable {
    private Socket socket;
    private final int id;
    private MessageHandler messageHandler;

    public Session(Socket socket, int id, MessageHandler messageHandler) {
        this.id = id;
        this.socket = socket;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            dataOutputStream.writeUTF("Connection is established.");
            System.out.println(dataInputStream.readUTF() + " by client #" + this.id);

            String messageFromClient = "";
            while (!messageFromClient.equals(":quit")) {
                messageFromClient = dataInputStream.readUTF();
                messageHandler.handle("Message from client #" + this.id + ": " + messageFromClient);
                if (messageFromClient.equals(":cn")) {
                    messageHandler.handle(String.valueOf(Server.getCurrentNumberOfSessions()));
                }
            }
            messageHandler.handle("Client #" + this.id + " is disconnected.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
