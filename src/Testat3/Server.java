package Testat3;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

//takes user input, passes it to Dispatcher
public class Server {
    public static void main(String[] args) throws SocketException{
        DatagramSocket dgSocket = new DatagramSocket(5999);
        //Reads through files in directory, creates read/writer for all of them
        File[] files = new File("C:\\Users\\lhahn\\IdeaProjects\\AdvIT\\Files").listFiles();
        List<rw> rws = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            rws.add(new rw(files[i]));
        }
        //starts task dispatcher
        Dispatcher dispatcher = new Dispatcher(2,dgSocket,rws);
        //waits for tasks from client - passes them to dispatcher
        while (true) {
            try {
                System.out.println("Waiting for connection");
                byte[] buffer = new byte[65535];
                DatagramPacket data = new DatagramPacket(buffer, 65535);
                dgSocket.receive(data);
                System.out.println("Connection successful");
                dispatcher.addTask(data);
                //Testing - REMEMBER TO COMMENT OUT WHILE TRUE
                /*System.out.println("Testing starts: ");
                String[] tasks = new String[16];
                String task1 = "WRITE 1.txt,1,line1";
                tasks[0] = task1;
                String task2 = "WRITE 1.txt,2,line2";
                tasks[1] = task2;
                String task3 = "WRITE 1.txt,3,line3";
                tasks[2] = task3;
                String task4 = "READ 1.txt,1";
                tasks[3] = task4;
                String task5 = "READ 1.txt,2";
                tasks[4] = task5;
                String task6 = "READ 1.txt,3";
                tasks[5] = task6;
                String task7 = "WRITE 1.txt,4,line4";
                tasks[6] = task7;
                String task8 = "READ 1.txt,4";
                tasks[7] = task8;
                String task9 = "WRITE 1.txt,5,line5";
                tasks[8] = task9;
                String task10 = "READ 1.txt,4";
                tasks[9] = task10;
                String task11= "WRITE 1.txt,6,line6";
                tasks[10] = task11;
                String task12= "WRITE 2.txt,1,line1";
                tasks[11] = task12;
                String task13= "WRITE 1.txt,7,line7";
                tasks[12] = task13;
                //purposefully wrong commands
                String task14= "READ 2.txt,4";
                tasks[13] = task14;
                String task15= "WRITE 2.txt,1";
                tasks[14] = task15;
                String task16= "READ 2.txt,1,line1";
                tasks[15] = task16;
                DatagramPacket[] tasksDP = new DatagramPacket[16];
                for (int i = 0; i < tasks.length; i++) {
                    tasksDP[i] = new DatagramPacket(tasks[i].getBytes(),tasks[i].length());
                }
                for (int i = 0; i < tasksDP.length; i++) {
                    dispatcher.addTask(tasksDP[i]);
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}