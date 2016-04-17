package com.ytying.ytblog.event;

/**
 * Created by UKfire on 16/4/17.
 */
public class ResendMessageEvent {
    public int position;

    public ResendMessageEvent(int position){
        this.position = position;
    }

    public int getPosition(){
        return this.position;
    }

}
