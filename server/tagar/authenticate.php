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

$email = htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['email'])))));
$password = htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['password'])))));


if (!empty($email) && !empty($password)) {

    $password=md5(sha1($password));
    
    $query = "select * from user where email='$email' and password='$password'";
    $result = mysql_query($query) or die(mysql_error());

    $ifExists = mysql_num_rows($result);

    if ($ifExists > 0) {

        while ($row = mysql_fetch_array($result)) {//only run once, as i just need one user's data
            extract($row);
            $str = "[[4],[" . $idUser . "],[" . $email . "],[" . $firstName . "],[" . $lastName . "]]";

            $base64 = base64_encode($str);
            echo $base64;

            break;
        }//end while
    }//end if
    else {//user not found
        $str = "[[1],[-2]]";

        $base64 = base64_encode($str);
        echo $base64;
    }//end else
}//end if
else {//empty fields
    $str = "[[1],[-1]]";

    $base64 = base64_encode($str);
    echo $base64;
}//end else
//$query="insert into user(email,password,firstName,lastName,joinDate,joinTime)values('mustafa','mustafa','mustafa','mustafa','2012-06-27','12:12:12')";
//mysql_query($query) or die(mysql_error());

mysql_close($con);
?>
