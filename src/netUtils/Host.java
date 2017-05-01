package netUtils;


import concurrentUtils.Channel;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Виктория on 31.03.2017.
 */
public class Host implements Runnable {
    private ServerSocket serverSocket;
    private Channel channel;
    MessageHandlerFactory messageHandlerFactory;
    private boolean isActive = true;

    public Host(int port, Channel channel, MessageHandlerFactory messageHandlerFactory) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.channel = channel;
            this.messageHandlerFactory = messageHandlerFactory;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int currentSessionID = 0;
        while (isActive) {
            try {
                Socket socket = this.serverSocket.accept();
                currentSessionID++;
                Session session = new Session(socket, currentSessionID, messageHandlerFactory.createMessageHandler());
                this.channel.put(session);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
