package com.example.alarm.service;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.example.alarm.data.Alarm;
import com.example.alarm.data.AlarmRepository;

import java.util.List;

public class RescheduleAlarmsService extends LifecycleService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        AlarmRepository alarmRepository = new AlarmRepository(getApplication());

        alarmRepository.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(List<Alarm> alarms) {
                for (Alarm a : alarms) {
                    if (a.isStarted()) {
                        a.schedule(getApplicationContext());
                    }
                }
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
