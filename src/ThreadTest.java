/**
 * Created by 14Ermoshkina on 17.02.2017.
 */
public class ThreadTest implements  Runnable {
    private int threadNumber;
    public  ThreadTest(int threadNumb){
        this.threadNumber = threadNumb;
    }
    public void run(){
        for (int i=0;i<5;i++){
            try {
                Thread.sleep(1000);
                System.out.println("thread " + threadNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
