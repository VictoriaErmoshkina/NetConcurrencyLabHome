package concurrentUtils;

import java.io.IOException;

/**
 * Created by Виктория on 17.03.2017.
 */
public class Dispatcher implements Stoppable {
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
            Stoppable session = channel.take();
            this.threadPool.execute(session);
        }
    }

    @Override
    public void stop() throws IOException {
        isActive = false;
        //stop sessions in channel
        for (Stoppable session : channel.getList()) {
            session.stop();
        }
    }
}
