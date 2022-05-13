<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['task_id'])){
		$taskId = $_POST['task_id'];
		$braodcast = array(); 
		
		$sql = "SELECT s.first_name, s.last_name, c.date, c.comment 
				FROM staff s, comment c
				WHERE s.id = c.staff_id 
				AND c.task_id = $taskId";
		if($result = mysqli_query($conn, $sql)){
			$num_rows = mysqli_num_rows($result);
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
				$braodcast[] = $row;
			}
		}
		print json_encode($braodcast);
		

	}
?>