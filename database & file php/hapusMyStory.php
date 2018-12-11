<?php

 $id= $_GET['story_ID'];
 
 //Import File Koneksi Database
 require_once('koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM story WHERE story_ID=$id;";
 
 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Story';
 }else{
 echo 'Gagal Menghapus Story';
 }
 
 mysqli_close($con);

?>