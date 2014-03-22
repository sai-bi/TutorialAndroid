package com.example.tutorial_android.app;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowConatnct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_conatnct);

        ListView contact_listview = (ListView)(findViewById(R.id.contact_list_view));
        ArrayList<HashMap<String, Object>> list_item = new ArrayList<HashMap<String,Object>>();

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        int contact_id_index = 0;
        int name_index = 0;
        //int img_index = 0;

        if(cursor.getCount() > 0){
            contact_id_index = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            name_index = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            //img_index = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);
        }
        while(cursor.moveToNext()){
            String contact_id = cursor.getString(contact_id_index);
            String name = cursor.getString(name_index);
            Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id,
                    null,null);
            int phone_index = 0;
            if(phone.getCount() > 0){
                phone_index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while(phone.moveToNext()){
                String phone_num = phone.getString(phone_index);
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("ItemImage",R.drawable.figure);
                map.put("Name",name);
                map.put("Phone",phone_num);
                list_item.add(map);
            }
        }

        SimpleAdapter list_item_adapter = new SimpleAdapter(this,list_item,R.layout.contact_item,
                new String[] {"ItemImage","Name","Phone"},
                new int[] {R.id.ItemImage,R.id.ItemTitle, R.id.ItemText});

        contact_listview.setAdapter(list_item_adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_conatnct, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
