package com.company.Threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        /*List<Thread> threadList = new ArrayList<Thread>();
        for (int i = 1; i < 10; i++) {
            Thread t = new Threads(i);
            threadList.add(t);
            t.start();
        }
        for (Thread t : threadList) {
            try {
                t.join();
            } catch(Exception e) { }
        } System.out.println("ENDE");*/
        noThread();
        Threads();
        
        
    }
    public static void Threads(){
        int[] largeArr = new int[2097152];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = 5;
        }
        int threadC = 2;
        int startIndex = 0;
        int endIndex = 2097151;
        for (int i = 0; i < threadC; i++) {
            endIndex =(int) Math.floor(2097151 / threadC + startIndex);
            Thread t = new Threads(i,largeArr,startIndex,endIndex);
            t.start();
            startIndex = endIndex++;
        }
        
    }
    
    public static void noThread(){
        int[] largeArr = new int[2097152];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = (int)Math.floor(Math.random() * 1000000);
        }
        long startTime = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < largeArr.length; i++) {
            sum += largeArr[i];
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime +"milliseconds");
        System.out.println(sum);
    }
}