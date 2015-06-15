package com.demo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.demo.model.Message;
import com.demo.provider.message.MessageColumns;
import com.demo.provider.message.MessageCursor;
import com.demo.views.adapter.SendersAdapter;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btn_send;
    private ArrayList<Message> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          getLoaderManager().initLoader(4, null, this);
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
        return new CursorLoader(this, MessageColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        messageList.clear();
if(data!=null)
{
    while(data.moveToNext())
    {
        MessageCursor cursor=new MessageCursor(data);
        Message message=new Message();
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

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
