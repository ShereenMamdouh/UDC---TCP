/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moustafa Ibrahim
 */
public class Client implements Runnable {

    String name;
    Random random;
    int r1,r2;
    String s1,s2,s;
    int port;
    Client(String name,int port) {
        this.name = name;
        this.port=port;
        System.out.println("Creating "+name);
    }

    public static void main(String[] args) throws IOException {
        Scanner s1 = new Scanner(System.in);
        String name;
        int client_num;
        System.out.println("Please Enter number of Port: ");
        int port=s1.nextInt();
        System.out.print("Please Enter Number of clients: ");
        client_num=s1.nextInt();
        for (int i = 0; i < client_num; i++) {
            name="Client "+i;
            new Thread(new Client(name,port)).start();
        }

    }

    @Override
    public void run() {
        try {
            okay();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void okay() throws IOException {
        System.out.println("Running " +  name );
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        random = new Random();
        r1= random.nextInt(10);
        r2=random.nextInt(10);
        System.out.println(name+" generated "+r1+":"+r2);        
        s1=Integer.toString(r1);
        s2=Integer.toString(r2);
        s=s1+":"+s2;
        sendData = s.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println(name + " FROM SERVER: " + modifiedSentence);
        clientSocket.close();
        
    }
}
