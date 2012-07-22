<?php
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
 * @author Mustafa Neguib
 * @company MN Tech Solutions
 * @applicationName tarAR
 * @appType This app is an augmented reality app which allows the user to tag locations
 * @version 0.0 
 * @fileName register.php
 * @description This file gets the encrypted data from the client and performs operations on them such as decrypting them and cleaning them for input.
 * and then checks if the data is empty. if none of the parameters are empty then the data is stored in the user table to create a new user.
 * @type server side
 * @param email
 * @param password
 * @param firstName
 * @param firstName
 * @param lastName 
 * @return if error [[1],[-1]] 
 * @return if no error [[1],[1]]
 * */
 

include_once 'config.php';


$email=htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['email'])))));
$password=htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['password'])))));
$firstName=htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['firstName'])))));
$lastName=htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['lastName'])))));

if(!empty($email) && !empty($password) && !empty($firstName) && !empty($lastName))
{


$query="select idUser from user where email='$email'";

$result=mysql_query($query)or die(mysql_error());

$ifExists=mysql_num_rows($result);

if($ifExists>0)
{//user exists for the given email address. the email address must be unique
	
	$str="[[1],[-2]]";
	$base64=base64_encode($str);
	echo $base64;
	
	
}//end if
else
{//no user exists for the given email address, so allow to create the user
	
        $password=md5(sha1($password));
    
	$query="insert into user(email,password,firstName,lastName,joinDate,joinTime)values('".$email."','".$password."','".$firstName."','".$lastName."','".date("Y-m-d")."','".date("H:i:s")."')";
	mysql_query($query) or die(mysql_error());

	$str="[[1],[1]]";

	$base64=base64_encode($str);
	echo $base64;
	
	
}//end else

}//end if
else
{
	$str="[[1],[-1]]";
	$base64=base64_encode($str);
	echo $base64;
}//end else			

mysql_close($con);

?>
