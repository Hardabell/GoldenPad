<?php
$id = $_GET['story_ID'];
	
	//Importing database
	require_once('koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM story WHERE story_ID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"image"=>$row['image'],
			"titles"=>$row['title'],
			"genres"=>$row['genre'],
			"descs"=>$row['description'],
			"stories"=>$row['stories'],
			"dates"=>$row['date']
		));
 
	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);


?>