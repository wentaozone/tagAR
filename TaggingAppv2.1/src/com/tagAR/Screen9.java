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
 * @description: This activity is the settings page where the user can manage a number of app settings and reset his password by giving in input his present password and 
 * the new password. We are asking for the present password so that only the user to whom the account belongs to
 * and knows the current password can change the password. 
 */

/**
 * Change in Version 2.1
 * Description has been updated 
 * 
 */

package com.tagAR;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tagAR.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Screen9 extends Activity implements OnClickListener{
    
	
	/**
	 * Change in Version 2.1 
	 * I have changed the urls to the server code from http://worldofpakistan.net/tagar/$fileName where $fileName is the file name
	 * to http://worldofpakistan.net/tagar/$fileName2_1 where $fileName2_1 is the new file name so that i can provide backward compatibility to 
	 * the apps which are of version 2.0. From now onwards if there is any change in the server code then i will keep the old code for backward 
	 * compatibility and use new code and will name the file's name to the latest version of the android app. I have made a fix to the code 
	 * which goes with the fix that i have made in Version 2.1 
	 * 
	 */
	
	private EditText presentPassword;
	private EditText newPassword;
	private Button resetPassword;
	private static Context context;
	private static String presentPasswordText;
	private static String newPasswordText;
	private static String emailText;
	private static ProgressDialog progressDialog;
	private Handler handler = new Handler();
	private static String toastMessage;
	private static ArrayList<String>userData;
	private CheckBox downloadImages;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.screen9);
        
        
        presentPassword=(EditText)findViewById(R.id.presentPassword);
        newPassword=(EditText)findViewById(R.id.newPassword);
        resetPassword=(Button)findViewById(R.id.resetButton);
        resetPassword.setOnClickListener(this);
        downloadImages=(CheckBox)findViewById(R.id.imageCheck);
        downloadImages.setOnClickListener(this);
              
        context=this;
        
        Bundle bundle=getIntent().getExtras();
        
        userData=bundle.getStringArrayList("userData");
        
        
        /**
         * Addition in Version 2.0
         * I am using shared preferences to store the setting of whether the user wants to download the images
         * associated with the tags or not. 
         */
        
        /**
         * Change in Version 2.1
         * I will set the share preference for the setting of whether to allow the app to download and upload images to false. The user
         * can change this setting in the Settings of the app.
         * 
         */
        
        SharedPreferences settings = getSharedPreferences("tagARPref", 0);
        Boolean downloadImageTags = settings.getBoolean("downloadImageTags", false);
        
        if(true==downloadImageTags)
        {
        	downloadImages.setChecked(true);
        }//end if
        else
        {
        	downloadImages.setChecked(false);
        }//end else
        
       
    }

	/**
	 * 
	 * source for showing Toast notification to the ui thread from a thread.
	 * http://www.codeproject.com/Articles/109735/Toast-A-User-Notification
	 * 
	 * I need to pass a handler which can send messages to the ui thread.
	 * 
	 */
	
	  public void showToast() {
		          handler.post(new Runnable() {
		               public void run() {
		                   Toast.makeText(getApplicationContext(),
		                           toastMessage, Toast.LENGTH_SHORT).show();
		               }
		           });
		      }
	

	@Override
	public void onClick(View view) {
		
		int viewId=view.getId();
		
		
		if(R.id.resetButton==viewId)
		{//the login button has been pressed
			
			
			presentPasswordText=presentPassword.getText().toString();
			presentPasswordText=presentPasswordText.replace(" ", "");
			
			newPasswordText=newPassword.getText().toString();
			newPasswordText=newPasswordText.replace(" ", "");
			
			if((presentPasswordText.equals(" ") || presentPasswordText.equals("")) || (newPasswordText.equals(" ") || newPasswordText.equals("")))
			{//if the password contains just space or no value at all then do not allow to continue.
			
				Toast.makeText(this, "Please do not leave any of the fields blank.", 1000).show();
				
			}//end if
			else
			{
				
				/**
				 *
				 *check for internet connection. if the internet connection is not enabled then ask the user to enable it.
				 *http://stackoverflow.com/questions/4238921/android-detect-whether-there-is-an-internet-connection-available
				 * 
				 */
				
			    
		        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo networkStatus= connectivityManager.getActiveNetworkInfo();
		        
			        
			        if(networkStatus!=null)
			        {
			        	//source for reading and writing to a url. http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
			        	//i am posting the data via the POST method
			        	progressDialog = ProgressDialog.show(context, "","Please wait...", true);
			        	progressDialog.setCancelable(true);
			        	
			        	new Thread() {
			        		
							public void run() {
			        	
					        	 try {
									
									/**
									 * 
									 *i am encoding the values into base64 encoding to provide a certain amount of security.
									 *i do not want to send the data as plain text over the airwaves
					        		 *i have chosen base64 instead of other encoding schemes such as md5 has, because i want 
					        		 *a 2 way encryption scheme which can be encoded and also be decoded because on the server side
					        		 *i need to decode the data to be able to perform operations
									 * 
									 */
					        		
					        		// Log.d("app userdata",userData.get(1));
					        		 
									emailText=Base64.encodeToString(userData.get(1).getBytes(),Base64.DEFAULT);
									presentPasswordText=Base64.encodeToString(presentPasswordText.getBytes(),Base64.DEFAULT);
									newPasswordText=Base64.encodeToString(newPasswordText.getBytes(),Base64.DEFAULT);
																				
									 byte [] decodedBytes;
									 String data;
						             String decodedString;
						             StringBuffer stringComplete=new StringBuffer();
						             
						             URL url = new URL("http://worldofpakistan.net/tagar/resetpassword2_1.php");
						             URLConnection connection = url.openConnection();
						             connection.setDoOutput(true);
						            
						             OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
						             out.write("email="+emailText+"&presentPassword="+presentPasswordText+"&newPassword="+newPasswordText);//i am sending data via the post method
						             out.close();
						
						             BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						             		             
						             while ((data = in.readLine()) != null) 
						             {
						            	 stringComplete.append(data);
		
						             }//end while
								        			        	             
						             in.close();
						             
						             decodedBytes=Base64.decode(stringComplete.toString(),Base64.DEFAULT);
						             decodedString=new String(decodedBytes);
						            // Toast.makeText(this, decodedString, 1000).show();
						           
						          
						             int pos=decodedString.indexOf("],",0);
						            
						             int pos1=0;
						             String num;
						             Integer numOfTerms;
						             try
						             {
						            	 num=decodedString.substring(2, pos);//i am starting from 2 because [[1 to get 1 i will have to place the pointer just before 1, which lies at 2. at 0 lies the first [, at 1 lies the second [, and at 2 lies the 1(or the first character of the number in the string 
						             	 numOfTerms=Integer.parseInt(num);
						             }//end if
						             catch(Exception e)
						             {
						            	 numOfTerms=-3;
						             }//end else
						             
						           //  Log.d("value of numOfTerms", ((Integer)numOfTerms).toString());
						             
						             ArrayList<String> userData=new ArrayList<String>();
						            // Toast.makeText(this, numOfTerms.toString(), 1000).show();
						             pos=pos+3;
						             String sub="";
	
						             
						             if(numOfTerms==1)
						             {
						            	 	pos1=decodedString.indexOf("]", pos);
							            	sub=decodedString.substring(pos, pos1);
							            	
							            	try
							            	{
							            		 Integer term=Integer.parseInt(sub);
							            		
							            		 /**
										             * Log.d("term", ((Integer)term).toString());
										             *the reason why i am comparing in this fashion where the comparing value is on the left side while the value 
										             *to be compared is on the right side, rather than the normal other way round, is because if i miss using two equals (==) 
										             *for equivalent and use just equal as in assignment (=) then upon compilation i will be informed of such an error, as in this 
										             *case a literal value can not be assigned some other value. 
										             *
										             */
				
							            		 
							            		 if(-1==term)
									             {
									            	
									            	 progressDialog.dismiss();
									            	 toastMessage="Please do not leave any of the fields blank.";
									            	 showToast();//i am calling the public function which will display a Toast notification by using the handler
									             }//end if
									             else if(-2==term)
									             {
									            	
									            	 progressDialog.dismiss();
									            	 toastMessage="No user exists for the given details. Please make sure that the details are correct.";
									            	 showToast();//i am calling the public function which will display a Toast notification by using the handler
									             }//end else if
									             else
									             {
									            	 progressDialog.dismiss();
									            	 toastMessage="The password has been reset.";
									            	 showToast();//i am calling the public function which will display a Toast notification by using the handler
									            	 
									             }//end else
							            		 
							            	}//end try
							            	catch(Exception e)
							            	{
							            		//e.printStackTrace();
							            	}//end catch
						            	 
						             }//end if
						             else
						             {
						            	 
						            	 
						             }//end else
	
							        
					        	 }//end try
					        	 catch(Exception e)
					        	 {
					        		 
					        	 }//end catch
							}
							
			        	}.start();
				        	
			        	
			        }//end if
			        else
			        {
			        	Toast.makeText(this, "No Internet connection detected. Please enable your internet connection.", 1000).show();
			       	 	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);//open up the systems screen which allows me to set the internet settings
			        
			        }//end else
				
			    
			        
			}//end else
			
				        
		}//end else if
		else if(R.id.imageCheck==viewId)
		{
			
			if(downloadImages.isChecked())
			{
				
			  SharedPreferences settings = getSharedPreferences("tagARPref", 0);//keep the preference private
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putBoolean("downloadImageTags",true);
		      editor.commit();

			}//end if
			else
			{
				  SharedPreferences settings = getSharedPreferences("tagARPref", 0);//keep the preference private
			      SharedPreferences.Editor editor = settings.edit();
			      editor.putBoolean("downloadImageTags",false);
			      editor.commit();
			}//end else
			
		}//end else if			
		else
		{
			
		}//end else
		
		
	}

	
	/**********onKeyDown function*********/
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{//this function will work with all the Android versions 
		//i am overriding the back button and the home button



	
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{


			Intent intent =new Intent("com.TagScreen5");
			intent.putStringArrayListExtra("userData",userData);
			startActivity(intent);                                              
			finish();

		}//end if
		else if(keyCode==KeyEvent.KEYCODE_MENU)
		{
			

			
		}//end else if


		return super.onKeyDown(keyCode, event);
	}
	
}