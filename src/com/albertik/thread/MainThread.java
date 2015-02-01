package com.albertik.thread;

import com.albertik.utils.MessageManager;
import com.perm.kate.api.Message;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Beta on 2/1/15.
 */
public class MainThread extends Thread {
    private MessageManager manager;
    private int waittime;
    private int delta_wait_time;


    public MainThread(MessageManager manager,int waittime,int delta_wait_time){
        this.manager = manager;
        this.waittime = waittime;
        this.delta_wait_time = delta_wait_time;
    }
    @Override
    public void run() {
       while (!this.isInterrupted()){
           try {
               Thread.sleep(waittime + new Random().nextInt(delta_wait_time));
               manager.downloadMessagesFromServer();
               ArrayList<Message> messages = manager.getMessages();
               for(Message m: messages){
                   System.out.println(m.body);
                   manager.reply(m,"+"+m.body);
               }
               manager.readMessages();

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
