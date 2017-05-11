package app; /**
 * Created by Виктория on 15.02.2017.
 */

import concurrentUtils.StopMessageChecker;
import java.io.*;
import java.net.Socket;

public class Client {
    private String hostname;
    private int port;
    private  boolean isActive = true;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
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

    public synchronized void stop() {
        this.isActive = false;
    }

    public void run(){
        try {
            Socket socket = new Socket(this.hostname, this.port);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String connectingMessage = dataInputStream.readUTF();
            System.out.println(connectingMessage);
            dataOutputStream.writeUTF("Peace be with you");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message = "";
            StopMessageChecker stopMessageChecker = new StopMessageChecker(socket, this);
            System.out.print("Message: ");
            while (!message.equals(":quit")) {
                if (this.isActive) {
                    if (reader.ready()) {
                        message = reader.readLine();
                        if (this.isActive) {
                            dataOutputStream.writeUTF(message);
                            System.out.print("Message: ");
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("...");
                    break;
                }
            }
            System.out.println("Application is closed.");
        } catch (IOException e){
            System.out.println(e.getMessage());
            this.stop();
        }
    }

    public static void main(String[] args) {
        /* Input: <host>, <port>
        *  To start: java app.Client <host> <port>
        * */
        try {
            String hostname = args[0];
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
            System.out.println("Connecting to Server...");
            Client client = new Client(hostname, port);
            client.run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
