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
 * @description: This activity allows the user to login into the app. Upon submission of the form the internet connection is 
 * checked if it is enabled. If not then the user is shown the settings screen to enable the internet connection.
 * Then the app is connected to the server where the user's login details are checked.
 */

package com.tagAR;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Screen3 extends Activity implements OnClickListener{
    
	/**
	 * Change in Version 2.1 
	 * I have changed the urls to the server code from http://worldofpakistan.net/tagar/$fileName where $fileName is the file name
	 * to http://worldofpakistan.net/tagar/$fileName2_1 where $fileName2_1 is the new file name so that i can provide backward compatibility to 
	 * the apps which are of version 2.0. From now onwards if there is any change in the server code then i will keep the old code for backward 
	 * compatibility and use new code and will name the file's name to the latest version of the android app. I have made a fix to the code 
	 * which goes with the fix that i have made in Version 2.1 
	 * 
	 */
	
	private TextView registerLink;
	private EditText email;
	private EditText password;
	private Button login;
	private static Context context;
	private static String emailText;
	private static String passwordText;
	private static ProgressDialog progressDialog;
	private Handler handler = new Handler();
	private static String toastMessage;
	private TextView forgotPass; 
	private static ArrayList<String>userAccounts;// i have to make this static because then i get null value in the userAccounts variable when i try to access it in the onClick function. 
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.screen3);
        
        registerLink=(TextView)findViewById(R.id.registerLinkActivity);
        registerLink.setOnClickListener(this);
        
        email=(EditText)findViewById(R.id.emailLogin);
        password=(EditText)findViewById(R.id.passwordLogin);
        login=(Button)findViewById(R.id.loginButton);
        login.setOnClickListener(this);
              
        forgotPass=(TextView)findViewById(R.id.forgotPasswordLink);
        forgotPass.setOnClickListener(this);
        
        context=this;
        
        
        /**
         * 
         * addition in version 1.0
         * I am checking if any user account (email and password) have been retrieved.
         * If they have been retrieved then show them in the form
         * 
         */
        Bundle bundle=getIntent().getExtras();
        boolean emptyUserAccountsList=bundle.getBoolean("emptyUserAccountsList");
        
        
        if(true==emptyUserAccountsList)
        {//no user account has been loaded
        	//no need to do anything here as no data has been retrieved
        	userAccounts=new ArrayList<String>();
        	
        }//end if
        else
        {//a user account has been loaded
        	
        	/**
        	 * addition in version 1.0
        	 * show the email and password in the form
        	 * 
        	 */
        	
        	//Log.d("bundle email: ",bundle.getString("email"));
        	//Log.d("bundle password: ",bundle.getString("password"));
        	email.setText(bundle.getString("email"));
        	password.setText(bundle.getString("password"));
        	userAccounts=bundle.getStringArrayList("userAccounts");

        	
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
		
		
		if(R.id.registerLinkActivity==viewId)
		{//the registration link has been pressed
			
       	   Intent intent=new Intent("com.TagScreen4");
           intent.putExtra("emptyUserAccountsList",false);
           intent.putStringArrayListExtra("userAccounts",userAccounts);
           startActivity(intent);
           finish();
              
		}//end if
		else if(R.id.loginButton==viewId)
		{//the login button has been pressed
			
			
			emailText=email.getText().toString();
			passwordText=password.getText().toString();
					
			
			emailText=emailText.replace(" ", "");
			passwordText=passwordText.replace(" ","");
			
			if((emailText.equals(" ") || emailText.equals("")) || (passwordText.equals(" ") || passwordText.equals("")))
			{//if the email or password contains just space or no value at all then do not allow to continue.
			
				Toast.makeText(this, "Please do not leave any of the fields blank.", 1000).show();
				
			}//end if
			else
			{
				/**
				 * 
				 * I am checking for the format of the email address
				 * source: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
				 * 
				 */
				  Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			      Matcher m = p.matcher(emailText);
			      boolean result = m.matches();
			      
			      if(false==result)
			      {
			    	  Toast.makeText(this,"The format of the email address is not valid. Please enter again.", 1000).show();
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
					        		
					        		 
									emailText=Base64.encodeToString(emailText.getBytes(),Base64.DEFAULT);
									passwordText=Base64.encodeToString(passwordText.getBytes(),Base64.DEFAULT);
																				
									 byte [] decodedBytes;
									 String data;
						             String decodedString;
						             StringBuffer stringComplete=new StringBuffer();
						             
						             URL url = new URL("http://worldofpakistan.net/tagar/authenticate2_1.php");
						             URLConnection connection = url.openConnection();
						             connection.setDoOutput(true);
						            
						             OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
						             out.write("email="+emailText+"&password="+passwordText);//i am sending data via the post method
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
									            	 toastMessage="No user exists for the given login details. Please make sure that the details are correct or register a new acccount.";
									            	 showToast();//i am calling the public function which will display a Toast notification by using the handler
									             }//end else if
									             else if(-3==term)
									             {
									            	
									            	 progressDialog.dismiss();
									            	 toastMessage="Please login again, as there was a problem with the network.";
									            	 showToast();//i am calling the public function which will display a Toast notification by using the handler
									             }//end else if
									             else
									             {
									            	
									            	 
									             }//end else
							            		 
							            	}//end try
							            	catch(Exception e)
							            	{
							            		//e.printStackTrace();
							            	}//end catch
						            	 
						             }//end if
						             else if(numOfTerms==4)
						             {
	
						            	 pos1=decodedString.indexOf("]", pos);
						            	 sub=decodedString.substring(pos, pos1);
						            	 pos=pos1+3;
						            	 userData.add(sub);//user id
						            	 
						            	 pos1=decodedString.indexOf("]", pos);
						            	 sub=decodedString.substring(pos, pos1);
						            	 pos=pos1+3;
						            	 userData.add(sub);//email
	
						            	 pos1=decodedString.indexOf("]", pos);
						            	 sub=decodedString.substring(pos, pos1);
						            	 pos=pos1+3;
						            	 userData.add(sub);//first name
						            	 
						            	 pos1=decodedString.indexOf("]", pos);
						            	 sub=decodedString.substring(pos, pos1);
						            	 pos=pos1+3;
						            	 userData.add(sub);//last name
						            	 
						            	 	
						            	// Log.d("appuserDatascreen3",((Integer)userData.size()).toString());
						            	 						  
						            	 
						            	 /**
								    	   * addition in version 1.0
								    	   * i am checking if the login details (email and password)
								    	   * in the form are in the internal file which contains
								    	   * a list of emails and passwords which the user has used to login
								    	   * with. If it does exist, then do nothing and proceed as normal,
								    	   * but if no such email exists in the file then add it in the file and then
								    	   * continue as normal.
								    	   * 
								    	   */
								    	  
								    	    byte[] decodedBytes1;
								    	    String decodedEmail,decodedPassword;
								    	    int i=0;
								    	    boolean userAccountExists=false;
								    	    
								    	    while(i<userAccounts.size())
								    	    {
								    	    
								    	    	
									    	    decodedBytes1=Base64.decode(userAccounts.get(i).toString(),Base64.DEFAULT);
									    	    decodedEmail=new String(decodedBytes1);
												//Log.d("app email",decodedEmail);
																			
												decodedBytes1=Base64.decode(userAccounts.get(i+1).toString(),Base64.DEFAULT);
												decodedPassword=new String(decodedBytes1);
												//Log.d("app password",decodedPassword);
												
												//Log.d("form email",email.getText().toString());
												
												//Log.d("form password",password.getText().toString());
												
												if(email.getText().toString().equals(decodedEmail) && password.getText().toString().equals(decodedPassword))
												{
													userAccountExists=true;
													//Log.d("app userAccountExists","true");
													break;
													
												}//end if
													
												
												i=i+2;
								    	    }//end while
								    	  
								    	    /**
								    	     * addition in version 1.0
								    	     * only add the login details to the list if it does not
								    	     * have the login details already added.
								    	     * 
								    	     */
								    	    
								    	    if(false==userAccountExists)
								    	    {//no user account exists for the given login details so add to the list and add to the file
								    	    	userAccounts.add(Base64.encodeToString(email.getText().toString().getBytes(),Base64.DEFAULT));
								    	    	userAccounts.add(Base64.encodeToString(password.getText().toString().getBytes(),Base64.DEFAULT));
								    	    	
								    	    				    			
								    			ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
								    			byte[] buffer;
								    			ObjectOutput out1;
								    			FileOutputStream fos;
								    			try {
								    				fos = openFileOutput("tagARUserAccounts",Context.MODE_PRIVATE);

								    				out1 = new ObjectOutputStream(bos);
								    				out1.writeObject(userAccounts); 
								    				out1.close(); 

								    				buffer  = bos.toByteArray(); 
								    				
								    				fos.write(buffer);	


								    				int lengthBuffer=buffer.length;
								    				//Log.v("buffer length", ((Integer)lengthBuffer).toString());
								    				fos.close();

								    				fos = openFileOutput("tagARUserAccountsLength",Context.MODE_PRIVATE);
								    				String val=((Integer)lengthBuffer).toString();
								    				fos.write(val.getBytes());
								    				fos.close();

								    				fos.close();
								    			} catch (FileNotFoundException e1) {
								    				e1.printStackTrace();
								    			} catch (IOException e) {
								    				
								    				e.printStackTrace();
								    			}

								    	    	
								    	    	
								    	    }//end if
						            	 
						            	 
						            	 progressDialog.dismiss();
						            	 Intent intent=new Intent("com.TagScreen5");
							             intent.putStringArrayListExtra("userData",userData);
							             startActivity(intent);
							             finish();
							          
							            
						            	 
						             }//end else if
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
			        
			}//end else
			
				        
		}//end else if
		else if(R.id.forgotPasswordLink==viewId)
		{//reset password
			 
        	
             Intent intent=new Intent("com.TagScreen8");
             intent.putExtra("emptyUserAccountsList",false);
             intent.putStringArrayListExtra("userAccounts",userAccounts);
             startActivity(intent);
             finish();
             
			
		}//end else if
		else
		{
			
		}//end else
		
		
	}

}