/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moustafa Ibrahim
 */
public class Server implements Runnable {

    byte[] sendData = new byte[1024];
    DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    String c;
    int a, b;
    String sentence;

    Server(DatagramSocket serverSocket, DatagramPacket receivePacket,String sentence) {
        this.serverSocket = serverSocket;
        this.receivePacket = receivePacket;
        this.sentence=sentence;
    }

    public static void main(String[] args) throws SocketException, IOException {
        Scanner s=new Scanner(System.in);
        System.out.println("Please Enter number of Port: ");
        int port=s.nextInt();
        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            //System.out.println(new String(receivePacket.getData()).trim());
            new Thread(new Server(serverSocket, receivePacket,new String(receivePacket.getData()).trim())).start();

        }
    }

    @Override
    public void run() {
        try {
            okay();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void okay() throws IOException {

        
        //sentence = new String(receivePacket.getData()).trim();
        System.out.println("RECEIVED: " + sentence);
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        a = Integer.parseInt(sentence.split(":")[0]);
        b = Integer.parseInt(sentence.split(":")[1]);
        c = Integer.toString(a + b);
        sendData = c.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(sendPacket);

    }

}
