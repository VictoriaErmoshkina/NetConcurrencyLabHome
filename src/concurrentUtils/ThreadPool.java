package concurrentUtils;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Виктория on 24.03.2017.
 */
public class ThreadPool {
    private int maxSize;
    private LinkedList<Stoppable> allWorkers = new LinkedList<>();
    private Channel freeWorkers;

    public ThreadPool(int maxSize) {
        this.maxSize = maxSize;
        this.freeWorkers = new Channel(maxSize);
        WorkerThread workerThread = new WorkerThread(this);
        allWorkers.add(workerThread);
        freeWorkers.put(workerThread);
    }

    public synchronized void execute(Stoppable task) {
        if (freeWorkers.getSize() == 0 && allWorkers.size() < maxSize) {
            WorkerThread workerThread = new WorkerThread(this);
            allWorkers.addLast(workerThread);
            freeWorkers.put(workerThread);
        }
        ((WorkerThread) freeWorkers.take()).execute(task);
    }

    public void onTaskCompleted(WorkerThread workerThread) {
        freeWorkers.put(workerThread);
    }

    public synchronized boolean isBusy() {
        if (freeWorkers.getSize() == 0 && allWorkers.size() == maxSize)
            return true;
        return false;
    }

    public synchronized int getCurrentNumberOfSessions() {
        return allWorkers.size() - freeWorkers.getSize();
    }

    public void stop() throws IOException {
        for (Stoppable worker : allWorkers) {
            worker.stop();
        }
    }
}
