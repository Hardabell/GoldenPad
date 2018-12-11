<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$img = $_POST['image'];
		$ttl = $_POST['title'];
		$des = $_POST['desc'];
		$gnr = $_POST['genre'];
		$stry = $_POST['stories'];
		$auth = $_POST['author'];
		$dat = $_POST['date'];
		$ids = $_POST['ID'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO story (image,title,description,genre,stories,author,date,user_ID) VALUES ('$img','$ttl','$des','$gnr','$stry','$auth','$dat', (SELECT ID from user where user.username='$ids'))";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan story';
		}else{
			echo 'Gagal Menambahkan story';
		}
		
		mysqli_close($con);
	}

?>