/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: MainActivity.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/6/13
 */

package com.adam.app.conditionvriabledemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    //State text
    private TextView mTv_state;
    
    //UI handler
    private UIHandler mHandler;
    
    //Work task
    private WorkTask mTask;
    
    //Thread object
    private Thread mThread;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mHandler = new UIHandler();
        
        mTask = new WorkTask(mHandler);
        
        mTv_state = (TextView)this.findViewById(R.id.text_state);
        
        //Start work thread
        mThread = new Thread(mTask, mTask.getClass().getSimpleName());
        mThread.start();
    }

    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        //Stop work task
        mTask.stopTask();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    /**
     * 
     * <h1>onOpenLock</h1>
     *
     * @param v
     * @return void
     *
     */
    public void onOpenLock(View v) {
        mTask.openLock();
    }
    
    /**
     * 
     * <h1>onStopTask</h1>
     *
     * @param v
     * @return void
     *
     */
    public void onStopTask(View v) {
        mTask.stopTask();
    }
    
    public class UIHandler extends Handler {

        public static final int UPDATE_WAITING_TEXT = 0;
        
        public static final int UPDATE_UNLOCK_TEXT = 1;
        
        public static final int SHOW_TASK_FINISH = 2;
        
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            switch(msg.what) {
            case UPDATE_WAITING_TEXT:
                mTv_state.setText("State: Waiting...");
                break;
            case UPDATE_UNLOCK_TEXT:
                mTv_state.setText("State: Unlock...");
                break;
            case SHOW_TASK_FINISH:
                mTv_state.setText("State: task finish...");
                break;
            default:
                break;
            }
        }

    }
}
