package concurrentUtils;

import java.io.IOException;

/**
 * Created by Виктория on 24.03.2017.
 */
public class WorkerThread implements Runnable {
    private Thread thread;
    private ThreadPool threadPool;
    private Runnable currentTask = null;
    private final Object lock = new Object();
    private boolean isActive;

    public WorkerThread(ThreadPool threadPool) {
        this.threadPool = threadPool;
        this.thread = new Thread(this);
        this.isActive = true;
        thread.start();
    }

    public void execute(Runnable task) {
        synchronized (lock) {
            if (this.currentTask != null)
                throw new IllegalStateException("Worker is busy");
            this.currentTask = task;
            lock.notifyAll();
        }
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (this.isActive) {
                try {
                    while (this.currentTask == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.currentTask.run();
                    this.currentTask = null;
                } catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    return;
                }
                finally {
                    this.threadPool.onTaskCompleted(this);
                }
            }
        }
    }


}
