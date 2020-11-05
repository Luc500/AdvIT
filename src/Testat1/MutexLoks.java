/*
Erstellt von Luca Hahn und Christoph Mangold am 17.10.2020
Abgabe für AdvanvedIT, Testat Aufgabe 1
*/
/*
Gedanke: In diesem Beispiel ist die Semaphore mutex nicht nötig, oder?
Da die Loks sich nur gegenseitig Erlaubnis geben können und Erlaubnis erst gegeben wird, wenn der
kritische Abschnitt bereits verlassen ist, können die Loks niemals gleichzeitig im kritischen Abschnitt sein
auch ohne mutex.
 */
package Testat1;

import java.util.concurrent.Semaphore;

//Lösung Aufgabe a
public class MutexLoks {
    
    static Semaphore mutex = new Semaphore(1, true);        //erlaubt nur eine Lok im kritischen Abschnitt
    static Semaphore lok1permit = new Semaphore(0, true);   //Lok 1 Erlaubnis
    static Semaphore lok0permit = new Semaphore(1,true);    //Lok 0 Erlaubnis
    
    static Runnable Lok0 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Lok 0 fährt im unkritischen Abschnitt");
                    int ukRnd = 1000 + (int)Math.floor(Math.random() * 4000);
                    Thread.sleep(ukRnd);
                    System.out.println("Lok 0 hat " + ukRnd + "ms im UK Abschnitt verbracht");
                    //Verbraucht Erlaubnis für Lok 0
                    lok0permit.acquire();
                    //Blockiert kritischen Abschnitt
                    mutex.acquire();
                    System.out.println("Lok 0 fährt jetzt im kritischen Abschnitt");
                    int kRnd = 1000 + (int)Math.floor(Math.random() * 2000);
                    Thread.sleep(kRnd);
                    System.out.println("Lok 0 hat " + kRnd + "ms im kritischen Abschnitt verbracht");
                    //Gibt kritischen Abschnitt wieder frei
                    mutex.release();
                    //Erteilt Erlaubnis für Lok 1 frei
                    lok1permit.release();
                    System.out.println("Lok 0 hat das Mittelstück verlassen");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    
    static Runnable Lok1 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try{
                    System.out.println("Lok 1 fährt im unkritischen Abschnitt");
                    int ukRnd = 1000 + (int)Math.floor(Math.random() * 4000);
                    Thread.sleep(ukRnd);
                    System.out.println("Lok 1 hat " + ukRnd + "ms im UK Abschnitt verbracht");
                    //Verbraucht Erlaubnis für Lok 1
                    lok1permit.acquire();
                    //Blockiert kritischen Abschnitt
                    mutex.acquire();
                    System.out.println("Lok 1 fährt jetzt im kritischen Abschnitt");
                    int kRnd = 1000 + (int)Math.floor(Math.random() * 2000);
                    Thread.sleep(kRnd);
                    System.out.println("Lok 1 hat " + kRnd + "ms im kritischen Abschnitt verbracht");
                    //Gibt kritischen Abschnitt wieder frei
                    mutex.release();
                    //Erteilt Erlaubnis für Lok 0
                    lok0permit.release();
                    System.out.println("Lok 1 hat das Mittelstück verlassen");
                }catch (Exception e){
                    e.printStackTrace();
                }
                
            }
        }
    };
}
