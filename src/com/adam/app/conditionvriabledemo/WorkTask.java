/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: WorkTask.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/6/13
 */

package com.adam.app.conditionvriabledemo;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.ConditionVariable;
import android.os.SystemClock;

import com.adam.app.conditionvriabledemo.MainActivity.UIHandler;

/**
 * <h1>WorkTask</h1>
 * 
 * @autor AdamChen
 * @since 2018/6/13
 */
public class WorkTask implements Runnable {

    //Condition variable lock
    private ConditionVariable mLock = new ConditionVariable();
    
    //UI handler
    private UIHandler mUIHandler;
    
    //Control task life cycle
    AtomicBoolean mStop = new AtomicBoolean(false);
    
    public WorkTask(UIHandler handler) {
        this.mUIHandler = handler;
    }
    
    @Override
    public void run() {
        
        while (mStop.get() == false) {
            
            //Show waiting text
            mUIHandler.sendEmptyMessage(UIHandler.UPDATE_WAITING_TEXT);
            
            //The lock enter to lock state
            mLock.block();
            
            //Show unlock text
            mUIHandler.sendEmptyMessage(UIHandler.UPDATE_UNLOCK_TEXT);
            
            //Delay 3 sec
            SystemClock.sleep(3000L);
            
            //Reset lock state
            mLock.close();
        }
        
        //Show task finish
        mUIHandler.sendEmptyMessage(UIHandler.SHOW_TASK_FINISH);
        
    }
    
    /**
     * 
     * <h1>openLock</h1>
     *     Let the lock enter to the unlock state
     * 
     * @return void
     *
     */
    public void openLock() {
        mLock.open();
    }
    
    /**
     * 
     * <h1>stopTask</h1>
     *
     * @return void
     *
     */
    public void stopTask() {
        
        mStop.set(true);
        
        mLock.open();
    }

}
