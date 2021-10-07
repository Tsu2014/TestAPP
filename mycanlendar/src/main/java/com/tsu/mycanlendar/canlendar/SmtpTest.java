package com.tsu.mycanlendar.canlendar;

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpTest {

    private static SmtpTest smtpTest;

    private SmtpTest(){

    }

    public static SmtpTest getInstance(){
        if(smtpTest == null){
            smtpTest = new SmtpTest();
        }

        return smtpTest;
    }

    public void sendBy126() throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.debug" , "true");
        props.setProperty("mail.smtp.auth" , "true");
        props.setProperty("mail.host", "smtp.126.com");
        props.setProperty("mail.transport.protocol" , "smtp");
        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        msg.setSubject("AndroidMailTest");
        msg.setText("这是一封由Tsu发送的邮件！");
        msg.setFrom(new InternetAddress("l66566@126.com"));
        Transport transport = session.getTransport();
        transport.connect("l66566@126.com" , "QCTAYAMYOZLPBJSH");
        transport.sendMessage(msg, new Address[]{new InternetAddress("tsu2021@qq.com")});
        transport.close();
    }

    public void sendByQQ() throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.debug" , "true");
        props.setProperty("mail.smtp.auth" , "true");
        props.setProperty("mail.host", "smtp.qq.com");
        props.setProperty("mail.transport.protocol" , "smtp");

        MailSSLSocketFactory msf = new MailSSLSocketFactory();
        msf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.ssl.socketFactory" , msf);

        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        msg.setSubject("AndroidMailTest2");
        msg.setText("这是一封由Tsu发送的邮件2！");
        msg.setFrom(new InternetAddress("tsu2021@qq.com"));
        Transport transport = session.getTransport();
        transport.connect("tsu2021@qq.com" , "wtcekqjoncwucbdj");
        transport.sendMessage(msg, new Address[]{new InternetAddress("l66567@126.com")});
        transport.close();
    }

}
