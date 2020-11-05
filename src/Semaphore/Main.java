package Semaphore;

public class Main {
    public static void main( String[] args){
        new Thread(withSemFifo.r).start();
        new Thread(withSemFifo.r).start();
        //new Thread(MutexSem.r).start();
       
    }
}
