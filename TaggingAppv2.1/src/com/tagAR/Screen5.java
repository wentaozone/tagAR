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
 * @description: This activity is the main menu. The options that the user can perform are shown in this menu.
 * 
 */

package com.tagAR;


import java.util.ArrayList;

import provider.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.tagAR.R;


public class Screen5 extends Activity implements OnClickListener {

	private ImageView tagLocation;
	private ImageView about;
	private ImageView settings;
	private ImageView exit;
	
	private static Provider locationProvider;
	private static ProgressDialog dialog;
	
	private static Context context;
		
	private static Double latitude=0.0;
	private static Double longitude=0.0;
	
    private static ArrayList<String>userData;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.screen5);
        context=this;
        
        tagLocation=(ImageView)findViewById(R.id.tagLocation);
        about=(ImageView)findViewById(R.id.about);
        settings=(ImageView)findViewById(R.id.settings);
        exit=(ImageView)findViewById(R.id.exit);
        
        
        tagLocation.setOnClickListener(this); 
        about.setOnClickListener(this);
        settings.setOnClickListener(this);
        exit.setOnClickListener(this);
        
        Bundle bundle=getIntent().getExtras();
        
        userData=bundle.getStringArrayList("userData");
		locationProvider=new Provider();
		

                
    }

	

	@Override
	protected void onPause() {
		super.onPause();
		
		/**
		 *Bug Fix
		 *Change in Version 0.4 from Version 0.3
		 *I am not calling this function here, because if the gps and wireless providers are not enabled then the app crashes as
		 *the user is redirected to the settings screen. 
		 */
		//locationProvider.stopUpdates();
	}



	@Override
	public void onClick(View view) {
		
		int viewId=view.getId();
				
		/**
		 * check for internet connection. if the internet connection is not enabled then ask the user to enable it.
		 * 
		 * http://stackoverflow.com/questions/4238921/android-detect-whether-there-is-an-internet-connection-available
		 * 
		 **/
      
        
        if(R.id.tagLocation==viewId)
        {
        	
        	 ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo networkStatus= connectivityManager.getActiveNetworkInfo();

     		locationProvider.registerLocationListeners((LocationManager)getSystemService(LOCATION_SERVICE));

			
        	if(networkStatus!=null)
        	{
        		
                
                 
        		boolean isNetworkProviderEnabled= locationProvider.getLocationManager().isProviderEnabled (LocationManager.NETWORK_PROVIDER);
        		boolean isGPSEnabled= locationProvider.getLocationManager().isProviderEnabled (LocationManager.GPS_PROVIDER);
        		
        		if(isNetworkProviderEnabled && isGPSEnabled)
        		{//network provider is on.
        			
            		dialog = ProgressDialog.show(this, "","Please wait...Getting your geo-location coordinates...", true);
            		dialog.setCancelable(true);
            		

            		new Thread() {

            			public void run() {

            			try{

            				//sleep(10000);//sleep for 10 seconds
            				
            				while(null==locationProvider.getCurrentLocation())
            				{
            					
            				}//end while

            			} catch (Exception e) {

            				//Log.e("tag", e.getMessage());

            			}
            				dialog.dismiss();  
            				latitude=locationProvider.getCurrentLocation().getLatitude();
            				longitude=locationProvider.getCurrentLocation().getLongitude();
            				
            				// Log.d("app",((Float)locationProvider.getCurrentLocation().getAccuracy()).toString());
            				//Log.d("app", latitude.toString());
            				//Log.d("app", longitude.toString());
            				
            				locationProvider.stopUpdates();//stop getting updates. Stop getting the updates as soon as i have the data. I do not want to waste the battery on looking for updates all the time.
            				
            				Intent intent=new Intent("com.TagScreen6");
     		    	        intent.putExtra("latitude", latitude);
     		    	        intent.putExtra("longitude", longitude);
     		    	        intent.putExtra("accuracy",locationProvider.getCurrentLocation().getAccuracy());
     		    	        intent.putStringArrayListExtra("userData", userData);
     		    	        context.startActivity(intent);
     		           		finish();
            			
            			}
            			
            			}.start();
            		
            		
            		
        		
        		}//end if
        		else
        		{//both of the providers must be enabled
        			Toast.makeText(this, "Please enable the wireless network(for location based services) and GPS. You need to enable both of the settings to be able to use this app.", 1000).show();
    				startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);//open up the system screen which allows me to enable the GPS
    				
        		}//end else
        		
        		
        		 
        	}//end if
        	else
        	{
	        	Toast.makeText(this, "No Internet connection detected. Please enable your internet connection.", 1000).show();
	        	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);//open up the systems screen which allows me to set the internet settings

        	}//end else
        	

        }//end if
        else if(R.id.about==viewId)
        {		
        	
        	 Intent intent=new Intent("com.TagScreen7");
        	 intent.putStringArrayListExtra("userData", userData);
        	 context.startActivity(intent);
        	 finish();
    	        	
        	
        }//end else if
        else if(R.id.settings==viewId)
        {//settings, reset password
        	
        		//Log.d("appuserData",((Integer)userData.size()).toString());
        		Intent intent=new Intent("com.TagScreen9");
        		intent.putStringArrayListExtra("userData", userData);
        		context.startActivity(intent);
        		finish();
        }//end else if
        else if(R.id.exit==viewId)
        {
        	finish();
        	
        }//end else if
	}


}