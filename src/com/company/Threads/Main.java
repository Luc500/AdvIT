package com.company.Threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        noThread();
        Threads(2);
        Threads(4);
        Threads(8);
        
        
        
    }
    public static void Threads(int threadC){
        int[] largeArr = new int[2^21];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = (int)Math.floor(Math.random() * 100);
        }
        int startIndex = 0;
        int endIndex = 2^21;
        for (int i = 0; i < threadC; i++) {
            endIndex =(int) Math.floor(2^21 / threadC + startIndex);
            Thread t = new Threads(i,largeArr,startIndex,endIndex);
            t.start();
            startIndex = endIndex++;
        }
        
    }
    
    public static void noThread(){
        int[] largeArr = new int[2^21];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = (int)Math.floor(Math.random() * 100);
        }
        long startTime = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < largeArr.length; i++) {
            sum += largeArr[i];
        }
        for (int i = 0; i < largeArr.length; i++) {
            sum -= largeArr[i];
        }
        for (int i = 0; i < largeArr.length; i++) {
            sum += largeArr[i];
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime +" milliseconds");
        System.out.println(sum);
    }
}