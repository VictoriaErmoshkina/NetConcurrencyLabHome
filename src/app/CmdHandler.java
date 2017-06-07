package app;//import netUtils.MessageHandler;

import netUtils.FileCmd;
import netUtils.MessageHandler;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Виктория on 31.03.2017.
 */
public class CmdHandler implements MessageHandler {
    FileCmd fileCmd;
    int id;
    @Override
    public String handle(String message) {
            System.out.println("Message from client #" + this.id + ": " + message);
            if (message.equals(":cn")) {
                System.out.println(String.valueOf(Server.getCurrentNumberOfSessions()));
            }
            try {
                fileCmd.command(message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        return message;
    }
    @Override
    public void disconnect(){
        System.out.println("Client #" + this.id + " is disconnected.");
    }

    @Override
    public void setDataOutputStream(DataOutputStream dataOutputStream){
        this.fileCmd = new FileCmd(dataOutputStream);
    }

    @Override
    public void setId(int id){
        this.id = id;
    }

}
