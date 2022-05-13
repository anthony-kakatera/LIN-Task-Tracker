<?php
	$host = "localhost";
	$user = "u386271580_simple";
	$pass = "FzT9T3Nzt2/";
	$database = "u386271580_luk_task_track";
	
	$conn = mysqli_connect($host,$user,$pass) or die("Didn't connect");
	
	mysqli_select_db($conn, $database);