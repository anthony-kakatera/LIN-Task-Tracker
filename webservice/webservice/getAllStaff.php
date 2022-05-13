<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	$braodcast = array(); 
	$sqlNew = "SELECT * FROM staff";
	if($resultNew = mysqli_query($conn, $sqlNew)){
		$num_rowsNew = mysqli_num_rows($resultNew);
		while($rowNew = mysqli_fetch_array($resultNew, MYSQLI_ASSOC)) {
			$braodcast[] = $rowNew;
		}
	}
	print json_encode($braodcast);
	
?>