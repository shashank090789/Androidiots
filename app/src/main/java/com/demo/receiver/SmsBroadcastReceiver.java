package com.demo.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.demo.MainActivity;
import com.demo.R;
import com.demo.provider.message.MessageContentValues;
import com.demo.service.UpdateService;

/**
 * Created by SHASHANK on 15-06-2015.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
@Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String smsBody="";
            String address="";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                 smsBody = smsMessage.getMessageBody().toString();
                 address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
            sendNotification(smsMessageStr, context);

            Intent updateService=new Intent(context, UpdateService.class);
            updateService.putExtra("message",smsBody);
            updateService.putExtra("phno",address);
            updateService.putExtra("is_last", false);
            context.startService(updateService);
            //this will update the UI with message
//            SmsActivity inst = SmsActivity.instance();
//            inst.updateList(smsMessageStr);
        }
    }
    private void sendNotification(String msg,Context context) {
        try{
            android.support.v4.app.NotificationCompat.Builder mBuilder;

            mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);

                mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("New SMS")
                                .setTicker(msg)
                                .setContentText(msg);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());



        }catch(Exception e){}



    }

}
