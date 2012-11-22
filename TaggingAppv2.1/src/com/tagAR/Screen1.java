/**
 *  
 *tagAR is a tagging application developed for the Android Platform
 *Copyright (C) 2012  Mustafa Neguib, MN Tech Solutions
 *  
 *This file is part of tagAR.
 *
 *tagAR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *You can contact the developer/company at the following:
 *
 *Phone: 00923224138957
 *Website: www.mntechsolutions.net
 *Email: support@mntechsolutions.net , mustafaneguib@mntechsolutions.net
 *
 *
 */

/*
 * @author: Mustafa Neguib
 * @company: MN Tech Solutions
 * @applicationName: tagAR
 * @appType: This app is an augmented reality app which allows the user to tag locations
 * @version: 2.1 
 * @description: This activity is basically a splash screen. All it does is show a logo for a few seconds and then 
 * starts the next activity.
 */


package com.tagAR;


import com.tagAR.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Screen1 extends Activity {
    /** Called when the activity is first created. */
    

	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.screen1);
        
        
        
        // thread for displaying the SplashScreen
           Thread splashTread = new Thread() {
               @Override
               public void run() {
                   try {
                       int waited = 0;
                       while(_active && (waited < _splashTime)) {
                           sleep(100);
                           if(_active) {
                               waited += 100;
                           }
                       }
                   } catch(InterruptedException e) {
                       // do nothing
                   } finally {
                       
                	   startActivity(new Intent("com.TagScreen2"));
                	   finish();
                       
                   }
               }
           };
           splashTread.start();
        
    }
}