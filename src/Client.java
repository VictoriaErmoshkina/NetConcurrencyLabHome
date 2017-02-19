/**
 * Created by Виктория on 15.02.2017.
 */
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            if (port < 0)
                throw new NullPointerException("Number of a port must be a positive number!");
            else if (port < 1024)
                throw new NullPointerException("Numbers up to 1023 are reserved. Please, try another number.");
            else if (port > 65535)
                throw new NullPointerException("Too big number!");

            Socket socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            int connectingType = dataInputStream.readInt();
            if (connectingType == 0){
                System.out.println(dataInputStream.readUTF());

                dataOutputStream.writeUTF("Peace be with you");

                Scanner in = new Scanner(System.in);
                String message = "";
                while (!message.equals("QUIT")){
                    System.out.print("Message: ");
                    message = in.nextLine();
                    dataOutputStream.writeUTF(message);
                }
            }
            else
                System.out.println(dataInputStream.readUTF());
        }
        catch (IOException e) {}
    }
}
