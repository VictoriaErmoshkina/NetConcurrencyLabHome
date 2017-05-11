package concurrentUtils;

import java.io.IOException;

/**
 * Created by Виктория on 07.04.2017.
 */
public interface Stoppable extends Runnable{
    void stop() throws IOException;
}
