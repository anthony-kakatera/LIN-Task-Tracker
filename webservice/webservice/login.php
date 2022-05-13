<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['username']) && isset($_POST['password'])){
		$username = $_POST['username'];
		$password = $_POST['password'];
		
		$braodcast = array(); 
		
		
		$sqlNew = "SELECT * FROM staff WHERE email = '".$username."' AND password = '".$password."' order by id desc limit 1";
		if($resultNew = mysqli_query($conn, $sqlNew)){
			$num_rowsNew = mysqli_num_rows($resultNew);
			while($rowNew = mysqli_fetch_array($resultNew, MYSQLI_ASSOC)) {
				$braodcast[] = $rowNew;
			}
		}
		print json_encode($braodcast);
	}
?>