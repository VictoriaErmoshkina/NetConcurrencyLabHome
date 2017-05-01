package concurrentUtils;

import java.io.IOException;

/**
 * Created by Виктория on 17.03.2017.
 */
public class Dispatcher implements Runnable {
    private Channel channel;
    private ThreadPool threadPool;
    private boolean isActive = true;

    public Dispatcher(Channel channel, ThreadPool threadPool) {
        this.threadPool = threadPool;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (isActive) {
            Runnable session = channel.take();
            this.threadPool.execute(session);
        }
    }


}
