<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['task_id'])){
		$taskId = $_POST['task_id'];
		$braodcast = array(); 
		
		$sql = "SELECT t.title, t.due_date, t.description, st.state, sf.first_name, sf.last_name 
				FROM task t, status st, staff sf 
				WHERE t.status_id = st.id AND sf.id = t.staff_id AND t.id = $taskId";
		if($result = mysqli_query($conn, $sql)){
			$num_rows = mysqli_num_rows($result);
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
				$braodcast[] = $row;
			}
		}
		print json_encode($braodcast);
	}
?>