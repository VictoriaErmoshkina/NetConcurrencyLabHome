/**
 * Created by 14Ermoshkina on 17.02.2017.
 */
public class SomeClass {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ThreadTest(1));
        Thread thread2 = new Thread(new ThreadTest(2));
        Thread thread3 = new Thread(new ThreadTest(3));
        Thread thread4 = new Thread(new ThreadTest(4));
        Thread thread5 = new Thread(new ThreadTest(5));
        Thread thread6 = new Thread(new ThreadTest(6));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
    }
}
