<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['task_id'])){
		$taskId = $_POST['task_id'];
		$braodcast = array(); 
		
		$sql = "SELECT s.first_name, s.last_name, s.id  
				FROM staff s, assignment a
				WHERE s.id = a.staff_id 
				AND a.task_id = $taskId";
		if($result = mysqli_query($conn, $sql)){
			$num_rows = mysqli_num_rows($result);
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
				$braodcast[] = $row;
			}
		}
		print json_encode($braodcast);
	}
?>