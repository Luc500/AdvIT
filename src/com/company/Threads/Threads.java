package com.company.Threads;

public class Threads extends Thread {
    private int id;
    private int[] largeArr;
    private int startIndex;
    private int endIndex;
    
    public Threads(int id,int[] largeArr, int startIndex, int endIndex) {
        this.id = id;
        this.largeArr = largeArr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    public void run() {
        /*try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {
        }
        System.out.println("Hello World (ID = " + id + ")");*/
    
        long startTime = System.currentTimeMillis();
        long sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += largeArr[i];
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime +" milliseconds\nWorker: " + id);
        System.out.println(sum);
        
    }
}

