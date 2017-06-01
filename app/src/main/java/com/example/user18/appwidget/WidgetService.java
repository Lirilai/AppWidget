package com.example.user18.appwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetService extends Service {
    AppWidgetManager manager = AppWidgetManager.getInstance(this);



    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int capacity = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            float fPercent = ((float) level/ (float) capacity) * 100f;
            int percent = Math.round(fPercent);

            int drawableLevel = percent * 100;
            updateBatteryWidget(percent);


        }
    };
    public WidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    private void updateBatteryWidget (int percent ) {


        int [] ids = manager.getAppWidgetIds(new ComponentName(this, WidgetReceiver.class));

        for (int id: ids) {

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.battery_widget);

            views.setTextViewText(R.id.widget_text, getString(R.string.persent_format, percent));
            manager.updateAppWidget(id, views);

        }



    }

}
