<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php /*header("Content-Type: applicaion/json; charset=UTF-8");*/ ?>
<?php
	if (isset($_POST['task_id']) && isset($_POST['staff_id'])){
		$taskId = $_POST['task_id'];
		$userId = $_POST['staff_id'];
		
		$sql = "INSERT INTO assignment (staff_id, task_id) VALUES ($userId, $taskId)";
		$result = mysqli_query($conn, $sql);
		
		if ($result) {
			// successfully updated
			$response["success"] = 1;
			$response["message"] = "Task assigned successfully added.";

			// echoing JSON response
			print json_encode($response);
		}
	}
?>