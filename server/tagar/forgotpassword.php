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

if (!empty($email)) {

    $query = "select * from user where email='$email'";
    $result = mysql_query($query) or die(mysql_error());

    $ifExists = mysql_num_rows($result);

    if ($ifExists > 0) {

        $password = strtotime("now");
        $passwordEncrypted = md5(sha1($password));

        $query = "update user set password='$passwordEncrypted' where email='$email'";
        $result = mysql_query($query) or die(mysql_error());

        $to = $email;

        // subject
        $subject = 'tagAR Password Reset Request';

        // message
        $message =<<<ABC
    <html>
    <head>
    <title>tagAR Password Reset Request</title>
    </head>
    <body>
    <p>Dear User, <br /> You requested for your password to be reset. If you did not place the request to get your password reset then please make a note of the
    password and change it immediately.</p>
    <p>Password: $password</p>

    <p>Please note that this an auto generated email. Do not reply to this email.</p>
    <p><span style="text-size:small;">MN Tech Solutions (www.mntechsolutions.net) tagAR</p>

    </body>
    </html>
ABC;

        // To send HTML mail, the Content-type header must be set
        $headers = 'MIME-Version: 1.0' . "\r\n";
        $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

        // Additional headers
        
        $headers .= 'From: tagAR Augmented Reality Tagging App' . "\r\n";

        // Mail it
        $value=mail($to, $subject, $message, $headers);

       
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
{//email field not entered
        $str = "[[1],[-1]]";

        $base64 = base64_encode($str);
        echo $base64;
}//end else

mysql_close($con);
?>