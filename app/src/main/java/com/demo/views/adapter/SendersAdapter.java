package com.demo.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.demo.R;
import com.demo.model.Message;
import com.demo.provider.message.MessageColumns;
import com.demo.provider.message.MessageContentValues;

import java.util.ArrayList;

/**
 * Created by SHASHANK on 14-06-2015.
 */
public class SendersAdapter extends RecyclerSwipeAdapter<SendersAdapter.SimpleViewHolder> {
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView sender_name;
        TextView message;
        Button buttonDelete;
        CardView cardView;
        TextView swipe_label;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            sender_name = (TextView) itemView.findViewById(R.id.sender_name);
            message = (TextView) itemView.findViewById(R.id.sender_number);
            swipe_label = (TextView) itemView.findViewById(R.id.swipe_label);
            buttonDelete = (Button) itemView.findViewById(R.id.delete);
            cardView=(CardView)itemView.findViewById(R.id.cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(getClass().getSimpleName(), "onItemSelected: " + sender_name.getText().toString());
                    Toast.makeText(view.getContext(), "onItemSelected: " + message.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private Context mContext;
    private ArrayList<Message> mDataset;


    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);


    public SendersAdapter(Context context, ArrayList<Message> objects) {
        this.mContext = context;
        this.mDataset = objects;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.senders_row, parent, false);
        return new SimpleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Message item = mDataset.get(position);
        if(item.getIsSenderInPhoneContact().equals("true"))
        {
            if(!item.getIs_read().equals("true")) {
                viewHolder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.solid_white));
            }
            else
            {
                viewHolder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.gray_btn_bg_color));
            }
            viewHolder.buttonDelete.setText("Mark Read");
            viewHolder.swipe_label.setText("Mark Read?");
        }
        else
        {
            viewHolder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.red_btn_bg_color));
            viewHolder.buttonDelete.setText("Yes, Delete");
            viewHolder.swipe_label.setText("Delete Message?");
        }
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                // YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!item.getIsSenderInPhoneContact().equals("true")) {
                    String where = MessageColumns.SENDER + "=?";
                    String[] args = new String[]{item.getSender()};
                    mContext.getContentResolver().delete(MessageColumns.CONTENT_URI, where, args);
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                    mItemManger.closeAllItems();
                    Toast.makeText(view.getContext(), "Deleted " + viewHolder.message.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MessageContentValues values=new MessageContentValues();
                    values.putIsRead("true");
                    String where = MessageColumns.SENDER+"=?";
                    String[] args = new String[] { item.getSender() };
                    mContext.getContentResolver().update(MessageColumns.CONTENT_URI, values.values(), where, args);
                    viewHolder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.gray_btn_bg_color));
                    viewHolder.message.setTypeface(null, Typeface.NORMAL);
                    viewHolder.sender_name.setTypeface(null, Typeface.NORMAL);

                }
            }
        });
        viewHolder.message.setText(item.getMessageText());
        viewHolder.sender_name.setText(item.getSender());
        mItemManger.bindView(viewHolder.itemView, position);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}

