package Semaphore;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class noSemFifo {
    static Runnable r = new Runnable() {
        private Queue<String> fifo = new LinkedList<String>();
        @Override
        public void run() {
            while(true){
                try{
                    fifo.add("This was put here by: " + Thread.currentThread().getName());
                    System.out.println(fifo.remove() +  "and removed by " + Thread.currentThread().getName());
                    Thread.yield();
                }catch (NoSuchElementException e){
                    e.printStackTrace();
                    break;
                }
            }
        }
    };
}
