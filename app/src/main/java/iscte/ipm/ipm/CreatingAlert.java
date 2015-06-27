package iscte.ipm.ipm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Filipe on 31/05/2015.
 */


public class CreatingAlert extends Activity{

    private TimePicker tpalert;
    private Calendar now;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alert);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar timeOff = Calendar.getInstance();
        int days = Calendar.SUNDAY + (7 - timeOff.get(Calendar.DAY_OF_WEEK)); // how many days until Sunday
        timeOff.add(Calendar.DATE, Calendar.getInstance().DAY_OF_MONTH);
        timeOff.set(Calendar.HOUR, 18);
        timeOff.set(Calendar.MINUTE, 35);
        timeOff.set(Calendar.SECOND, 0);

        System.out.println(timeOff + "PUTA");

        Intent i = new Intent(this, NotificationBarAlarm.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarm.set(AlarmManager.RTC_WAKEUP, timeOff.getTimeInMillis(), pi);



        now = Calendar.getInstance();
        tpalert = (TimePicker)findViewById(R.id.TPalert);
        tpalert.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        tpalert.setCurrentMinute(now.get(Calendar.MINUTE));

    }


    public void save(View v){
        Intent i = new Intent(CreatingAlert.this, Alerts.class);
        startActivity(i);
    }
}
