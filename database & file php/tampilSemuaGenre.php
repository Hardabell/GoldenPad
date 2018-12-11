<?php

require_once('koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM story";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"story_ID"=>$row['story_ID'],
			"title"=>$row['title'],
			"author"=>$row['author'],
			"genre"=>$row['genre'],
			"desc"=>$row['desc']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);

?>