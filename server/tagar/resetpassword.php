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
$presentPassword = htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['presentPassword'])))));
$newPassword = htmlspecialchars(mysql_real_escape_string(strip_tags(trim(base64_decode($_POST['newPassword'])))));


if (!empty($email) && !empty($presentPassword) && !empty($newPassword)) {

    
    $passwordEncrypted = md5(sha1($presentPassword));
    $query = "select * from user where email='$email' and password='$passwordEncrypted'";
    $result = mysql_query($query) or die(mysql_error());

    $ifExists = mysql_num_rows($result);

    if ($ifExists > 0) {

        
        $passwordEncrypted = md5(sha1($newPassword));

        $query = "update user set password='$passwordEncrypted' where email='$email'";
        $result = mysql_query($query) or die(mysql_error());

        
        $str = "[[1],[1]]";

        $base64 = base64_encode($str);
        echo $base64;
    }//end if
    else
    {//no user exists for the given email address
        $str = "[[1],[-2]]";

        $base64 = base64_encode($str);
        echo $base64;
        
    }//end else
}//end if
else
{//none or any one of the fields have not been entered
        $str = "[[1],[-1]]";

        $base64 = base64_encode($str);
        echo $base64;
}//end else

mysql_close($con);
?>
