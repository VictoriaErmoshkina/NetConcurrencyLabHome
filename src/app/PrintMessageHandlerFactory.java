package app;

import netUtils.MessageHandler;
import netUtils.MessageHandlerFactory;

/**
 * Created by Виктория on 31.03.2017.
 */
public class PrintMessageHandlerFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler createMessageHandler(){
        return new PrintMessageHandler();
    }
}
