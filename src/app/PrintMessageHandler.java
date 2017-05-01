package app;//import netUtils.MessageHandler;

import netUtils.MessageHandler;

/**
 * Created by Виктория on 31.03.2017.
 */
public class PrintMessageHandler implements MessageHandler {
    @Override
    public String handle(String message){
        System.out.println(message);
        return message;
    }
}
