<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$ids = $_POST['ID'];
		$img = $_POST['image'];
		$nem = $_POST['name'];
		$gndr = $_POST['gender'];
		$db = $_POST['DOB'];
		$imel = $_POST['email'];
		$pass = $_POST['password'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE user SET password = '$pass', name = '$nem', gender = '$gndr', DOB = '$db', email = '$imel', image = '$img' WHERE ID = $ids;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data user';
		}else{
			echo 'Gagal Update Data user';
		}
		
		mysqli_close($con);
	}

?>