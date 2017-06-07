package app;

import netUtils.MessageHandler;
import netUtils.MessageHandlerFactory;

/**
 * Created by Виктория on 31.03.2017.
 */
public class CmdHandlerFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler createMessageHandler(){
        return new CmdHandler();
    }
}
