<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['task_id']) && isset($_POST['status'])){
		$taskId = $_POST['task_id'];
		$status = $_POST['status'];
	
		$sql = "UPDATE task SET status_id = '$status' WHERE id = '$taskId' ";
		$result = mysqli_query($conn, $sql);
	
		if ($result) {
			// successfully updated
			$response["success"] = "true";
			$response["message"] = "task successfully updated.";

			// echoing JSON response
			echo json_encode($response);
		}
	}
?>