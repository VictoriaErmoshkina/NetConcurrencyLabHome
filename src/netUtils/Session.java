package netUtils;

import app.Server;
import concurrentUtils.Stoppable;

import java.io.*;
import java.net.Socket;

/**
 * Created by 14Ermoshkina on 17.02.2017.
 */
public class Session implements Stoppable {
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
            this.messageHandler.setDataOutputStream(dataOutputStream);
            this.messageHandler.setId(this.id);
            String messageFromClient = "";
            while (!messageFromClient.equals(":quit")) {
                messageFromClient = dataInputStream.readUTF();
                messageHandler.handle(messageFromClient);
            }
            messageHandler.disconnect();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() throws IOException {
        if (socket != null) {
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("Application is closed.");

        }
    }
}
