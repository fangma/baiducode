package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by 马方 on 2015/12/4.
 */
public class ChatSocket extends Thread{
    private Socket socket;
    public  ChatSocket(Socket socket){
        this.socket=socket;
    }

    public void out(String out){
        try {
            socket.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
//            int count =0;
//            while(true){
//                count++;
//                out("loop:"+count);
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        try {
            BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line=null;
            while((line =br.readLine())!=null){
                ChatMnager.getCm().publish(this,line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
