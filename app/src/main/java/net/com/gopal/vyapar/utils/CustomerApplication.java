package net.com.gopal.vyapar.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import java.security.acl.Group;
import java.util.ArrayList;

public class CustomerApplication extends MultiDexApplication {
    private String someVariable, cust_id;
    private double lat, lng;
    public static final String CHANNEL_1_ID = "channel_1";
    private static CustomerApplication instance;
    public static ArrayList<Group> articles = new ArrayList<Group>();
    int taxiCount;
    public int getTaxiCount() {
        return taxiCount;
    }
    public CustomerApplication() {
        instance = this;
    }
    public String getDevtokenVariable() {
        return someVariable;
    }
    public void setDevtokenVariable(String someVariable) {
        this.someVariable = someVariable;
    }
    public  double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible = false;

    public static boolean open=true;

    public static Context getContext() {
        return instance;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {

        super.onCreate();
        createNotificationChannels();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel_1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel1.setLightColor(Color.argb(0, 128, 0, 128));
            // channel1.setVibrationPattern(new long[] { 1000, 1000, 1000, 1000, 1000 });
            channel1.setDescription("General Notification");
            NotificationManager manager = getSystemService(NotificationManager.class);
            //  manager.createNotificationChannel(defaultchannel);
            manager.createNotificationChannel(channel1);
          /*  NotificationManager default1= (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
            default1.createNotificationChannel(channel1);*/

        }
    }
}
