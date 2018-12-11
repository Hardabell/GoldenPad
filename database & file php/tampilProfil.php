<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

$usr = $_POST['username'];

require_once('koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM user WHERE story.author='$usr'";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"image"=>$row['image'],
			"name"=>$row['name'],
			"gender"=>$row['gender'],
			"DOB"=>$row['DOB'],
			"email"=>$row['email'],
			"password"=>$row['password'],
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('resultS'=>$result));
	
	mysqli_close($con);
	
	}

?>
