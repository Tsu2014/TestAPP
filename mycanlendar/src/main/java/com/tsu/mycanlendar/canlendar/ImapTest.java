package com.tsu.mycanlendar.canlendar;

import android.util.Log;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.protocol.IMAPProtocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
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

    public void getInputBoxBy1261() throws Exception{
        Properties prop = System.getProperties();
        prop.put("mail.imap.host" , Consts.IMAP_HOST_126);
        prop.put("mail.imap.auth.plain.disable" , true);

        final Map<String, String> clientParams = new HashMap<String, String>();
        clientParams.put("name", "my-imap");
        clientParams.put("version", "1.0");

        Session mailSession = Session.getInstance(prop , null);
        mailSession.setDebug(false);
        IMAPFolder folder = null;
        IMAPStore store = null;
        int total = 0;
        try{
            store = (IMAPStore) mailSession.getStore("imap");
            store.connect(Consts.IMAP_HOST_126 , Consts.EMAIL_ADDR_126 , Consts.CODE2_IMAP_SMTP_126);
            folder = (IMAPFolder) store.getFolder("INBOX");

            folder.doOptionalCommand("ID not supported", new IMAPFolder.ProtocolCommand() {
                @Override
                public Object doCommand(IMAPProtocol imapProtocol) throws ProtocolException {
                    return imapProtocol.id(clientParams);
                }
            });

            folder.open(Folder.READ_ONLY);
            total = folder.getMessageCount();
            Log.d(TAG , "message total : "+total);
            Message [] msgs = folder.getMessages();
            Log.d(TAG , "msgs length : "+msgs.length);
            for(Message msg : msgs){
                Log.d(TAG , "subject : "+msg.getSubject()+" , revDate : "+msg.getSentDate());
            }
            Log.d(TAG , "unread : "+folder.getUnreadMessageCount());
            Log.d(TAG , "new email : "+folder.getNewMessageCount());

        }catch(MessagingException e){
            e.printStackTrace();
        }finally {
            try{
                if(folder !=null){
                    folder.close(true);
                }
                if(store != null){
                    store.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
