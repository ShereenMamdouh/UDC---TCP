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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moustafa Ibrahim
 */
public class Server implements Runnable {

    Socket csocket;

    Server(Socket csocket) {
        this.csocket = csocket;
    }

    public static void main(String[] args) throws IOException, Exception {
        Scanner s=new Scanner(System.in);
        System.out.println("Please Enter number of Port: ");
        int port=s.nextInt();
        ServerSocket WelcomeSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Connected");
            Socket sock = WelcomeSocket.accept();
            System.out.println("Connected");
            new Thread(new Server(sock)).start();
        }
    }

    @Override
    public void run() {

        try {
            okay();
        } catch (IOException ex) {
               System.out.println("error1");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void okay() throws IOException {
        String a, b;
        String c;
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(csocket.getOutputStream());
        a = inFromClient.readLine();
        b = inFromClient.readLine();
        System.out.println("RECEIVED " + a+":"+b);
        c = Integer.toString((Integer.parseInt(a)) + (Integer.parseInt(b)));
        outToClient.writeBytes(c + '\n');
    }

}
