package com.tsu.mycanlendar.canlendar;

import android.text.format.DateUtils;
import android.util.Log;

import org.apache.commons.httpclient.util.DateUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import microsoft.exchange.webservices.data.Appointment;
import microsoft.exchange.webservices.data.Attendee;
import microsoft.exchange.webservices.data.AttendeeCollection;
import microsoft.exchange.webservices.data.CalendarView;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.FindItemsResults;
import microsoft.exchange.webservices.data.FolderId;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.ItemView;
import microsoft.exchange.webservices.data.Mailbox;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.ServiceLocalException;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;

public class ExchangeTest {

    private static final String TAG = "ExchangeTest";
    private static ExchangeTest exchangeTest;
    private final static String SERVICE_HOST = "ex.qq.com";//"outlook.office365.com";//
    private static final String EMAIL_ADDRESS = "tsu2021@qq.com";//"tsu202110@outlook.com";//
    private static final String PASSWORD = "isfknimvnodkbjij";//"SUsu7905";//
    private static final String DOMAIN = "qq.com";//"outlook.com";//

    private ExchangeService service = null;

    private ExchangeTest(){
        initService();
    }

    public static ExchangeTest getInstance(){
        if(exchangeTest == null){
            exchangeTest = new ExchangeTest();
        }

        return exchangeTest;
    }

    public void initService(){
        service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        ExchangeCredentials credentials = new WebCredentials(EMAIL_ADDRESS , PASSWORD , DOMAIN);

        service.setCredentials(credentials);
        //service.setTraceEnabled(true);
        try {
            service.setUrl(new URI("https://"+SERVICE_HOST+"/EWS/Exchange.asmx"));
            //service.autodiscoverUrl(EMAIL_ADDRESS);
            //service.setUrl(new URI("http://ex.qq.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void getCalendar() throws Exception {
        if(service ==null){
            initService();
        }

        FindItemsResults<Item> result = service.findItems(WellKnownFolderName.Calendar , new ItemView(10));
        Log.d(TAG , "result count : "+result.getTotalCount());
        for(Item item : result){
            if(item instanceof Appointment){
                Appointment temp = (Appointment)item;
                Log.d(TAG , "title : "+temp.getSubject()+" , start : "+temp.getStart()+" , end : "+temp.getEnd()+" , rrule : ");
            }else{
                Log.d(TAG , "item : "+item.getSubject());
            }

        }
    }

    public void getCalendar1(){
        if(service ==null){
            initService();
        }

        Date start = new Date();
        Date end = new Date(start.getTime()+1000*3600*24);

        CalendarView calendarView = new CalendarView(start , end);
        FolderId folderId = new FolderId(WellKnownFolderName.Calendar , new Mailbox(EMAIL_ADDRESS));
        FindItemsResults<Appointment> findItemsResults = null;
        try{
            findItemsResults = service.findAppointments(folderId , calendarView);
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<Appointment> appointmentItems = findItemsResults == null?null:findItemsResults.getItems();
        for(Appointment appointment : appointmentItems) {

            try {
                appointment.load();
                String subject = appointment.getSubject();
                Log.d(TAG, "subject : " + subject);
                if (subject.startsWith("已取消")) {
                    continue;
                }

                String html_body = appointment.getBody().toString();
                Date start1 = appointment.getStart();
                Date end1 = appointment.getEnd();
                Log.d(TAG, "start : " + start1);
                Log.d(TAG, "end : " + end1);

                AttendeeCollection resource = appointment.getResources();

                appointment.getRequiredAttendees();
                appointment.getOptionalAttendees();

            } catch (ServiceLocalException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void sendMessage() throws Exception{
        if(service == null){
            initService();
        }
        EmailMessage message = new EmailMessage(service);
        message.getToRecipients().add("l66566@126.com");
        message.setSubject("Hello world!");
        message.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Android API."));
        message.send();
    }

}
