package com.dr.livedatabus;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
        
        LiveDataBus.get().with("华为", String.class).observe(this, new Observer<String>() {
            
            
            @Override
            public void onChanged(String s) {
                if(!TextUtils.isEmpty(s)) {
                    Log.i("MainActivity2", s);
                }
                
            }
        });
        //        LiveDataBus.get().with("三星", SanXing.class).observe(this, new Observer<SanXing>() {
        //            @Override
        //            public void onChanged(SanXing sanXing) {
        //                 if(sanXing!=null){
        //                     Log.i("MainActivity", sanXing.toString());
        //                 }
        //            }
        //        });
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
    
    /**
     * 发布消息
     * @param view
     */
    public void sendMessage(View view) {
        LiveDataBus.get().with("华为", String.class).postValue("华为-MateX");
        //        LiveDataBus.get().with("三星", SanXing.class).postValue(new SanXing("三星","x-234"));
        
    }
    
}
