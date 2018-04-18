/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    int port;
    Random random;
    int r1,r2;
    String s1,s2,s;
    Client(String name,int port) {
        this.name = name;
        this.port=port;
         System.out.println("Creating " +  name );
    }

    public static void main(String[] args) throws IOException {
         System.out.println("Please enter number of Port: ");
        Scanner s1 = new Scanner(System.in);
        String name;
        System.out.println("Please enter number of Port: ");
        int port=s1.nextInt();
        System.out.println("Please enter number of Clients: ");
        int c=s1.nextInt();
        for (int i = 0; i < c; i++) {
            name="Client "+i;
            new Thread(new Client(name,port)).start();
        }

    }

    @Override
    public void run() {
        try {
            okay();
        } catch (IOException ex) {
               System.out.println("Connected");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void okay() throws IOException {
         System.out.println("Running " +  name );
        String z;
        Socket ClientSocket = new Socket("LocalHost", port);
         System.out.println(name + " connected");
        DataOutputStream OutToServer = new DataOutputStream(ClientSocket.getOutputStream());
        System.out.println(name + " connected");
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
        random = new Random();
        r1= random.nextInt(10);
        r2=random.nextInt(10);
        System.out.println(name+" generated "+r1+":"+r2);        
        s1=Integer.toString(r1);
        s2=Integer.toString(r2);
        OutToServer.writeBytes(s1+ '\n');
        OutToServer.writeBytes(s2 + '\n');
        z = inFromServer.readLine();
        System.out.println(name + " from server :  " + z);
        ClientSocket.close();

    }
}
