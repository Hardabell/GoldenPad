<?php
$con=mysql_connect("localhost","root","","goldenpad");
if (mysql_connect_errno($con){
 echo "failed konak";
}
$u = $_POST['username']
$pw = $_POST['password']
$res = mysqli_query($con, "SELECT role from user where username='$u' and password='$pw');
$row = mysqli_fetch_array($res);
$data = $row[0];

if($data){
echo $data;
}
mysqli_close($con); 
?>