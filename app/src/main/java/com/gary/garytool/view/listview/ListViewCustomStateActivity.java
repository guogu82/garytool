package com.gary.garytool.view.listview;

import android.app.ListActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.view.MessageListItem;


public class ListViewCustomStateActivity extends ListActivity {

    private Message[] messages=new Message[]{
            new Message("Gas bill overdue", true),
            new Message("Congratulations, you've won!", true),
            new Message("I love you!", false),
            new Message("Please reply!", false),
            new Message("You ignoring me?", false),
            new Message("Not heard from you", false),
            new Message("Electricity bill", true),
            new Message("Gas bill", true), new Message("Holiday plans", false),
            new Message("Marketing stuff", false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new ArrayAdapter<Message>(this,-1,messages){
            private LayoutInflater inflater=LayoutInflater.from(getContext());

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView==null)
                {
                    convertView=inflater.inflate(R.layout.listview_item_message_state,parent,false);
                }
                MessageListItem messageListItem= (MessageListItem) convertView;
                TextView tv= (TextView) convertView.findViewById(R.id.id_msg_item_text);
                tv.setText(getItem(position).message);
                messageListItem.setMessageReaded(getItem(position).readed);
                return convertView;
            }
        });
    }

    private static class Message {

        private String message;
        private boolean readed;

        private Message(String msg, boolean state) {
            this.message = msg;
            this.readed = state;
        }

    }
}
