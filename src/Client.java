/**
 * Created by Виктория on 15.02.2017.
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

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
        /* Input: host, port
        *  To start: java Client host port
        * */
        try {
            String host = args[0];
            int port;
            if (isParsable(args[1])) {
                port = Integer.parseInt(args[1]);
            } else throw new IOException("Incorrect format of port!");
            if (port < 0)
                throw new IOException("Number of a port must be a positive number!");
            else if (port < 1024)
                throw new IOException("Numbers up to 1023 are reserved. Please, try another number.");
            else if (port > 65535)
                throw new IOException("Too big number!");

            Socket socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            int connectingType = dataInputStream.readInt();
            String connectingMessage = dataInputStream.readUTF();

            if (connectingType == 1) {
                System.out.println(connectingMessage);
            }
            while (connectingType != 0) {
                connectingType = dataInputStream.readInt();
                connectingMessage = dataInputStream.readUTF();
            }

            System.out.println(connectingMessage);
            dataOutputStream.writeUTF("Peace be with you");

            Scanner in = new Scanner(System.in);
            String message = "";

            while (!message.equals(":quit")) {
                System.out.print("Message: ");
                message = in.nextLine();
                dataOutputStream.writeUTF(message);
            }


        } catch (IOException e) {
            System.out.println("Incorrect number of port!");
        }
    }
}
