package Testat3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket dgSocket = new DatagramSocket(5998);
        Scanner s = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        
        while (true) {
            System.out.println("Type in command");
            String msg = s.nextLine();
            
            DatagramPacket data = new DatagramPacket(msg.getBytes(), msg.length(), ip, 5999);
            dgSocket.send(data);
            
            byte[] buffer = new byte[1024];
            DatagramPacket answerData = new DatagramPacket(buffer,1024);
            dgSocket.receive(answerData);
            String output = new String(answerData.getData(),0, answerData.getLength());
            System.out.println(output);
        }
    }
}