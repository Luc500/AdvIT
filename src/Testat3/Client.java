/*
Created by Luca Hahn and Christoph Mangold
on 5.11.20
hahn.luca@yahoo.de christoph.mangold@freenet.de
 */

package Testat3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

//Sends user input to Server
public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket dgSocket = new DatagramSocket(5998);
        Scanner s = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        //Takes user input - UDP connection to Server
        while (true) {
            System.out.println("Type in command");
            String msg = s.nextLine();
            
            DatagramPacket data = new DatagramPacket(msg.getBytes(), msg.length(), ip, 5999);
            dgSocket.send(data);
            
            byte[] buffer = new byte[65535];
            DatagramPacket answerData = new DatagramPacket(buffer,65535);
            dgSocket.receive(answerData);
            String output = new String(answerData.getData(),0, answerData.getLength());
            System.out.println(output);
        }
    }
}