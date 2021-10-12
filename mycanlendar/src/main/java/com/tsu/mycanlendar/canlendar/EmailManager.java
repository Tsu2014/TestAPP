package com.tsu.mycanlendar.canlendar;

public class EmailManager {

    private static final String TAG  = "EmailManager";
    private static EmailManager emailManager;

    private EmailManager(){

    }

    public static EmailManager getInstance(){
        if(emailManager == null){
            emailManager = new EmailManager();
        }

        return emailManager;
    }

    public void Test(){

    }

}
