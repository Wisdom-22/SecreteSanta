<?php
if(!empty($_POST['username'])){
	$username = $_POST['username'];
	$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
	
	
	if($con){
		$sql = "select secrete_santa from group_table where users_name = '$username'";
		
		$result = mysqli_query($con, $sql);
		if(mysqli_num_rows($result) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($result)){
				$dataArray[$i] = $row;
				$i++;
			}
			echo json_encode($dataArray, JSON_PRETTY_PRINT);
		}
	
	}
	else
	{
		echo "Failed to connect to database";
	}
	
	
	
}
	
?>