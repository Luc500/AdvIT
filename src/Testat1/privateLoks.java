/*
Erstellt von Luca Hahn und Christoph Mangold am 17.10.2020
Abgabe für AdvanvedIT, Testat Aufgabe 1
*/
package Testat1;

import java.util.concurrent.Semaphore;

//Lösung Aufgabe b
public class privateLoks {
    
    private Semaphore mutex = new Semaphore(1, true); //erlaubt kritischen Gleiszustand überprüfen/ ändern
    private Semaphore privSem[];                                    //erlaubt kritisches Gleis zu befahren
    private boolean gleisFrei = true;
    private boolean waiting[];                                      //besitzt thread_ids die auf kritsche Gleiserlaubnis warten
    private static final int t_id = -1;                             //nicht-existente Thread-ID (Platzhalter)
    private int blockLok = 1;                                       //blockt eben gefahrene Lok vom kritischen Abschnitt
    
    public privateLoks(int threadCount) {
        privSem = new Semaphore[threadCount];
        waiting = new boolean[threadCount];
        for (int i = 0; i < threadCount; i++) {
            waiting[i] = false;
            privSem[i] = new Semaphore(0, true);
        }
    }
    
    public void drive(int thread_id) {
        while (true) {
            try {
                System.out.println("Lok " + thread_id +" fährt im unkritischen Abschnitt");
                int ukRnd = 1000 + (int)Math.floor(Math.random() * 4000);
                Thread.sleep(ukRnd);
                System.out.println("Lok " + thread_id + " hat " + ukRnd + "ms im UK Abschnitt verbracht");
                //erlaubt kritischen Gleiszustand zu überprüfen/ ändern in belegt
                mutex.acquire();
                if (gleisFrei && blockLok != thread_id) {
                    gleisFrei = false;
                    blockLok = thread_id;
                    //erlaubt zutritt in kritischen Abschnitt
                    privSem[thread_id].release();
                } else {
                    //stellt thread_id als wartend
                    waiting[thread_id] = true;
                }
                //gibt Gleizustand überprüfen/ ändern wieder frei
                mutex.release();
                //betritt kritischen Abschnitt
                privSem[thread_id].acquire();
                System.out.println("Lok "+ thread_id + " fährt jetzt im kritischen Abschnitt");
                int kRnd = 1000 + (int)Math.floor(Math.random() * 2000);
                Thread.sleep(kRnd);
                System.out.println("Lok "+ thread_id +" hat " + kRnd + "ms im kritischen Abschnitt verbracht");
                //ändert kritischen Abschnitt in frei
                mutex.acquire();
                gleisFrei = true;
                System.out.println("Lok "+ thread_id +" hat das Mittelstück verlassen");
                int selectedThread = t_id;
                for (int i = 0; i < waiting.length; i++) {
                    if (waiting[i]) {
                        selectedThread = i;
                    }
                }
                //falls wartender thread gefunden wurde
                if (selectedThread != t_id) {
                    //nicht mehr wartend
                    waiting[selectedThread] = false;
                    //blockiert kritischen Abschnitt
                    gleisFrei = false;
                    //erteilt Gleiserlaubnis für kritischen Abschnitt
                    privSem[selectedThread].release();
                }
                //gibt Zustand des kritischen Abschnitts ändern/ überprüfen wieder frei
                mutex.release();
                
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
    
}
