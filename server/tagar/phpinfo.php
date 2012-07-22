 <?php
 /*$url = "http://ws.geonames.org/findNearbyPlaceNameJSON?lat=31.4177743&lng=74.1701845";
 $json = file_get_contents($url);
 $data = json_decode($json, true);
 foreach ($data["geonames"] as $key => $val) {
 	echo $val["name"]." ".$val["countryName"]." ".$val["countryCode"]."<br />";
        
        print_r($data["geonames"]);
 }*/

/*
// set your API key here
$api_key = "AIzaSyAhXn8oiofB3_r-vsn9wcJJFF_A71RksS8";
// format this string with the appropriate latitude longitude
$url = 'http://maps.google.com/maps/geo?q=31.4565971,74.3684542&output=json&sensor=false&key=' . $api_key;
// make the HTTP request
$data = file_get_contents($url);

// parse the json response
$jsondata = json_decode($data,true);


// if we get a placemark array and the status was good, get the addres
if(is_array($jsondata )&& $jsondata ['Status']['code']==200)
{
      
      $localityInner=$jsondata['Placemark'][0]['AddressDetails']['Country']['AdministrativeArea']['SubAdministrativeArea']['Locality']['DependentLocality']['Thoroughfare']['ThoroughfareName'];
      echo $localityInner;
      echo "<br />";
      $locality=$jsondata['Placemark'][0]['AddressDetails']['Country']['AdministrativeArea']['SubAdministrativeArea']['Locality']['DependentLocality']['DependentLocalityName'];
      echo $locality;
      echo "<br />";
      $province = $jsondata ['Placemark'][0]['AddressDetails']['Country']['AdministrativeArea']['AdministrativeAreaName'];
      echo $province;
      echo "<br />";
      $city=$jsondata['Placemark'][0]['AddressDetails']['Country']['AdministrativeArea']['SubAdministrativeArea']['Locality']['LocalityName'];
      echo $city;
      echo "<br />";
      $country=$jsondata['Placemark'][0]['AddressDetails']['Country']['CountryName'];
      echo $country;

      
      
      
}

*/

echo md5(sha1('m'));
echo "<br />";

echo md5(sha1(strtotime("now")));


 $to ="mustafa.neguib@gmail.com";

        // subject
        $subject = 'tagAR Password Reset Request';

        // message
        $message =<<<ABC
    <html>
    <head>
    <title>tagAR Password Reset Request</title>
    </head>
    <body>
    <p>You requested for your password to be reset. If you did not place the request to get your password reset then please make a note of the
    password and change it immediately.</p>
    <p>Password: ssss</p>

    <p>Please note that this an auto generated email. Do not reply to this email.</p>
    <p><span style="text-size:small;">MN Tech Solutions (www.mntechsolutions.net) tagAR</p>

    </body>
    </html>
ABC;

        // To send HTML mail, the Content-type header must be set
        $headers = 'MIME-Version: 1.0' . "\r\n";
        $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

        // Additional headers
        $headers .= 'To: Mary <mary@example.com>, Kelly <kelly@example.com>' . "\r\n";
        $headers .= 'From: tagAR Augmented Reality Tagging App' . "\r\n";

        // Mail it
        $val=mail($to, $subject, $message, $headers);
        
        if($val==true)
        {
            echo "sent";
            
        }//end if
        else
        {
            echo "failed";
        }//end else

?>

