<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

$usr = $_POST['username'];

require_once('koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM user WHERE user.username='$usr'";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"ID"=>$row['ID'],
			"name"=>$row['name']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('resultS'=>$result));
	
	mysqli_close($con);
	
	}

?>