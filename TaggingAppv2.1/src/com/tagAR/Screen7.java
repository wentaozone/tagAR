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
 * @description: This activity is a simple About page which gives some information to the user about the app and the company.
 * 
 */

package com.tagAR;




import java.util.ArrayList;

import com.tagAR.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;



public class Screen7 extends Activity{

	
    private  ArrayList<String>userData;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.screen7);
       
        Bundle bundle=getIntent().getExtras();
        
        userData=bundle.getStringArrayList("userData");
    }

	
	/**********onKeyDown function*********/
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{//this function will work with all the Android versions 
		//i am overriding the back button and the home button



	
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{


			Intent intent =new Intent("com.TagScreen5");
			intent.putStringArrayListExtra("userData", userData);
			startActivity(intent);                                              
			finish();

		}//end if
		else if(keyCode==KeyEvent.KEYCODE_MENU)
		{
			

			
		}//end else if


		return super.onKeyDown(keyCode, event);
	}
	

}