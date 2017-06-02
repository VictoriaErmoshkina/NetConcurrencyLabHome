package app;

import netUtils.MessageHandler;

import java.io.DataOutputStream;

/**
 * Created by Виктория on 02.06.2017.
 */
public class PrintMessageHandler implements MessageHandler {
    int id;
    DataOutputStream dataOutputStream;
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public String handle(String message) {
        System.out.println("Message from client #" + this.id + ": " + message);
       return message;
    }

    @Override
    public void disconnect() {
        System.out.println("Client #" + this.id + " is disconnected.");
    }
}
