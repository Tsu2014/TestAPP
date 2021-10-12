package com.account.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CalendarSyncAdapterService extends Service {

    //private static SyncAdapterImpl sSyncAdapter = null;
    //static MeetingsDatabaseAdapter sync_Meetings = null;

    public CalendarSyncAdapterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}