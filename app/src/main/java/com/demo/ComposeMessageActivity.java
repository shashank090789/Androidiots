package com.demo;

import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import com.demo.model.Contact;
import com.demo.views.adapter.ContactSuggestionAdapter;
import com.demo.views.adapter.SendersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class ComposeMessageActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private AutoCompleteTextView et_to;
    private EditText et_message;
    private Button btn_send;
    ArrayList<Contact> contactList;
    private ContactSuggestionAdapter adapter;
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER

            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        getSupportLoaderManager().initLoader(0, null, this);
        et_to= (AutoCompleteTextView) findViewById(R.id.et_to);
        et_message= (EditText) findViewById(R.id.et_message);
        btn_send= (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phno=et_to.getText().toString();
                String msge=et_message.getText().toString();
                if(!phno.equals(""))
                {
                    if(!msge.equals(""))
                    {
                        sendMessage(phno,msge);
                        finish();
                    }
                    else {
                        Toast.makeText(ComposeMessageActivity.this,"Please type message",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(ComposeMessageActivity.this,"Please type receipient no.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose_message, menu);
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
        return new CursorLoader(
                this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        try{
             contactList=new ArrayList<Contact>();
            while(data.moveToNext())
            {

                String name=data.getString(3);
                String phoneNumber=data.getString(4).replaceAll("[\\D]", "");
                if(phoneNumber.length()==10||phoneNumber.length()>10)
                {
                    Contact contact=new Contact();
                    if(phoneNumber.length()==10)
                    {
                       contact.setContact_name(name);
                        contact.setContact_no(phoneNumber);
                        //System.out.println(name+".................."+phoneNumber);
                    }
                    else if(phoneNumber.length()>10)
                    {
                        //if(phoneNumber.startsWith("0"))
                        {
                            phoneNumber=phoneNumber.substring(phoneNumber.length()-10);
                            contact.setContact_name(name);
                            contact.setContact_no(phoneNumber);
                            //System.out.println(name+".................."+phoneNumber);
                        }
					/*else if(phoneNumber.startsWith("91"))
					{
						phoneNumber=phoneNumber.substring(phoneNumber.length()-10);
						contact.put("name", name);
						contact.put("phNo", "+91"+phoneNumber);
						// System.out.println(name+".................."+phoneNumber);
					}*/
                    }
                    contactList.add(contact);
                }
               adapter=new ContactSuggestionAdapter(ComposeMessageActivity.this,contactList);
                et_to.setAdapter(adapter);
                et_to.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        et_to.setText(adapter.getItem(position).getContact_no());

                    }
                });
                // get display name
                // String phoneNumber = arg1.getString(arg1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // get phone number
                // System.out.println(name+".................."+phoneNumber);
            }

        }catch(Exception e){}
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // NOTE: this function is *always* called from a background thread,
            // and
            // not the UI thread.
            String constraintStr = constraint.toString().toLowerCase(Locale.getDefault());
            FilterResults result = new FilterResults();

            if (constraint != null && constraint.toString().length() > 0) {
                ArrayList<Contact> filterItems = new ArrayList<Contact>();

                synchronized (this) {
                    for (Contact contact : contactList) {
                        if (contact.getContact_name().toLowerCase(Locale.getDefault()).contains(constraintStr)
                                ) {
                            filterItems.add(contact);
                        }
                    }
                    result.count = filterItems.size();
                    result.values = filterItems;
                }
            } else {
                synchronized (this) {
                    result.count = contactList.size();
                    result.values = contactList;
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Contact> filtered = (ArrayList<Contact>) results.values;
            if (filtered!=null && filtered.size() <= 0) {

            } else {

                adapter = new ContactSuggestionAdapter (ComposeMessageActivity.this, filtered);
                adapter.notifyDataSetChanged();
                et_to.setAdapter(adapter);

            }
            // sort array and extract sections in background Thread

        }

    }
    private void sendMessage(String phoneNumber,String message)
    {
        try{
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phoneNumber, null, message, null, null);
        }catch(Exception e){}
    }
}
