<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$mail = $_POST['email'];
		$usrnm = $_POST['username'];
		$pss = $_POST['password'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO user (email,username,password) VALUES ('$mail','$usrnm','$pss')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Register Success';
		}else{
			echo 'Invalid register';
		}
		
		mysqli_close($con);
	}

?>