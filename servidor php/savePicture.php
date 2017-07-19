<?php
	$name = $_POST["name"];
	$image = $_POST["image"];


	//echo $name;

	$decodedImage = base64_decode("$image");
	file_put_contents("pictures/".$name.".JPG", $decodedImage);
	

	echo '$image';

	
	
?>
