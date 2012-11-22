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
 */

/*
 * @author: Mustafa Neguib
 * @company: MN Tech Solutions
 * @applicationName: tagAR
 * @appType: This app is an augmented reality app which allows the user to tag locations
 * @version: 2.1
 * @description: This class provides with the functionality for setting up a GPS Provider or Network Provider and then use it.
 * @originalSource: http://devdiscoveries.wordpress.com/2010/02/04/android-use-location-services/
 * 
 */

package provider;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class Provider {

	private LocationManager locationManager;
    private LocationListener listenerCoarse;
    private LocationListener listenerFine;
	private boolean locationAvailable = true;
 	private Location currentLocation;

    
	 public Provider() {
		super();
		
	}

	public Provider(LocationManager locationManager,
			LocationListener listenerCoarse, LocationListener listenerFine,Location currentLocation) {
		super();
		this.locationManager = locationManager;
		this.listenerCoarse = listenerCoarse;
		this.listenerFine = listenerFine;
		this.currentLocation=currentLocation;
	}


	

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	public LocationListener getListenerCoarse() {
		return listenerCoarse;
	}

	public void setListenerCoarse(LocationListener listenerCoarse) {
		this.listenerCoarse = listenerCoarse;
	}

	public LocationListener getListenerFine() {
		return listenerFine;
	}

	public void setListenerFine(LocationListener listenerFine) {
		this.listenerFine = listenerFine;
	}

	public boolean isLocationAvailable() {
		return locationAvailable;
	}

	public void setLocationAvailable(boolean locationAvailable) {
		this.locationAvailable = locationAvailable;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	
	
	public void registerLocationListeners(LocationManager locationManager) {
			
	    	try{
	    		
	    		this.locationManager=locationManager;
	    		
			// Initialize criteria for location providers
			Criteria fine = new Criteria();
			fine.setAccuracy(Criteria.ACCURACY_FINE);
			Criteria coarse = new Criteria();
			coarse.setAccuracy(Criteria.ACCURACY_COARSE);
			
			// Get at least something from the device,
			// could be very inaccurate though
			currentLocation = locationManager.getLastKnownLocation(
				locationManager.getBestProvider(fine, true));
			
			if (null==listenerFine || null==listenerCoarse)
				createLocationListeners();
				
			// Will keep updating about every 500 ms until 
			// accuracy is about 1000 meters to get quick fix.
			locationManager.requestLocationUpdates(
				locationManager.getBestProvider(coarse, true), 
				500, 1000, listenerCoarse);
			// Will keep updating about every 500 ms until 
			// accuracy is about 50 meters to get accurate fix.
			locationManager.requestLocationUpdates(
				locationManager.getBestProvider(fine, true), 
				500, 50, listenerFine);
			
	    	}//end try
	    	catch(Exception e)
	    	{
	    		
	    	
	    	}//end catch
		}
	 
	 
	 /**
		* 	Creates LocationListeners
		*/
		private void createLocationListeners() {
			
			try
			{
				
				 listenerCoarse = new LocationListener() {
				public void onStatusChanged(String provider, 
					int status, Bundle extras) {
					switch(status) {
					case LocationProvider.OUT_OF_SERVICE:
					case LocationProvider.TEMPORARILY_UNAVAILABLE:
						locationAvailable = false;
						break;
					case LocationProvider.AVAILABLE:
						locationAvailable = true;
					}
				}
				public void onProviderEnabled(String provider) {
					
					try{
						
						

					}//end try
					catch(Exception e)
					{
						
						
					}//end catch
					
				}
				public void onProviderDisabled(String provider) {
					
					try{
						
						
						
					}//end try
					catch(Exception e)
					{
						
						
					}//end catch
					
				}
				public void onLocationChanged(Location location) {
					currentLocation = location;
					if (location.getAccuracy() > 1000 && 
						location.hasAccuracy())
						locationManager.removeUpdates(this);
					
					Double lat=currentLocation.getLatitude();
					Double longt=currentLocation.getLongitude();
					
										
				}
			};
			
				listenerFine = new LocationListener() {
				public void onStatusChanged(String provider, 
					int status, Bundle extras) {
					switch(status) {
					case LocationProvider.OUT_OF_SERVICE:
					case LocationProvider.TEMPORARILY_UNAVAILABLE:
						locationAvailable = false;
						break;
					case LocationProvider.AVAILABLE:
						locationAvailable = true;
					}
				}
				public void onProviderEnabled(String provider) {
					
					try{
						
						

					}//end try
					catch(Exception e)
					{
						
						
					}//end catch
					
				}
				public void onProviderDisabled(String provider) {
					
					try{
						
						
						
					}//end try
					catch(Exception e)
					{
						
						
					}//end catch
					
				}
				
				public void onLocationChanged(Location location) {
					currentLocation = location;
					if (location.getAccuracy() > 1000 
						&& location.hasAccuracy())
						locationManager.removeUpdates(this);
				}
			};
			
			
			}//end try
			catch(Exception e)
			{
				
				
			}//end catch
			
		}
	
		
		
		List<Address> getLocationData(Location location,Context context)
		{
			
			try {
				Geocoder myLocation = new Geocoder(context, Locale.getDefault());   
				
					List<Address> myList = myLocation.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
					
					return myList;
					
					//Address address=myList.get(0);
					
		          // String result = address.getAddressLine(0) + ", "+ address.getLocality()+", "+address.getCountryName();

							
				} catch (Exception e) {
		
					return null;

				}
			
		
		}
		
		
		public void stopUpdates()
		{
			//remove the updates only if the locationManager is not null
				if(null!=locationManager)
				{
					locationManager.removeUpdates(listenerCoarse);
			        locationManager.removeUpdates(listenerFine);
			        
				}//end if

		}
	
}
