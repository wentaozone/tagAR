<?php

$con = mysql_connect("localhost","worldofp_tagar","*JSkyFg[%R}~");
if (!$con)
{
die('Could not connect: ' . mysql_error());
}

mysql_select_db("worldofp_tagar",$con);

$timeZone="Asia/Karachi";
date_default_timezone_set($timeZone);

?>
