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

include_once 'config.php';

$latitudeUser= htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['latitude'])))));
$longitudeUser= htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['longitude'])))));


/**
 * source for the sql query to get the distance between two points, https://developers.google.com/maps/articles/phpsqlsearch_v3
 * this sql query gets the distance between 2 geo location points and gets 10 or less points which are less than 5 km away from the current location 
 * http://www.movable-type.co.uk/scripts/latlong.html
 * 6371 is for getting the distance in km
 * haversine formula has been used to calculate the distance between 2 points
 * more information about calculating distance between 2 points  http://www.movable-type.co.uk/scripts/latlong.html
 * 
 */

//$latitudeUser=31.4625762;
//$longitudeUser=74.3670648;

$query="SELECT *, ( 6371 * acos( cos( radians('".$latitudeUser."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitudeUser."') ) + sin( radians('".$latitudeUser."') ) * sin( radians( latitude ) ) ) ) AS distance FROM tag HAVING distance < 5 ORDER BY distance LIMIT 0,10";


//the rest of the code is my own

$result=mysql_query($query)or die(mysql_error());

$distance=0.0;

$ifExists=mysql_num_rows($result);

if($ifExists>0)
{//there is some data in the database
    
    $numOfRows=$ifExists;
    $str = "[[9],[".$numOfRows."]";
    while($row=mysql_fetch_array($result))
    {
        extract($row);

        //$distance=distance($latitudeUser,$longitudeUser,$latitude,$longitude,"k");

        $str=$str.",[".$idTag."],[".$distance."],[".$latitude."],[".$longitude."],[".$direction."],[".$message."],[".$locality."],[".$city."],[".$country."]";
                
    }//end while
    
    
    $str=$str."]";
    
    $base64 = base64_encode($str);
    echo $base64;
    
}//end if
else
{
    
        $str = "[[1],[-2]]";

        $base64 = base64_encode($str);
        echo $base64;
        
}//end else

?>


