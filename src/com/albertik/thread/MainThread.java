package com.albertik.thread;

import com.albertik.utils.MessageManager;
import com.albertik.utils.yandex.SpellChecker;
import com.perm.kate.api.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

               //LOGGIN
               DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
               Date date = new Date();

               for(Message m: messages){

                   System.out.println(dateFormat.format(date) + " -> " +m.body);


                   SpellChecker checker = new SpellChecker();
                   String correct = checker.correct(m.body);
                   manager.reply(m, "+" + correct);
               }
               manager.readMessages();

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
