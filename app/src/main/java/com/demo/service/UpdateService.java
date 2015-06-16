package com.demo.service;

import android.app.IntentService;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.util.Log;

import com.demo.MainActivity;
import com.demo.provider.message.MessageColumns;
import com.demo.provider.message.MessageContentValues;
import com.demo.provider.message.MessageSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * Created by SHASHANK on 15-06-2015.
 */
public class UpdateService extends IntentService  {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    String message;
    String phno;
    boolean is_last;
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER

            };
    public UpdateService() {
        super("Update");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
            message=intent.getStringExtra("message");

            phno=intent.getStringExtra("phno");
        is_last=intent.getBooleanExtra("is_last",false);
        Log.d("Service Started", "details" + message + "_" + phno+is_last);
        Cursor mCCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION, null, null, null);

       saveData(mCCursor);
    }

private void saveData(Cursor data)
{
    Log.d("onLoadComplete","details"+message+"_"+phno);
    String sender_phne="";
    boolean isInPhoneContact=false;
    if(phno.length()==10 || phno.length()>10)
    {
             if(phno.length()==10)
             {
                 sender_phne=phno;
             }
        else if(phno.length()>10)
             {
                 sender_phne= phno.substring(phno.length()-10);
             }
    }
    else
    {
        sender_phne=phno;
    }

    ArrayList<String> phneList=new ArrayList<String>();
    while(data.moveToNext()) {
        String phoneNumber = data.getString(4);
        phneList.add(phoneNumber);
    }
    for(String string:phneList)
    {
         if(string.contains(sender_phne))
         {
             Log.d("contact", "details"+string);
             isInPhoneContact=true;
             break;
         }

    }
   // int index=Collections.binarySearch(phneList, phno);
    //Log.d("index","index"+index);
    insert(isInPhoneContact);
}


    private void insert(boolean isInPhoneContact)
    {
        Log.d("insert", "details" + isInPhoneContact + "_" + phno);

        MessageContentValues values=new MessageContentValues();
        values.putMessageId(System.currentTimeMillis());
        if (isInPhoneContact) {
            values.putIsSenderInPhoneContact("true");
        }
        else
        {
            values.putIsSenderInPhoneContact("false");
        }
        values.putIsReceived("true");
        values.putMessageText(message);
        values.putReceiver("");
        values.putSender(phno);
        values.putTime(new Date().toString());
        values.putIsRead("false");
        Log.d("inserted", "details" + true + "_" + phno);
        if(checkIfSenderExistAlready(phno))
        {
            String where = MessageColumns.SENDER+"=?";
            String[] args = new String[] { phno };
            getContentResolver().update(MessageColumns.CONTENT_URI, values.values(), where, args);
        }
        else {
            getContentResolver().insert(MessageColumns.CONTENT_URI, values.values());
        }
        if(is_last)
        {
            Intent req_intent=new Intent(MainActivity.INSERT_BROADCAST_ACTION);


            sendBroadcast(req_intent);
        }
    }
    private boolean checkIfSenderExistAlready(String phne)
    {
        MessageSelection where = new MessageSelection();
        where.sender(phne);
        Cursor c = getContentResolver().query(MessageColumns.CONTENT_URI, null,
                where.sel(), where.args(), null);
        if(c.moveToNext())
        {
            Log.d("checkIfSender", "details" + true + "_" + phno);
            return true;
        }
        else {
            return false;
        }
    }

}
