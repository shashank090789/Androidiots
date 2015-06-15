package com.demo.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.demo.*;
import com.demo.R;
import com.demo.model.Contact;

import java.util.ArrayList;

/**
 * Created by SHASHANK on 14-06-2015.
 */
public class ContactSuggestionAdapter extends ArrayAdapter<Contact> implements Filterable{
    private ComposeMessageActivity activity;
    private static LayoutInflater inflater=null;
    private ArrayList<Contact> contactList;
    public ContactSuggestionAdapter(ComposeMessageActivity activity,ArrayList<Contact> contactList)
    {
        super(activity, R.layout.receipient_row, contactList);
        this.activity=activity;
        this.contactList=contactList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Contact getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        ViewHolder holder;
        if(convertView==null)
        {
            view = inflater.inflate(R.layout.receipient_row, null);
            holder = new ViewHolder();
            holder.contactName= (TextView) view.findViewById(R.id.recepient_name);
            holder.cotactNumber= (TextView) view.findViewById(R.id.recepient_no);
            view.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        Contact contact=contactList.get(position);
        holder.contactName.setText(contact.getContact_name());
        holder.cotactNumber.setText(contact.getContact_no());
        return view;
    }

    @Override
    public Filter getFilter() {
        return activity.new ListFilter();
    }

    static class ViewHolder {
        TextView contactName;
        TextView cotactNumber;



    }
}
