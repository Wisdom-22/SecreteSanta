<?php
if(!empty($_POST['username']) && !empty($_POST['status'])){
	$username = $_POST['username'];
	$status = $_POST['status'];
	$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
	//$sql = "update crud_table set data = '".$data."' where id = ". $id;
	
	if($con){
		$sql = "update secretesantatable set status = '".$status."' where username = '$username'";
		
		
		if(mysqli_query($con, $sql)){
			echo "Success";
		}
		else{
			echo "Failed to insert data";
		}
	
	}
	else
	{
		echo "Failed to connect to database";
	}
	
	
	
}
	
	
?>