package com.albertik.utils;

import com.perm.kate.api.Api;
import com.perm.kate.api.KException;
import com.perm.kate.api.Message;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Beta on 2/1/15.
 */
public class MessageManager {
    private Api api;
    private ArrayList<Message> messages  = new ArrayList<Message>();
    public MessageManager(Api api){
        this.api=api;
    }


    public ArrayList<Message> getMessages(){
        return this.messages;
    }
    public void addMessages(ArrayList<Message> messages){
        this.messages = messages;
    }
    public void downloadMessagesFromServer()
    {
        try {
            this.messages = api.getMessages(0, false, 100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KException e) {
            e.printStackTrace();
        }
    }
    public boolean reply(Message m,String text){
        try {
            api.sendMessage(m.uid,0,text
                    ,"","0",new ArrayList<String>(),new ArrayList<Long>(),
                    "","","","");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (KException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public  void readMessages()
    {
        ArrayList<Long> mids = new ArrayList<Long>();
        for(Message m :this.messages){
            System.out.println(m.body);
            mids.add(m.mid);
        }
        try {
            api.markAsNewOrAsRead(mids,true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KException e) {
            e.printStackTrace();
        }
    }


}
