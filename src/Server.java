/**
 * Created by Виктория on 15.02.2017.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            int port;
            port = Integer.parseInt(args[0]);
            if (port < 0)
                throw new NullPointerException("Number of a port must be a positive number!");
            else if (port < 1024)
                throw new NullPointerException("Numbers up to 1023 are reserved. Please, try another number.");
            else if (port > 65535)
                throw new NullPointerException("Too big number!");

            ServerSocket serverSocket = new ServerSocket(port);

            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            String message = dataInputStream.readUTF();
            System.out.println(message);

            message = dataInputStream.readUTF();
            while (!message.equals("QUIT")){
                System.out.println(message);
                message = dataInputStream.readUTF();
            }

        } catch (IOException e) {}
    }
}