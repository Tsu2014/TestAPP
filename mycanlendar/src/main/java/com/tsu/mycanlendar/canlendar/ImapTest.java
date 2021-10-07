package com.tsu.mycanlendar.canlendar;

import android.util.Log;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class ImapTest {

    private static final String TAG = "ImapTest";
    private static ImapTest imapTest ;

    private ImapTest(){

    }

    public static ImapTest getInstance(){
        if(imapTest == null){
            imapTest = new ImapTest();
        }

        return imapTest;
    }

    public void getInputBoxByQQ() throws Exception{
        Session session = Session.getDefaultInstance(new Properties());
        Store store = session.getStore("imaps");
        store.connect(Consts.IMAP_HOST_QQ , Consts.IMAP_PORT_QQ , Consts.EMAIL_ADDR_QQ , Consts.CODE_IMAP_SMTP_QQ);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message [] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));
        Log.d(TAG , "messages length : "+messages.length);
        for(Message message : messages){
            Log.d(TAG , message.getSubject()+" , description : "+message.getDescription());
        }
    }

    public void getInputBoxBy126() throws Exception{
        Session session = Session.getDefaultInstance(new Properties());
        Store store = session.getStore("imaps");
        store.connect(Consts.IMAP_HOST_126 , 993 , Consts.EMAIL_ADDR_126 , Consts.CODE2_IMAP_SMTP_126);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message [] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));
        Log.d(TAG , "messages length : "+messages.length);
        for(Message message : messages){
            Log.d(TAG , message.getSubject()+" , description : "+message.getDescription());
        }
    }

}
