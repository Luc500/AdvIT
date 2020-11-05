package Testat3;


public class rwMonitor {
    private String file;
    private int waitingWrite = 0;
    private int currentRead = 0;
    private int currentWrite = 0;
    
    public rwMonitor(String file){
        this.file = file;
    }
    
    synchronized public void startRead() {
        try {
            while (currentWrite > 0 || waitingWrite > 0) {
                System.out.println("I have to wait to read " + file);
                wait();
            }
            System.out.println("Now reading " + file);
            currentRead++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    synchronized public void endRead() {
        System.out.println("Done reading");
        currentRead--;
        notifyAll();
    }
    
    synchronized public void startWrite() {
        try {
            waitingWrite++;
            while (currentWrite > 0 || currentRead > 0) {
                System.out.println("I have to wait to write " + file);
                wait();
            }
            System.out.println("Now writing" + file);
            currentWrite++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    synchronized public void endWrite() {
        System.out.println("Done writing");
        currentWrite--;
        waitingWrite--;
        notifyAll();
    }
}
