<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['email_address'])  && isset($_POST['postal_address'])){
		$firstName = $_POST['first_name'];
		$lastName = $_POST['last_name'];
		$password = $_POST['password'];
		$email = $_POST['email_address'];
		$postalAddress = $_POST['postal_address'];
		
		$braodcast = array(); 
		
		$sql = "INSERT INTO staff (first_name, last_name, email, password) VALUES ('".$firstName."', '".$lastName."', '".$email."', '".$password."')";
		$result = mysqli_query($conn, $sql);
		//get the latest staffer
		
		$sqlNew = "SELECT * FROM staff order by id desc limit 1";
		if($resultNew = mysqli_query($conn, $sqlNew)){
			$num_rowsNew = mysqli_num_rows($resultNew);
			while($rowNew = mysqli_fetch_array($resultNew, MYSQLI_ASSOC)) {
				$braodcast[] = $rowNew;
			}
		}
		print json_encode($braodcast);
	}
?>