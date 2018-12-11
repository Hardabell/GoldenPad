<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable 
		$id = $_POST['story_ID'];
		$img = $_POST['image'];
		$ttl = $_POST['title'];
		$gnr = $_POST['genre'];
		$des = $_POST['desc'];
		$str = $_POST['stories'];
		$det = $_POST['date'];
		
		//import file koneksi database 
		require_once('koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE story SET image = '$img', title = '$ttl', genre = '$gnr', description = '$des', stories = '$str', date = '$det'  WHERE story_ID = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data story';
		}else{
			echo 'Gagal Update Data story';
		}
		
		mysqli_close($con);
	}

?>