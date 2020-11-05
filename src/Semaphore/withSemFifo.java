package Semaphore;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class withSemFifo {
    static Semaphore mutex = new Semaphore(1,true);
    static Runnable r = new Runnable() {
        private Queue<String> fifo = new LinkedList<String>();
        @Override
        public void run() {
            while(true){
                try{
                    mutex.acquire();
                    fifo.add("This was put here by: " + Thread.currentThread().getName());
                    System.out.println(fifo.remove() +  " and removed by " + Thread.currentThread().getName());
                    Thread.yield();
                    mutex.release();
                }catch (NoSuchElementException e){
                    e.printStackTrace();
                    break;
                }catch (InterruptedException e){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
    };
}
