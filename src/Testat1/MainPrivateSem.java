/*
Erstellt von Luca Hahn und Christoph Mangold am 17.10.2020
Abgabe für AdvanvedIT, Testat Aufgabe 1
 */
package Testat1;

public class MainPrivateSem {
    public static void main(String[] args) {
        //Lösung Aufgabe b
        int threadCount = 2;
        privateLoks loks = new privateLoks(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            new Thread(() -> {
                loks.drive(finalI);
            }).start();
        }
    }
}
