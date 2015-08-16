package com.gary.garytool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ListViewActivity extends ActionBarActivity {
    private ListView listView;
    private  static final int BOTTOM_MENU_WITH_VIEWPAGER=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView= (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data=new ArrayList<String>();
        data.add("one");
        data.add("two");
        data.add("three");
        data.add("BottomMenuWithViewpager");
        return data;
    }

        public final class ViewHolder {
            public TextView item;
        }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return  getData().size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.listview_item, null);
                holder.item = (TextView)convertView.findViewById(R.id.list_item);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.item.setText(getData().get(position));



            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (position)
                    {    case 0:
                        intent =new Intent(ListViewActivity.this,ListViewDemo1Activity.class);
                        startActivity(intent);
                        break;
                        case 1:
                            intent =new Intent(ListViewActivity.this,ListViewDemo2Activity.class);
                            startActivity(intent);
                             break;
                        case 2:
                            intent =new Intent(ListViewActivity.this,ListViewDemo3Activity.class);
                            startActivity(intent);
                            break;
                        case BOTTOM_MENU_WITH_VIEWPAGER:
                            intent =new Intent(ListViewActivity.this,BottomMenuWithViewpager.class);
                            startActivity(intent);
                            break;
                    }


                }
            });
            return convertView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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
}
