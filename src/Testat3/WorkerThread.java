package Testat3;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

//completes tasks, answers Client
public class WorkerThread extends Thread {
    private DatagramSocket dgSocket;
    private Dispatcher dispatcher;
    private List<rw> rws;
    
    public WorkerThread(DatagramSocket dgSocket, Dispatcher dispatcher, List<rw> rws) {
        this.dgSocket = dgSocket;
        this.dispatcher = dispatcher;
        this.rws = rws;
    }
    
    public void run() {
        //tries to grab data from dispatcher - if successful processes it
        while (true) {
            DatagramPacket data = dispatcher.getTask();
            if(data != null){
                main(data);
            }
        }
    }
    
    public void main(DatagramPacket data) {
        try {
            String command = new String(data.getData(), 0, data.getLength());
            InetAddress ip = InetAddress.getLocalHost();
            String answer;
            if (command.startsWith("READ")) {
                String[] splits = command.split(",");
                if (splits.length != 2) {
                    send("Invalid command", ip);
                    return;
                }
                String filename = splits[0].substring(5);
                //finds rw for current File
                rw currentRw = findRw(filename);
                if(currentRw == null){
                    send("File was not found",ip);
                    return;
                }
                int line = Integer.parseInt(splits[1]);
                if (checkLine(line)) {
                    answer = currentRw.read(line);
                    try {
                        send(answer, ip);
                    } catch (NullPointerException e) {
                        send("Line is empty!", ip);
                    }
                } else {
                    send("Line needs to be a positive integer number", ip);
                }
            } else if (command.startsWith("WRITE")) {
                String[] splits = command.split(",", 3);
                if (splits.length != 3) {
                    send("Invalid Command", ip);
                    return;
                }
                String filename = splits[0].substring(6);
                //finds rw for current File
                rw currentRw = findRw(filename);
                if(currentRw == null){
                    send("File was not found",ip);
                    return;
                }
                int line = Integer.parseInt(splits[1]);
                if (checkLine(line)) {
                    String writeData = splits[2];
                    send(currentRw.write(line, writeData), ip);
                } else {
                    send("Line needs to be a positive integer number", ip);
                }
            } else {
                send("Not a valid command", ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //checks if given line is valid
    public Boolean checkLine(int line) {
        if (line > 0 && line % 1 == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    //replies to Client
    public void send(String answer, InetAddress ip) throws Exception {
        DatagramPacket answerData = new DatagramPacket(answer.getBytes(), answer.length(), ip, 5998);
        dgSocket.send(answerData);
    }
    //gives back read/write for currently used file
    public rw findRw(String filename){
        File file = new File("C:\\Users\\lhahn\\IdeaProjects\\AdvIT\\Files\\" + filename);
        for (rw rw:rws) {
            if(rw.file.equals(file)){
                return rw;
            }
        }
        return null;
    }
}