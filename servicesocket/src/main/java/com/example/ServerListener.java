package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Created by 马方 on 2015/12/3.
 */
public class ServerListener extends Thread{

    @Override
    public void run() {
        //1-65535
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true){
                Socket socket = serverSocket.accept();
                JOptionPane.showMessageDialog(null,"你打开了12345");
                //将socket传递给新的线程
                ChatSocket cs = new ChatSocket(socket);
                cs.start();
                ChatMnager.getCm().add(cs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
