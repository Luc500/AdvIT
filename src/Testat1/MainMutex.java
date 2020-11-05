/*
Erstellt von Luca Hahn und Christoph Mangold am 17.10.2020
Abgabe für AdvanvedIT, Testat Aufgabe 1
 */

package Testat1;


import com.company.Threads.Threads;

public class MainMutex {
    public static void main(String[] args) {
        //Lösung Aufgabe a
        new Thread(MutexLoks.Lok0).start();     //startet Lok 0 Runnable
        new Thread(MutexLoks.Lok1).start();     //startet Lok 1 Runnable
    }
}
