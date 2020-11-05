package Semaphore;

import java.util.concurrent.Semaphore;

public class MutexSem {
    
    static Semaphore mutex = new Semaphore(1, true);
    static Runnable r = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName() + "_im_k.A.");
                    Thread.sleep(3000);
                    mutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
