<?php
$id = $_GET['ID'];
	
	//Importing database
	require_once('koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM user WHERE ID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"image"=>$row['image'],
			"name"=>$row['name'],
			"gender"=>$row['gender'],
			"DOB"=>$row['DOB'],
			"email"=>$row['email'],
			"password"=>$row['password']
		));
 
	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);


?>