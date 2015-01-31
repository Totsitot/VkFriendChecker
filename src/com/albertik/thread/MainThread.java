package com.albertik.thread;

import com.albertik.utils.MessageManager;
import com.perm.kate.api.Message;

import java.util.ArrayList;

/**
 * Created by Beta on 2/1/15.
 */
public class MainThread extends Thread {
    private MessageManager manager;
    private int waittime;



    public MainThread(MessageManager manager,int waittime){
        this.manager = manager;
        this.waittime = waittime;
    }
    @Override
    public void run() {
       while (!this.isInterrupted()){
           try {
               Thread.sleep(waittime);
               manager.downloadMessagesFromServer();
               ArrayList<Message> messages = manager.getMessages();
               for(Message m: messages){
                   System.out.println();
               }

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
