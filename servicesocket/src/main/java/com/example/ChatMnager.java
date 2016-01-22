package com.example;

import java.util.Vector;

/**
 * Created by 马方 on 2015/12/4.
 */
public class ChatMnager {

    private static final ChatMnager cm = new ChatMnager();

    private Vector<ChatSocket> vector = new Vector<ChatSocket>();
    private ChatMnager(){

    }

    public static ChatMnager getCm(){
        return cm;
    }

    public void add(ChatSocket chatSocket){
        vector.add(chatSocket);
    }

    public void publish(ChatSocket chatSocket,String out){
        for (int i=0;i<vector.size();i++){
            ChatSocket cs = vector.get(i);
            if(!cs.equals(chatSocket)){
                cs.out(out);
            }
        }
    }
}
