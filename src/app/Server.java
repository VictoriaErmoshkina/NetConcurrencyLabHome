package app; /**
 * Created by Виктория on 15.02.2017.
 */

import concurrentUtils.Channel;
import concurrentUtils.Dispatcher;
import concurrentUtils.Stoppable;
import concurrentUtils.ThreadPool;
import netUtils.Host;
import netUtils.MessageHandlerFactory;

import java.io.*;

public class Server {
    private static final int maxNumberOfSessions = 6;
    private static ThreadPool threadPool = new ThreadPool(maxNumberOfSessions);

    public static int getCurrentNumberOfSessions() {
        return threadPool.getCurrentNumberOfSessions();
    }

    public static boolean isBusy() {
        return threadPool.isBusy();
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

    public static void main(String[] args) {
        /* Input: a number of port
        *  To start: java Server <port>
        *  */
        try {
            int port;
            if (Server.isParsable(args[0])) {
                port = Integer.parseInt(args[0]);
            } else throw new IOException("Incorrect format of port!");
            if (port < 0)
                throw new IOException("Number of a port must be a positive number!");
            else if (port < 1024)
                throw new IOException("Numbers up to 1023 are reserved. Please, try another number.");
            else if (port > 65535)
                throw new IOException("Too big number!");

            Channel channel = new Channel(maxNumberOfSessions / 2);
            Stoppable dispatcher = new Dispatcher(channel, Server.threadPool);
            Thread dispatcherThread = new Thread(dispatcher);
            MessageHandlerFactory messageHandlerFactory = new PrintMessageHandlerFactory();
            Stoppable host = new Host(port, channel, messageHandlerFactory);
            Thread hostThread = new Thread(host);

            //Обработка внезапной остановки приложения
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    host.stop();
                    dispatcher.stop();
                    Server.threadPool.stop();
                    System.out.println("Server is closed.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            hostThread.start();
            dispatcherThread.start();
            System.out.println(" --- Server is started up --- ");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}