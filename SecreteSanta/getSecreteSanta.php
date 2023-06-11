<?php
//header('Content-Type: application/json');
if(!empty($_POST['username']) ){
	$username = $_POST['username'];  //"Damon";
	
	$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
	
	//check to see if the secrete_santa column has been is filled or an empty string
	$sqlSecreteSanta = "select secrete_santa from group_table where users_name = '$username'";
	$resultSecreteSanta = mysqli_query($con, $sqlSecreteSanta);
	$arySecreteSanta = array();
	$checkSecreteSanta = "";
	
	if(mysqli_num_rows($resultSecreteSanta) != 0){
		$i = 0;
		while($row = mysqli_fetch_assoc($resultSecreteSanta)){
			$arySecreteSanta[$i] = $row;
			
			$i++;
		}
		
		foreach($arySecreteSanta as $category => $category_array){
				$checkSecreteSanta = $category_array['secrete_santa'];
				
		}
		
		//echo "<br>";
		echo json_encode($arySecreteSanta, JSON_PRETTY_PRINT);
		
	}
	
	//if it an empty string do what is in the if statement
	if($checkSecreteSanta == ""){
		$sqlSelectGroupName = "select group_name from group_table where users_name = '$username'";
		$resultSelectGroupName = mysqli_query($con, $sqlSelectGroupName);
		$arySelectGroupName = array();
		$checkGroupName = "";
	
		if(mysqli_num_rows($resultSelectGroupName) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($resultSelectGroupName)){
				$arySelectGroupName[$i] = $row;
				
				$i++;
			}
			
			foreach($arySelectGroupName as $category => $category_array){
					$checkGroupName = $category_array['group_name'];
			}
			
			//echo $checkGroupName ."<br>";
			//echo json_encode($arySelectGroupName, JSON_PRETTY_PRINT);
		}
	
		$sqlSelectUsers = "select users_name from group_table where group_name = '$checkGroupName'";
		$resultSelectUsers = mysqli_query($con, $sqlSelectUsers);
		$arySelectUsersname = array();
		$checkSelectUsersname = "";
		$ary = array();
		
		//select all names from the secrete_santa column to make sure when you are selecting a secrete_santa that is not in the column
		$sqlSelectSecreteSanta = "select secrete_santa from group_table where group_name = '$checkGroupName'";
		$resultSelectSecreteSanta = mysqli_query($con, $sqlSelectSecreteSanta);
		$arySelectSecreteSanta = array();
		$checkSecreteSanta = "";
		$arySanta = array();
		
		
		if(mysqli_num_rows($resultSelectUsers) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($resultSelectUsers)){
				$arySelectUsersname[$i] = $row;
				
				$i++;
			}
		
			foreach($arySelectUsersname as $category => $category_array){
					$checkSelectUsersname = $category_array['users_name'];
					$ary[] = $checkSelectUsersname;
					
			}
		}	
			if(mysqli_num_rows($resultSelectSecreteSanta) != 0){
				$i = 0;
				while($row = mysqli_fetch_assoc($resultSelectSecreteSanta)){
					$arySelectSecreteSanta[$i] = $row;
					
					$i++;
				}
				
				foreach($arySelectSecreteSanta as $category => $category_array){
						$checkSecreteSanta = $category_array['secrete_santa'];
						$arySanta[] = $checkSecreteSanta;
						
				}
			}
			
			echo json_encode($ary, JSON_PRETTY_PRINT);
			echo json_encode($arySanta, JSON_PRETTY_PRINT);
			echo "<br>";
			$selectedUser = "";
			$isChecked = false;
			$index = 0;
			$max = count($ary) - 1;
			
			$counter = 0;
			$maximum = 0;
			$limit = count($ary)-1;
			while($isChecked == false){
				$count = count($ary) - 1;
				$id = rand(0, $count);
					
				$selectedUser = $ary[$id];
				
				//make sure that the $selectedUser is not is not the secrete_santa of the $username that was just assigned
				$sqlSelectDouble = "select users_name from group_table where secrete_santa = '$selectedUser'";
				$resultSelectDouble = mysqli_query($con, $sqlSelectDouble);
				$arySelectDouble = array();
				$checkSelectDouble = "";
				$arySelectDouble1 = array();
				
				if(mysqli_num_rows($resultSelectDouble) != 0){
					$i = 0;
					while($row = mysqli_fetch_assoc($resultSelectDouble)){
						$arySelectDouble[$i] = $row;
						
						$i++;
					}
					$maximum = count($arySelectDouble1) - 1;
					//while($counter < $maximum){
						foreach($arySelectDouble as $category => $category_array){
							$checkSelectDouble = $category_array['users_name'];
							$arySelectDouble1[] = $checkSelectDouble;
							
						}
						//$counter++;
					//}
				}		

				//echo json_encode($arySelectDouble1, JSON_PRETTY_PRINT);
				
				
				if(in_array($selectedUser, $arySanta) == false && $selectedUser != $username && $selectedUser != $checkSelectDouble){// in_array($selectedUser, $arySelectDouble1) == false){
					//echo "<br>";
					//echo $selectedUser;
					//echo "<br>";
					$isChecked = true;
				}
				
				
			}
			
			$counter = 0;
				//$sql = "update crud_table set data = '".$data."' where id = ". $id;
				$sql = "update group_table set secrete_santa = '".$selectedUser."' where users_name = '$username'";		
				$result = mysqli_query($con, $sql);
		
	
		$sqlSecreteSanta = "select secrete_santa from group_table where users_name = '$username'";
		$resultSecreteSanta = mysqli_query($con, $sqlSecreteSanta);
		$arySecreteSanta = array();
		$checkSecreteSanta = "";
		
		if(mysqli_num_rows($resultSecreteSanta) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($resultSecreteSanta)){
				$arySecreteSanta[$i] = $row;

				$i++;
			}
			
			foreach($arySecreteSanta as $category => $category_array){
				$arySecreteSanta = $category_array['secrete_santa'];
					
			}
			
			//echo "<br>";
			echo json_encode($arySecreteSanta, JSON_PRETTY_PRINT);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
	
	
?>