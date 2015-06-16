package com.demo;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.tv.TvContract;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.model.Message;
import com.demo.provider.message.MessageColumns;
import com.demo.provider.message.MessageCursor;
import com.demo.service.UpdateService;
import com.demo.views.adapter.SendersAdapter;


import java.net.URI;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btn_send;
    private InsertNotifier notifier;
    public static final String SP_FILE_NAME="com.demo.1mg";
    public static final String IS_INBOX_SYNCED="is_inbox_synced";
    public static final int LOADER_FETCH_MESSAGES=4;
    public static final int LOADER_READ_INBOX=3;
    public static final String INSERT_BROADCAST_ACTION="com.demo.INSERT_ACTION";
    private SharedPreferences sp;
    private ProgressBar pg;
    public static final Uri INBOX_URI=Uri.parse("content://sms/inbox");
    private ArrayList<Message> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter mInsertIntentFilter = new IntentFilter(
                INSERT_BROADCAST_ACTION);
        notifier= new InsertNotifier();
        registerReceiver(
                notifier,
                mInsertIntentFilter);
        sp=getSharedPreferences(SP_FILE_NAME,MODE_PRIVATE);
        pg=(ProgressBar)findViewById(R.id.pb);
        boolean is_inbox_synced=sp.getBoolean(IS_INBOX_SYNCED,false);
        if(is_inbox_synced) {
            getLoaderManager().initLoader(LOADER_FETCH_MESSAGES, null, this);
        }
        else {
            Toast.makeText(MainActivity.this,"Syncing inbox,please wait..",Toast.LENGTH_LONG).show();


            getLoaderManager().initLoader(LOADER_READ_INBOX, null, this);
        }
        btn_send = (Button) findViewById(R.id.btn_send);

        messageList=new ArrayList<Message>();
        mRecyclerView = (RecyclerView) findViewById(R.id.message_list);
        // analyst_pic=findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        // specify an adapter (see also next example)
        mAdapter = new SendersAdapter(MainActivity.this,messageList);
        mRecyclerView.setAdapter(mAdapter);

      //  fab.show();
        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(MainActivity.this, ComposeMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(
                notifier);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       // String[] arg = new String[] { "DISTINCT "+MessageColumns.SENDER };
        switch (id) {
            case LOADER_FETCH_MESSAGES:
                return new CursorLoader(this, MessageColumns.CONTENT_URI, null, null, null, null);

            case LOADER_READ_INBOX:
                pg.setVisibility(View.VISIBLE);
                String[] reqCols = new String[] {"_id", "address", "body"};
                return new CursorLoader(this, INBOX_URI, reqCols, null, null, null);


            default:
                return new CursorLoader(this, MessageColumns.CONTENT_URI, null, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        messageList.clear();
        if(loader.getId()==LOADER_FETCH_MESSAGES) {
            if (data != null) {

                while (data.moveToNext()) {
                    MessageCursor cursor = new MessageCursor(data);
                    Message message = new Message();
                    message.setMessageId(String.valueOf(cursor.getMessageId()));
                    message.setIsReceived(cursor.getIsReceived());
                    message.setIsSenderInPhoneContact(cursor.getIsSenderInPhoneContact());
                    message.setMessageText(cursor.getMessageText());
                    message.setReceiver(cursor.getReceiver());
                    message.setSender(cursor.getSender());
                    message.setTime(cursor.getTime());
                    message.setIs_read(cursor.getIsRead());
                    messageList.add(message);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
        else if(loader.getId()==LOADER_READ_INBOX)
        {

            if (data != null) {
                while (data.moveToNext()) {

                    String address = "";
                    String body = "";
                    address = data.getString(data.getColumnIndex("address"));
                    body = data.getString(data.getColumnIndex("body"));
                    Intent updateService=new Intent(MainActivity.this, UpdateService.class);
                    updateService.putExtra("message", body);
                    if(data.isLast())
                    {
                        updateService.putExtra("is_last",true);
                    }
                    else
                    {
                        updateService.putExtra("is_last",false);
                    }

                    updateService.putExtra("phno", address);
                    startService(updateService);

                   // Log.d("inbox", "details" + address + "_" + body);
                }

                SharedPreferences.Editor ed=sp.edit();
                ed.putBoolean(IS_INBOX_SYNCED, true);
                ed.apply();

                mAdapter.notifyDataSetChanged();
                }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    private class InsertNotifier extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
              pg.setVisibility(View.GONE);
            Log.d("onReceive", "details");
            getLoaderManager().initLoader(LOADER_FETCH_MESSAGES, null, MainActivity.this);
            mAdapter.notifyDataSetChanged();
        }
    }
}
