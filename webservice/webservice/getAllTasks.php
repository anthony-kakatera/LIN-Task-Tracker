<?php require("connection.php"); ?>
<?php session_start(); ?>
<?php header("Content-Type: applicaion/json; charset=UTF-8"); ?>
<?php
	if (isset($_POST['staff_id'])){
		$userId = $_POST['staff_id'];
		$braodcast = array(); 
		//this query returns all tasks a user created and all that were assigned to them that are completed
		$sql = "SELECT title, due_date, description, id, status_id AS state FROM 
                (SELECT t.title, t.due_date, t.description, t.staff_id, t.id, t.status_id 
                FROM task t WHERE t.staff_id = '".$userId."') one 
                LEFT JOIN (SELECT sf.first_name, sf.last_name, a.staff_id FROM staff sf, assignment a WHERE sf.id = a.staff_id) two ON one.staff_id = two.staff_id GROUP BY one.id ";
		if($result = mysqli_query($conn, $sql)){
			$num_rows = mysqli_num_rows($result);
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
				$braodcast[] = $row;
			}
		}
		print json_encode($braodcast);
	}
?>