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
 */

/*
 * @author: Mustafa Neguib
 * @company: MN Tech Solutions
 * @applicationName: tagAR
 * @appType: This app is an augmented reality app which allows the user to tag locations
 * @version: 2.1
 * @description: This class is the placeholder for the tags that are shown to the user.
 */

package data;

import android.graphics.Bitmap;

public class Tag {

	
	private float id;//id-->idTag
	private double distance;
	private double latitude;
	private double longitude;
	private float x;//x-->direction
	private float y;
	private float z;	
	private String comment;//comment-->message
	private String locality;//address
	private String city;
	private String country;
	private float diffX;
	private float [] vector;
	private float [] backUpVector;
	private float [] matrix;
	private boolean shownOnScreen;//added in Version 0.4 to keep track of which tag is being shown at a particular instant
	private float accuracy;//added in Version 2.0 to store the accuracy of the tag
	private Bitmap image;
	
	public Tag() {
		super();
		
		
		this.matrix = new float[16];

		for(int i=0;i<16;i++)
		{
			this.matrix[i]=0.0f;
		}//end for
		
		this.vector=new float[6];

		
		for(int i=0;i<6;i++)
		{
			this.vector[i]=0.0f;
		}//end for
		
		this.backUpVector=new float[6];

		
		for(int i=0;i<6;i++)
		{
			this.backUpVector[i]=0.0f;
		}//end for
		
		shownOnScreen=false;
		accuracy=0.0f;
		
	}
	public float getId() {
		return id;
	}
	public void setId(float id) {
		this.id = id;
	}
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longtitude) {
		this.longitude = longtitude;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
		
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public float getDiffX() {
		return diffX;
	}
	public void setDiffX(float diffX) {
		this.diffX = diffX;
	}
	
	public float[] getVector() {
		return vector;
	}




	public void setVector(float[] vector) {

		int i=0;
		for(i=0;i<this.vector.length;i++)
		{
			this.vector[i] = vector[i];	
		}//end for

	}
	
	
	public float[] getBackUpVector() {
		return backUpVector;
	}




	public void setBackUpVector(float[] backUpVector) {


		int i=0;
		for(i=0;i<this.vector.length;i++)
		{
			this.backUpVector[i] = backUpVector[i];	
		}//end for

	}

	
	public float[] getMatrix() {
		return matrix;
	}




	public void setMatrix(float[] matrix) {
		this.matrix = matrix;
	}
	
	
	
	
	public void resetToIdentity()
	{//i am reseting the matrix to the identity matrix
		this.matrix[0]=1;
		this.matrix[1]=0;
		this.matrix[2]=0;
		this.matrix[3]=0;

		this.matrix[4]=0;
		this.matrix[5]=1;
		this.matrix[6]=0;
		this.matrix[7]=0;

		this.matrix[8]=0;
		this.matrix[9]=0;
		this.matrix[10]=1;
		this.matrix[11]=0;

		this.matrix[12]=0;
		this.matrix[13]=0;
		this.matrix[14]=0;
		this.matrix[15]=1;

	}
	


	public boolean isShownOnScreen() {
		return shownOnScreen;
	}
	public void setShownOnScreen(boolean shownOnScreen) {
		this.shownOnScreen = shownOnScreen;
	}
	
	
	
	
	public float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	
	
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public void translation(float tx,float ty, float tz)
	{//translation is performed on the matrix with the provided parameters

		float [] matrixNew=new float[16];
		float [] translationM=new float[16];

		//float a=this.matrix[0], b=this.matrix[1], c=this.matrix[2], d=this.matrix[3], e=this.matrix[4], f=this.matrix[5], g=this.matrix[6], h=this.matrix[7], i=this.matrix[8], j=this.matrix[9], k=this.matrix[10],l=this.matrix[11], m=this.matrix[12], n=this.matrix[13], o=this.matrix[14], p=this.matrix[15];


		for(int i1=0;i1<16;i1++)
		{
			translationM[i1]=0.0f;
			matrixNew[i1]=0.0f;
		}//end for

		translationM[0]=1;
		translationM[3]=tx;
		translationM[5]=1;
		translationM[7]=ty;
		translationM[10]=1;
		translationM[11]=tz;
		translationM[15]=1;

		matrixNew[0]=(translationM[0]*this.matrix[0])+(translationM[1]*this.matrix[4])+(translationM[2]*this.matrix[8])+(translationM[3]*this.matrix[12]);
		matrixNew[1]=(translationM[0]*this.matrix[1])+(translationM[1]*this.matrix[5])+(translationM[2]*this.matrix[9])+(translationM[3]*this.matrix[13]);
		matrixNew[2]=(translationM[0]*this.matrix[2])+(translationM[1]*this.matrix[6])+(translationM[2]*this.matrix[10])+(translationM[3]*this.matrix[14]);
		matrixNew[3]=(translationM[0]*this.matrix[3])+(translationM[1]*this.matrix[7])+(translationM[2]*this.matrix[11])+(translationM[3]*this.matrix[15]);

		matrixNew[4]=(translationM[4]*this.matrix[0])+(translationM[5]*this.matrix[4])+(translationM[6]*this.matrix[8])+(translationM[7]*this.matrix[12]);
		matrixNew[5]=(translationM[4]*this.matrix[1])+(translationM[5]*this.matrix[5])+(translationM[6]*this.matrix[9])+(translationM[7]*this.matrix[13]);
		matrixNew[6]=(translationM[4]*this.matrix[2])+(translationM[5]*this.matrix[6])+(translationM[6]*this.matrix[10])+(translationM[7]*this.matrix[14]);
		matrixNew[7]=(translationM[4]*this.matrix[3])+(translationM[5]*this.matrix[7])+(translationM[6]*this.matrix[11])+(translationM[7]*this.matrix[15]);

		matrixNew[8]=(translationM[8]*this.matrix[0])+(translationM[9]*this.matrix[4])+(translationM[10]*this.matrix[8])+(translationM[11]*this.matrix[12]);
		matrixNew[9]=(translationM[8]*this.matrix[1])+(translationM[9]*this.matrix[5])+(translationM[10]*this.matrix[9])+(translationM[11]*this.matrix[13]);
		matrixNew[10]=(translationM[8]*this.matrix[2])+(translationM[9]*this.matrix[6])+(translationM[10]*this.matrix[10])+(translationM[11]*this.matrix[14]);
		matrixNew[11]=(translationM[8]*this.matrix[3])+(translationM[9]*this.matrix[7])+(translationM[10]*this.matrix[11])+(translationM[11]*this.matrix[15]);

		matrixNew[12]=(translationM[12]*this.matrix[0])+(translationM[13]*this.matrix[4])+(translationM[14]*this.matrix[8])+(translationM[15]*this.matrix[12]);
		matrixNew[13]=(translationM[12]*this.matrix[1])+(translationM[13]*this.matrix[5])+(translationM[14]*this.matrix[9])+(translationM[15]*this.matrix[13]);
		matrixNew[14]=(translationM[12]*this.matrix[2])+(translationM[13]*this.matrix[6])+(translationM[14]*this.matrix[10])+(translationM[15]*this.matrix[14]);
		matrixNew[15]=(translationM[12]*this.matrix[3])+(translationM[13]*this.matrix[7])+(translationM[14]*this.matrix[11])+(translationM[15]*this.matrix[15]);

		for(int i2=0;i2<16;i2++)
		{
			this.matrix[i2]=matrixNew[i2];
		}//end for


	}

	public void scaling(float sx,float sy, float sz)
	{//scaling is performed on the matrix with the provided parameters


		float [] matrixNew=new float[16];
		float [] scalingM=new float[16];


		for(int i1=0;i1<16;i1++)
		{
			scalingM[i1]=0.0f;
			matrixNew[i1]=0.0f;
		}//end for

		scalingM[0]=sx;
		scalingM[5]=sy;
		scalingM[10]=sz;
		scalingM[15]=1;

		matrixNew[0]=(scalingM[0]*this.matrix[0])+(scalingM[1]*this.matrix[4])+(scalingM[2]*this.matrix[8])+(scalingM[3]*this.matrix[12]);
		matrixNew[1]=(scalingM[0]*this.matrix[1])+(scalingM[1]*this.matrix[5])+(scalingM[2]*this.matrix[9])+(scalingM[3]*this.matrix[13]);
		matrixNew[2]=(scalingM[0]*this.matrix[2])+(scalingM[1]*this.matrix[6])+(scalingM[2]*this.matrix[10])+(scalingM[3]*this.matrix[14]);
		matrixNew[3]=(scalingM[0]*this.matrix[3])+(scalingM[1]*this.matrix[7])+(scalingM[2]*this.matrix[11])+(scalingM[3]*this.matrix[15]);

		matrixNew[4]=(scalingM[4]*this.matrix[0])+(scalingM[5]*this.matrix[4])+(scalingM[6]*this.matrix[8])+(scalingM[7]*this.matrix[12]);
		matrixNew[5]=(scalingM[4]*this.matrix[1])+(scalingM[5]*this.matrix[5])+(scalingM[6]*this.matrix[9])+(scalingM[7]*this.matrix[13]);
		matrixNew[6]=(scalingM[4]*this.matrix[2])+(scalingM[5]*this.matrix[6])+(scalingM[6]*this.matrix[10])+(scalingM[7]*this.matrix[14]);
		matrixNew[7]=(scalingM[4]*this.matrix[3])+(scalingM[5]*this.matrix[7])+(scalingM[6]*this.matrix[11])+(scalingM[7]*this.matrix[15]);

		matrixNew[8]=(scalingM[8]*this.matrix[0])+(scalingM[9]*this.matrix[4])+(scalingM[10]*this.matrix[8])+(scalingM[11]*this.matrix[12]);
		matrixNew[9]=(scalingM[8]*this.matrix[1])+(scalingM[9]*this.matrix[5])+(scalingM[10]*this.matrix[9])+(scalingM[11]*this.matrix[13]);
		matrixNew[10]=(scalingM[8]*this.matrix[2])+(scalingM[9]*this.matrix[6])+(scalingM[10]*this.matrix[10])+(scalingM[11]*this.matrix[14]);
		matrixNew[11]=(scalingM[8]*this.matrix[3])+(scalingM[9]*this.matrix[7])+(scalingM[10]*this.matrix[11])+(scalingM[11]*this.matrix[15]);

		matrixNew[12]=(scalingM[12]*this.matrix[0])+(scalingM[13]*this.matrix[4])+(scalingM[14]*this.matrix[8])+(scalingM[15]*this.matrix[12]);
		matrixNew[13]=(scalingM[12]*this.matrix[1])+(scalingM[13]*this.matrix[5])+(scalingM[14]*this.matrix[9])+(scalingM[15]*this.matrix[13]);
		matrixNew[14]=(scalingM[12]*this.matrix[2])+(scalingM[13]*this.matrix[6])+(scalingM[14]*this.matrix[10])+(scalingM[15]*this.matrix[14]);
		matrixNew[15]=(scalingM[12]*this.matrix[3])+(scalingM[13]*this.matrix[7])+(scalingM[14]*this.matrix[11])+(scalingM[15]*this.matrix[15]);

		for(int i2=0;i2<16;i2++)
		{
			this.matrix[i2]=matrixNew[i2];
		}//end for


	}

	public void rotation(int type, float angle)
	{//rotation is performed on the matrix with the provided parameters

		//Log.v("Angle1",((Float)angle).toString());

		float angleRadians=(float)((angle/180.0)*Math.PI);
		float [] matrixNew=new float[16];



		//Log.v("Angle2",((Float)angle).toString());

		//Log.v("PI",((Double)Math.PI).toString());

	//	float a=this.matrix[0], b=this.matrix[1], c=this.matrix[2], d=this.matrix[3], e=this.matrix[4], f=this.matrix[5], g=this.matrix[6], h=this.matrix[7], i=this.matrix[8], j=this.matrix[9], k=this.matrix[10],l=this.matrix[11], m=this.matrix[12], n=this.matrix[13], o=this.matrix[14], p=this.matrix[15];

		float [] rotationM=new float[16];


		for(int i1=0;i1<16;i1++)
		{
			rotationM[i1]=0.0f;
			matrixNew[i1]=0.0f;
		}//end for



		switch(type)
		{
		//rotation about the x-axis
		case 1:  

			rotationM[0]=1;
			rotationM[15]=1;

			if(angle==90 || angle==270)
			{
				rotationM[5]=0.0f;
				rotationM[10]=0.0f;
			}//end if
			else
			{
				rotationM[5]=(float)Math.cos(angleRadians);	
				rotationM[10]=(float)Math.cos(angleRadians);	
			}//end else


			if(angle==180 || angle==360)
			{
				rotationM[6]=0.0f;
				rotationM[9]=0.0f;
			}//end if
			else
			{
				rotationM[6]=((float)Math.sin(angleRadians))*(-1);	
				rotationM[9]=(float)Math.sin(angleRadians);	
			}//end else



			matrixNew[0]=(rotationM[0]*this.matrix[0])+(rotationM[1]*this.matrix[4])+(rotationM[2]*this.matrix[8])+(rotationM[3]*this.matrix[12]);
			matrixNew[1]=(rotationM[0]*this.matrix[1])+(rotationM[1]*this.matrix[5])+(rotationM[2]*this.matrix[9])+(rotationM[3]*this.matrix[13]);
			matrixNew[2]=(rotationM[0]*this.matrix[2])+(rotationM[1]*this.matrix[6])+(rotationM[2]*this.matrix[10])+(rotationM[3]*this.matrix[14]);
			matrixNew[3]=(rotationM[0]*this.matrix[3])+(rotationM[1]*this.matrix[7])+(rotationM[2]*this.matrix[11])+(rotationM[3]*this.matrix[15]);

			matrixNew[4]=(rotationM[4]*this.matrix[0])+(rotationM[5]*this.matrix[4])+(rotationM[6]*this.matrix[8])+(rotationM[7]*this.matrix[12]);
			matrixNew[5]=(rotationM[4]*this.matrix[1])+(rotationM[5]*this.matrix[5])+(rotationM[6]*this.matrix[9])+(rotationM[7]*this.matrix[13]);
			matrixNew[6]=(rotationM[4]*this.matrix[2])+(rotationM[5]*this.matrix[6])+(rotationM[6]*this.matrix[10])+(rotationM[7]*this.matrix[14]);
			matrixNew[7]=(rotationM[4]*this.matrix[3])+(rotationM[5]*this.matrix[7])+(rotationM[6]*this.matrix[11])+(rotationM[7]*this.matrix[15]);

			matrixNew[8]=(rotationM[8]*this.matrix[0])+(rotationM[9]*this.matrix[4])+(rotationM[10]*this.matrix[8])+(rotationM[11]*this.matrix[12]);
			matrixNew[9]=(rotationM[8]*this.matrix[1])+(rotationM[9]*this.matrix[5])+(rotationM[10]*this.matrix[9])+(rotationM[11]*this.matrix[13]);
			matrixNew[10]=(rotationM[8]*this.matrix[2])+(rotationM[9]*this.matrix[6])+(rotationM[10]*this.matrix[10])+(rotationM[11]*this.matrix[14]);
			matrixNew[11]=(rotationM[8]*this.matrix[3])+(rotationM[9]*this.matrix[7])+(rotationM[10]*this.matrix[11])+(rotationM[11]*this.matrix[15]);

			matrixNew[12]=(rotationM[12]*this.matrix[0])+(rotationM[13]*this.matrix[4])+(rotationM[14]*this.matrix[8])+(rotationM[15]*this.matrix[12]);
			matrixNew[13]=(rotationM[12]*this.matrix[1])+(rotationM[13]*this.matrix[5])+(rotationM[14]*this.matrix[9])+(rotationM[15]*this.matrix[13]);
			matrixNew[14]=(rotationM[12]*this.matrix[2])+(rotationM[13]*this.matrix[6])+(rotationM[14]*this.matrix[10])+(rotationM[15]*this.matrix[14]);
			matrixNew[15]=(rotationM[12]*this.matrix[3])+(rotationM[13]*this.matrix[7])+(rotationM[14]*this.matrix[11])+(rotationM[15]*this.matrix[15]);



			break;

			//rotation about the y-axis
		case 2: 


			rotationM[5]=1;
			rotationM[15]=1;


			if(angle==90 || angle==270)
			{
				rotationM[0]=0.0f;
				rotationM[10]=0.0f;
			}//end if
			else
			{
				rotationM[0]=(float)Math.cos(angleRadians);	
				rotationM[10]=(float)Math.cos(angleRadians);	
			}//end else


			if(angle==180 || angle==360)
			{
				rotationM[2]=0.0f;
				rotationM[8]=0.0f;
			}//end if
			else
			{
				rotationM[2]=((float)Math.sin(angleRadians));	
				rotationM[8]=((float)Math.sin(angleRadians))*(-1);	
			}//end else


			matrixNew[0]=(rotationM[0]*this.matrix[0])+(rotationM[1]*this.matrix[4])+(rotationM[2]*this.matrix[8])+(rotationM[3]*this.matrix[12]);
			matrixNew[1]=(rotationM[0]*this.matrix[1])+(rotationM[1]*this.matrix[5])+(rotationM[2]*this.matrix[9])+(rotationM[3]*this.matrix[13]);
			matrixNew[2]=(rotationM[0]*this.matrix[2])+(rotationM[1]*this.matrix[6])+(rotationM[2]*this.matrix[10])+(rotationM[3]*this.matrix[14]);
			matrixNew[3]=(rotationM[0]*this.matrix[3])+(rotationM[1]*this.matrix[7])+(rotationM[2]*this.matrix[11])+(rotationM[3]*this.matrix[15]);

			matrixNew[4]=(rotationM[4]*this.matrix[0])+(rotationM[5]*this.matrix[4])+(rotationM[6]*this.matrix[8])+(rotationM[7]*this.matrix[12]);
			matrixNew[5]=(rotationM[4]*this.matrix[1])+(rotationM[5]*this.matrix[5])+(rotationM[6]*this.matrix[9])+(rotationM[7]*this.matrix[13]);
			matrixNew[6]=(rotationM[4]*this.matrix[2])+(rotationM[5]*this.matrix[6])+(rotationM[6]*this.matrix[10])+(rotationM[7]*this.matrix[14]);
			matrixNew[7]=(rotationM[4]*this.matrix[3])+(rotationM[5]*this.matrix[7])+(rotationM[6]*this.matrix[11])+(rotationM[7]*this.matrix[15]);

			matrixNew[8]=(rotationM[8]*this.matrix[0])+(rotationM[9]*this.matrix[4])+(rotationM[10]*this.matrix[8])+(rotationM[11]*this.matrix[12]);
			matrixNew[9]=(rotationM[8]*this.matrix[1])+(rotationM[9]*this.matrix[5])+(rotationM[10]*this.matrix[9])+(rotationM[11]*this.matrix[13]);
			matrixNew[10]=(rotationM[8]*this.matrix[2])+(rotationM[9]*this.matrix[6])+(rotationM[10]*this.matrix[10])+(rotationM[11]*this.matrix[14]);
			matrixNew[11]=(rotationM[8]*this.matrix[3])+(rotationM[9]*this.matrix[7])+(rotationM[10]*this.matrix[11])+(rotationM[11]*this.matrix[15]);

			matrixNew[12]=(rotationM[12]*this.matrix[0])+(rotationM[13]*this.matrix[4])+(rotationM[14]*this.matrix[8])+(rotationM[15]*this.matrix[12]);
			matrixNew[13]=(rotationM[12]*this.matrix[1])+(rotationM[13]*this.matrix[5])+(rotationM[14]*this.matrix[9])+(rotationM[15]*this.matrix[13]);
			matrixNew[14]=(rotationM[12]*this.matrix[2])+(rotationM[13]*this.matrix[6])+(rotationM[14]*this.matrix[10])+(rotationM[15]*this.matrix[14]);
			matrixNew[15]=(rotationM[12]*this.matrix[3])+(rotationM[13]*this.matrix[7])+(rotationM[14]*this.matrix[11])+(rotationM[15]*this.matrix[15]);


			break;

			//rotation about the z-axis
		case 3: 

			rotationM[10]=1;
			rotationM[15]=1;



			if(angle==90 || angle==270)
			{
				rotationM[0]=0.0f;
				rotationM[5]=0.0f;
			}//end if
			else
			{
				rotationM[0]=(float)Math.cos(angleRadians);	
				rotationM[5]=(float)Math.cos(angleRadians);	
			}//end else


			if(angle==180 || angle==360)
			{
				rotationM[1]=0.0f;
				rotationM[4]=0.0f;
			}//end if
			else
			{
				rotationM[1]=((float)Math.sin(angleRadians))*(-1);	
				rotationM[4]=(float)Math.sin(angleRadians);	
			}//end else

			matrixNew[0]=(rotationM[0]*this.matrix[0])+(rotationM[1]*this.matrix[4])+(rotationM[2]*this.matrix[8])+(rotationM[3]*this.matrix[12]);
			matrixNew[1]=(rotationM[0]*this.matrix[1])+(rotationM[1]*this.matrix[5])+(rotationM[2]*this.matrix[9])+(rotationM[3]*this.matrix[13]);
			matrixNew[2]=(rotationM[0]*this.matrix[2])+(rotationM[1]*this.matrix[6])+(rotationM[2]*this.matrix[10])+(rotationM[3]*this.matrix[14]);
			matrixNew[3]=(rotationM[0]*this.matrix[3])+(rotationM[1]*this.matrix[7])+(rotationM[2]*this.matrix[11])+(rotationM[3]*this.matrix[15]);

			matrixNew[4]=(rotationM[4]*this.matrix[0])+(rotationM[5]*this.matrix[4])+(rotationM[6]*this.matrix[8])+(rotationM[7]*this.matrix[12]);
			matrixNew[5]=(rotationM[4]*this.matrix[1])+(rotationM[5]*this.matrix[5])+(rotationM[6]*this.matrix[9])+(rotationM[7]*this.matrix[13]);
			matrixNew[6]=(rotationM[4]*this.matrix[2])+(rotationM[5]*this.matrix[6])+(rotationM[6]*this.matrix[10])+(rotationM[7]*this.matrix[14]);
			matrixNew[7]=(rotationM[4]*this.matrix[3])+(rotationM[5]*this.matrix[7])+(rotationM[6]*this.matrix[11])+(rotationM[7]*this.matrix[15]);

			matrixNew[8]=(rotationM[8]*this.matrix[0])+(rotationM[9]*this.matrix[4])+(rotationM[10]*this.matrix[8])+(rotationM[11]*this.matrix[12]);
			matrixNew[9]=(rotationM[8]*this.matrix[1])+(rotationM[9]*this.matrix[5])+(rotationM[10]*this.matrix[9])+(rotationM[11]*this.matrix[13]);
			matrixNew[10]=(rotationM[8]*this.matrix[2])+(rotationM[9]*this.matrix[6])+(rotationM[10]*this.matrix[10])+(rotationM[11]*this.matrix[14]);
			matrixNew[11]=(rotationM[8]*this.matrix[3])+(rotationM[9]*this.matrix[7])+(rotationM[10]*this.matrix[11])+(rotationM[11]*this.matrix[15]);

			matrixNew[12]=(rotationM[12]*this.matrix[0])+(rotationM[13]*this.matrix[4])+(rotationM[14]*this.matrix[8])+(rotationM[15]*this.matrix[12]);
			matrixNew[13]=(rotationM[12]*this.matrix[1])+(rotationM[13]*this.matrix[5])+(rotationM[14]*this.matrix[9])+(rotationM[15]*this.matrix[13]);
			matrixNew[14]=(rotationM[12]*this.matrix[2])+(rotationM[13]*this.matrix[6])+(rotationM[14]*this.matrix[10])+(rotationM[15]*this.matrix[14]);
			matrixNew[15]=(rotationM[12]*this.matrix[3])+(rotationM[13]*this.matrix[7])+(rotationM[14]*this.matrix[11])+(rotationM[15]*this.matrix[15]);


			break;


		default: break;

		}//end switch



		for(int i2=0;i2<16;i2++)
		{
			this.matrix[i2]=matrixNew[i2];
		}//end for


	}


	public void updateXYZ()
	{//this function will update the x, y, and z values by multiplying the current matrix with the existing x, y, and z values

		//float a=this.matrix[0], b=this.matrix[1], c=this.matrix[2], d=this.matrix[3], e=this.matrix[4], f=this.matrix[5], g=this.matrix[6], h=this.matrix[7], i=this.matrix[8], j=this.matrix[9], k=this.matrix[10],l=this.matrix[11], m=this.matrix[12], n=this.matrix[13], o=this.matrix[14], p=this.matrix[15];

		float x=0.0f,y=0.0f,z=0.0f,x1=0.0f,y1=0.0f,z1=0.0f;

		x=(this.matrix[0]*this.vector[0])+(this.matrix[1]*this.vector[1])+(this.matrix[2]*this.vector[2])+(this.matrix[3]*1);
		y=(this.matrix[4]*this.vector[0])+(this.matrix[5]*this.vector[1])+(this.matrix[6]*this.vector[2])+(this.matrix[7]*1);
		z=(this.matrix[8]*this.vector[0])+(this.matrix[9]*this.vector[1])+(this.matrix[10]*this.vector[2])+(this.matrix[11]*1);

		x1=(this.matrix[0]*this.vector[3])+(this.matrix[1]*this.vector[4])+(this.matrix[2]*this.vector[5])+(this.matrix[3]*1);
		y1=(this.matrix[4]*this.vector[3])+(this.matrix[5]*this.vector[4])+(this.matrix[6]*this.vector[5])+(this.matrix[7]*1);
		z1=(this.matrix[8]*this.vector[3])+(this.matrix[9]*this.vector[4])+(this.matrix[10]*this.vector[5])+(this.matrix[11]*1);



		vector[0]=x;
		vector[1]=y;
		vector[2]=z;

		vector[3]=x1;
		vector[4]=y1;
		vector[5]=z1;


	}



	public void updateBackupXYZ()
	{//this function will update the x, y, and z values by multiplying the current matrix with the existing x, y, and z values

		//float a=this.matrix[0], b=this.matrix[1], c=this.matrix[2], d=this.matrix[3], e=this.matrix[4], f=this.matrix[5], g=this.matrix[6], h=this.matrix[7], i=this.matrix[8], j=this.matrix[9], k=this.matrix[10],l=this.matrix[11], m=this.matrix[12], n=this.matrix[13], o=this.matrix[14], p=this.matrix[15];

		float x=0.0f,y=0.0f,z=0.0f,x1=0.0f,y1=0.0f,z1=0.0f;

		x=(this.matrix[0]*this.vector[0])+(this.matrix[1]*this.vector[1])+(this.matrix[2]*this.vector[2])+(this.matrix[3]*1);
		y=(this.matrix[4]*this.vector[0])+(this.matrix[5]*this.vector[1])+(this.matrix[6]*this.vector[2])+(this.matrix[7]*1);
		z=(this.matrix[8]*this.vector[0])+(this.matrix[9]*this.vector[1])+(this.matrix[10]*this.vector[2])+(this.matrix[11]*1);

		x1=(this.matrix[0]*this.vector[3])+(this.matrix[1]*this.vector[4])+(this.matrix[2]*this.vector[5])+(this.matrix[3]*1);
		y1=(this.matrix[4]*this.vector[3])+(this.matrix[5]*this.vector[4])+(this.matrix[6]*this.vector[5])+(this.matrix[7]*1);
		z1=(this.matrix[8]*this.vector[3])+(this.matrix[9]*this.vector[4])+(this.matrix[10]*this.vector[5])+(this.matrix[11]*1);


		backUpVector[0]=x;
		backUpVector[1]=y;
		backUpVector[2]=z;

		backUpVector[3]=x1;
		backUpVector[4]=y1;
		backUpVector[5]=z1;


	}

	public void switchVectorToBackUpVector()
	{//this function will copy the values of the vector into the backUpVector 

		backUpVector[0]=vector[0];
		backUpVector[1]=vector[1];
		backUpVector[2]=vector[2];

		backUpVector[3]=vector[3];
		backUpVector[4]=vector[4];
		backUpVector[5]=vector[5];

	}

	public void swithBackUpVectorToVector()
	{//this function will copy the values of the backUpVector into the vector

		vector[0]=backUpVector[0];
		vector[1]=backUpVector[1];
		vector[2]=backUpVector[2];

		vector[3]=backUpVector[3];
		vector[4]=backUpVector[4];
		vector[5]=backUpVector[5];

	}

	
	public void resetVector()
	{
	
		for(int i=0;i<6;i++)
		{
			vector[i]=0.0f;
			
		}//end for

	
	}
}
