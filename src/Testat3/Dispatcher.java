package Testat3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//starts workerThreads, passes Tasks to them
public class Dispatcher{
    private BlockingQueue<DatagramPacket> workerQueue;
    private Thread[] workerThreads;
    private List<rw> rws;
    
    public Dispatcher(int numberThreads, DatagramSocket dgSocket, List<rw> rws){
        workerQueue = new LinkedBlockingQueue<>();
        workerThreads = new Thread[numberThreads];
        this.rws = rws;
        //starts worker Threads
        for(Thread thread : workerThreads){
            thread = new WorkerThread(dgSocket,this,rws);
            thread.start();
        }
    }
    
    //Task from Server gets added to Queue
    public void addTask(DatagramPacket data) {
        try{
            workerQueue.put(data);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //Worker Thread removed Task from Queue - BlockingQueue inherently Thread proof
    public DatagramPacket getTask() {
        try{
            return workerQueue.take();
        }catch(InterruptedException e){
            e.printStackTrace();
            return null;
        }
        
    }
}
