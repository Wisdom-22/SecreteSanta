<?php
require "DataBase.php";
$db = new DataBase();

if(!empty($_POST['username']) && !empty($_POST['password']) && !empty($_POST['confirmPassword'])){
	$username = $_POST['username'];
	$password = $_POST['password'];
	$confirmPassword = $_POST['confirmPassword'];
	
	$password = password_hash($password, PASSWORD_DEFAULT);
	
	$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
	
	$sqlSelect = "select * from secretesantatable where username = '$username'";
	$resultSelect = mysqli_query($con, $sqlSelect);
		
	if($con){
		if(mysqli_num_rows($resultSelect) != 0){
			$sql = "update secretesantatable set password = '".$password."' where username = '$username'";
			echo "Password Update Success";
		}else{
			echo "Please Register";
		}
		
		//This was here for the error
		//if(mysqli_query($con, $sql)){
			//echo "Password Update Success";
		//}
		//else{
			//echo "Failed to insert data";
		//}
	
	}
	else
	{
		echo "Failed to connect to database";
	}
	
	
	
	
}
	
	
	
	
	
	
?>