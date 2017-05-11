package concurrentUtils;

import java.util.LinkedList;

/**
 * Created by Виктория on 17.03.2017.
 */
public class Channel {
    private final LinkedList<Stoppable> queue = new LinkedList<>();
    private final int maxCount;
    private final Object lock = new Object();

    public Channel(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getSize() {
        synchronized (lock) {
            return queue.size();
        }
    }

    public void put(Stoppable x) {
        synchronized (lock) {
            while (queue.size() >= maxCount)
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            queue.addLast(x);
            lock.notifyAll();
        }
    }

    public Stoppable take() {
        synchronized (lock) {
            while (queue.isEmpty())
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            lock.notifyAll();
            return queue.removeFirst();
        }
    }

    public LinkedList<Stoppable> getList() {
        return queue;
    }
}
