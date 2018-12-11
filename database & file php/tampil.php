<?php
$usrname = $_GET['username'];
	
	//Importing database
	require_once('koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT ID FROM user WHERE user.username='$usrname'";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"ID"=>$row['ID'],
			"username"=>$row['username'],
			"password"=>$row['password'],
			"name"=>$row['name']
			"gender"=>$row['gender']
			"DOB"=>$row['DOB']
			"email"=>$row['email']
			"image"=>$row['image']
		));
 
	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);


?>