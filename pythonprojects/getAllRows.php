<?php
header('Content-Type: application/json');
$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');

$checkMainTable = "select * from appliancetable";
$resultMainTable = mysqli_query($con, $checkMainTable);



	if(mysqli_num_rows($resultMainTable) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($resultMainTable)){
				$data[$i] = $row;
				$i++;
			}
			echo json_encode($data, JSON_PRETTY_PRINT);
			//print_r($data);
	}
	else
	{
		echo "Failed to connect to database";
	}





?>