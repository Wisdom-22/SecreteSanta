<?php
header('Content-Type: application/json');
$data = array();
if(!empty($_POST['id'])){
	$id = $_POST['id'];
	$data = array();
	$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
	
	
	if($con){
		$query = "SELECT COUNT(id) AS count FROM secretesantatable";
		$query_result = mysqli_query($con, $query);
		$values = mysqli_fetch_assoc($query_result);
		$num_rows = $values['count'];
		$id = rand(1, $num_rows);
		//echo $id ."<br>";	
		
		
		$sql = "select * from secretesantatable where id = ".$id;
		$result = mysqli_query($con, $sql);
		if(mysqli_num_rows($result) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($result)){
				$data[$i] = $row;
				$i++;
			}
			echo json_encode($data, JSON_PRETTY_PRINT);
		}
	}
	else
	{
		echo "Failed to connect to database";
	}
}


?>