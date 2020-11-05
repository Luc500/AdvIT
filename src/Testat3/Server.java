package Testat3;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws SocketException{
        DatagramSocket dgSocket = new DatagramSocket(5999);
        File[] files = new File("C:\\Users\\lhahn\\IdeaProjects\\Threads\\Files").listFiles();
        List<rw> rws = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            rws.add(new rw(files[i]));
        }
        Dispatcher dispatcher = new Dispatcher(2,dgSocket,rws);
        while (true) {
            try {
                System.out.println("Waiting for connection");
                byte[] buffer = new byte[65535];
                DatagramPacket data = new DatagramPacket(buffer, 65535);
                dgSocket.receive(data);
                System.out.println("Connection successful");
                dispatcher.addTask(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}