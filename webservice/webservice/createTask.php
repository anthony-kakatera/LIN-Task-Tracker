<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php /*header("Content-Type: applicaion/json; charset=UTF-8");*/ ?>
<?php
	if (isset($_POST['title']) && isset($_POST['description']) && isset($_POST['due_date'])  && isset($_POST['status'])  && isset($_POST['staff_id'])){
		$title = $_POST['title'];
		$description = $_POST['description'];
		$dueDate = $_POST['due_date'];
		$status = $_POST['status'];
		$userId = $_POST['staff_id'];
		
		$braodcast = array(); 
		
		$sql = "INSERT INTO task (title, description, due_date, status_id, staff_id) VALUES ('".$title."', '".$description."', '".$dueDate."', '".$status."', '".$userId."')";
		$result = mysqli_query($conn, $sql);
		//return to show successful
		if ($result) {
			// successfully updated
			$response["success"] = "true";
			$response["message"] = "task successfully added.";
			// echoing JSON response
			print json_encode($response);
		}
	}
?>