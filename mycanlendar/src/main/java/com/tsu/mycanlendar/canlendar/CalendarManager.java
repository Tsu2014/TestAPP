package com.tsu.mycanlendar.canlendar;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarManager {

    private static final String TAG = "CanlendarManager";

    private static String CALENDAR_URL = "content://com.android.calendar/calendars";
    private static String CALENDAR_EVENT_URL = "content://com.android.calendar/events";
    private static String CALENDAR_REMINDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDAR_NAME = "TSU";
    private static String CALENDAR_ACCOUNT_NAME = "TEST@TSU.com";
    private static String CALENDAR_ACCOUNT_TYPE = "com.android.tsu";
    private static String CALENDAR_DISPLAY_NAME = "TSU ACCOUNT";

    private Context context;
    private static CalendarManager canlendarManager;

    private CalendarManager() {
        //this.context = context;
    }

    public static CalendarManager getInstance() {
        if (canlendarManager == null) {
            canlendarManager = new CalendarManager();
        }

        return canlendarManager;
    }

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     *
     * @param context
     * @return
     */
    public int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id， 否则返回-1
     *
     * @param context
     * @return
     */
    @SuppressLint("Range")
    public int checkCalendarAccount(Context context) {
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALENDAR_URL), null, null, null, null);
        try {
            if (userCursor == null) {
                Log.d(TAG, "UserCursor is null !");
                return -1;
            }
            int count = userCursor.getCount();
            Log.d(TAG, "checkCalendarAccount count : " + count);
            if (count > 0) {
                int calID = -1;
                userCursor.moveToFirst();
                while(!userCursor.isAfterLast()){
                    int calId = userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
                    String accountName = userCursor.getString(userCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME));
                    String displayName = userCursor.getString(userCursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
                    if(!TextUtils.isEmpty(displayName) && "tsu2021@qq.com".equals(displayName)){
                        calID = calId;
                    }
                    String accountType  = userCursor.getString(userCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_TYPE));
                    Log.d(TAG , "CalID : "+calID+" , ACCOUNT_NAME : "+accountName+" , DISPLAY_NAME : "+displayName+" , TYPE : "+accountType);
                    userCursor.moveToNext();
                }

                if(calID == -1){
                    userCursor.moveToFirst();
                    calID = userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
                }
                Log.d(TAG , "calID : "+calID);
                return calID;
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回id，否则返回-1
     * @param context
     * @return
     */
    public long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDAR_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDAR_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDAR_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALENDAR_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER , "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME , CALENDAR_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE , CALENDAR_ACCOUNT_TYPE)
                .build();
        Uri result = context.getContentResolver().insert(calendarUri , value);
        long id = result ==null ?-1: ContentUris.parseId(result);
        return id;
    }

    public void addCalendarEvent(Context context , String title , String description , long reminderTime , int previousDate){
        if(context ==null){
            return ;
        }
        int calId = checkAndAddCalendarAccount(context);
        if(calId <0){
            return ;
        }

        Calendar mCalenddar = Calendar.getInstance();
        mCalenddar.setTimeInMillis(reminderTime);//设置开始时间
        long start = mCalenddar.getTime().getTime();
        mCalenddar.setTimeInMillis(start + 10*60*1000); //设置结束时间，开始时间加10分钟
        long end = mCalenddar.getTime().getTime();
        ContentValues event = new ContentValues();
        event.put("title" , title);
        event.put("description" , description);
        event.put("calendar_id" , calId);
        event.put(CalendarContract.Events.DTSTART , start);
        event.put(CalendarContract.Events.DTEND , end);
        event.put(CalendarContract.Events.HAS_ALARM , 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE , "Asia/Shanghai");

        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALENDAR_EVENT_URL) , event);
        if(newEvent == null){
            return ;
        }

        //事件提醒的设定
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID , ContentUris.parseId(newEvent));
        values.put(CalendarContract.Reminders.MINUTES , previousDate * 24 * 60);
        values.put(CalendarContract.Reminders.METHOD , CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = context.getContentResolver().insert(Uri.parse(CALENDAR_REMINDER_URL) , values);
        if(uri == null){ //添加事件提醒失败直接返回
            return ;
        }
    }

    public void queryCalendarEvent(Context context){
        if(context  == null){
            return ;
        }

        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDAR_EVENT_URL) , null ,null,null,null);

        try{
            if(eventCursor == null){
                return ;
            }
            int count = eventCursor.getCount();
            Log.d(TAG , "count : "+count);
            if(count > 0){
                for(eventCursor.moveToFirst();!eventCursor.isAfterLast();eventCursor.moveToNext()) {
                    @SuppressLint("Range") String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    @SuppressLint("Range") int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Events._ID));
                    @SuppressLint("Range") int calId = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID));
                    Log.d(TAG , "id : "+id+" , title : "+eventTitle+" , calId : "+calId);
                }
            }
        }finally {
            if(eventCursor!=null){
                eventCursor.close();
            }
        }
    }

    public void deleteCalendarEvent(Context context , String title){
        if(context == null){
            return ;
        }

        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDAR_EVENT_URL) , null ,null,null,null);
        try{
            if(eventCursor ==null){
                return;
            }
            if(eventCursor.getCount() >0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    @SuppressLint("Range") String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        @SuppressLint("Range") int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Log.d(TAG, "find a event : " + id);
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALENDAR_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) {   //事件删除失败
                            return;
                        }
                    }
                }
            }
        }finally {
            if(eventCursor !=null){
                eventCursor.close();;
            }
        }
    }
}
