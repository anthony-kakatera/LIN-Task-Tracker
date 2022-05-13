<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['task_id']) && isset($_POST['comment']) && isset($_POST['user_id'])){
		$taskId = $_POST['task_id'];
		$comment = $_POST['comment'];
		$userId = $_POST['user_id'];
		
		$sql = "INSERT INTO comment (staff_id, comment, date, task_id) VALUES ($userId, $comment, NOW(), $taskId)";
		$result = mysqli_query($conn, $sql);
		
		if ($result) {
			// successfully updated
			$response["success"] = 1;
			$response["message"] = "comment successfully added.";

			// echoing JSON response
			print json_encode($response);
		}
		
	}
?>