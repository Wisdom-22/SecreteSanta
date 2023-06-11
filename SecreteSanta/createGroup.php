<?php
//header('Content-Type: application/json');
if(!empty($_POST['groupname']) && !empty($_POST['usersname']) && !empty($_POST['creator'])){ //here creator is the username of person creating the group
	$groupname = $_POST['groupname']; // "group1"; 
	$usersname =   $_POST['usersname']; // "klause";
	$creator =   $_POST['creator']; // "Damon"; 
	
	$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
	$arrayCreatorPresnt = array();
	
	//check if the creator is in the group_creators table
	$sql = "select * from group_creators where created_by = '$creator'";
	$result = mysqli_query($con, $sql);
	$creatorPresent = false;
	
	if(mysqli_num_rows($result) != 0){
		$i = 0;
		while($row = mysqli_fetch_assoc($result)){
			$arrayCreatorPresnt[$i] = $row;
			
			$i++;
		}

		$checkgroupname = "";
		$checkcreatedby = "";
		$checkName ="";
		//assigns the group_name and created_by entities to two strings
		foreach($arrayCreatorPresnt as $category => $category_array){
			//echo $category_array['users_name'];
			$checkgroupname = $category_array['group_name'];
			$checkcreatedby = $category_array['created_by'];

		}
		
		//if they are thesame with what you sent from android  
		if($checkcreatedby == $creator && $checkgroupname == $groupname){
			$creatorPresent = true;
			//if $checkcreatedby == True then i can add them to the group table with (group name and users name)
			//echo "<br>";
			//echo "True+"; 
			//echo "<br>";
			
			//select all from the main table (secretesantatable)
			$sqlSelectUsers = "select * from secretesantatable where username = '$usersname'";
			$resultSelectUsers = mysqli_query($con, $sqlSelectUsers);
			$arraySelectUsers = array();
		
			
			if(mysqli_num_rows($resultSelectUsers) != 0){
				$index = 0;
				while($row = mysqli_fetch_assoc($resultSelectUsers)){
					$arraySelectUsers[$index] = $row;
				
					$index++;
				}
				
				//after selection the selected entities from the secretesantatable will have the entities usersname and status assigned to two variables  
				$checkUsername = "";
				$checkStatus = "";
				foreach($arraySelectUsers as $category => $category_array){
					//echo $category_array['users_name'];
					$checkUsername = $category_array['username'];
					$checkStatus = $category_array['status'];
					

				}
				
				//++++++We are having dublicate usersname entries in group_table. we need to check if it is already in the table
				//we will check to see if usersname you want to add to group_table is already in group_table
				$sqlCheckUsersName = "select users_name from group_table where group_name = '$groupname'";
				$resultCheckUsersName= mysqli_query($con, $sqlCheckUsersName);
				$arrayCheckUsersName = array();
				$isUsersNamePresent = false;
				
				if(mysqli_num_rows($resultCheckUsersName) != 0){
					$index = 0;
					while($row = mysqli_fetch_assoc($resultCheckUsersName)){
						$arrayCheckUsersName[$index] = $row;
					
						$index++;
					}
					
					//assigns the usersname to a string
					$checkUsersName = "";
					foreach($arrayCheckUsersName as $category => $category_array){
						//echo $category_array['users_name'];
						$checkUsersName = $category_array['users_name'];
						
					//check to see if the name you want to add and the name that is already in the group_table are thesame. if they are thesame $isUsersNamePresent will be true.
					//it will only be added to the group_table if they are not thesame
						if($checkUsersName == $usersname){
							$isUsersNamePresent = true;
							break;
						}
					}
				}
				//in order that someone else does not add someone who is already in a group into another group we have to check that the person is in the group_table
				$sqlCheckUsersExist = "select users_name from group_table";
				$resultSelectUsers = mysqli_query($con, $sqlCheckUsersExist);
				$arrayCheckUsersExist = array();
				$doesUsersExist = false;
				
				if(mysqli_num_rows($resultSelectUsers) != 0){
					$index = 0;
					while($row = mysqli_fetch_assoc($resultSelectUsers)){
						$arrayCheckUsersExist[$index] = $row;
					
						$index++;
					}
					
					$checkUsersExist = "";
					foreach($arrayCheckUsersExist as $category => $category_array){
						//echo $category_array['users_name'];
						$checkUsersExist = $category_array['users_name'];
						if($checkUsersExist == $usersname){
							$doesUsersExist = true;
							break;
						}

					}
				}
				
 				
				###this part. before i add i check if it already exist
				
				//insert statement to add the user into the group_table
				if($checkUsername == $usersname && $checkStatus == "Registered" && $isUsersNamePresent == false && $doesUsersExist == false){
					$sqlInsertUser = "insert into group_table (group_name, users_name) values('".$groupname."', '".$usersname. "')";
					$resultInsertUser = mysqli_query($con, $sqlInsertUser);
					//echo "<br>";
					//echo "True"; 
					//echo "<br>";
					echo "Success";
					echo "<br>";
				}elseif($checkStatus != "Registered"){
					echo "Not Registered";
					echo "<br>";
				}elseif($doesUsersExist == false){
					echo "User is already in a group";
					echo "<br>";
				}
				
			}
			
			//i can also add the creator to the group he or she is creating if he or she is not already in the group
			$sqlCheckCreator = "select users_name from group_table where group_name = '$groupname'";
			$resultCheckCreator = mysqli_query($con, $sqlCheckCreator);
			$arrayCheckCreator = array();
			$isCreatorPresent = false;
			
			$sqlCheckUsers1 = "select group_name from group_table where users_name = '$usersname'";
			$resultCheckUsers1 = mysqli_query($con, $sqlCheckUsers1);
			$arrayCheckUsers1 = array();
			$checkUsersName = "";
			$checkGroupName = "";
			
			if(mysqli_num_rows($resultCheckCreator) != 0){
				$index = 0;
				while($row = mysqli_fetch_assoc($resultCheckCreator)){
					$arrayCheckCreator[$index] = $row;
				
					$index++;
				}
				
				foreach($arrayCheckCreator as $category => $category_array){
					//echo $category_array['users_name'];
					$checkUsersName = $category_array['users_name'];

				}
			}
			
			if(mysqli_num_rows($resultCheckUsers1) !=0 ){
				$index = 0;
				while($row1 = mysqli_fetch_assoc($resultCheckUsers1)){
					$arrayCheckUsers1[$index] = $row1;
				
					$index++;
				}
				foreach($arrayCheckUsers1 as $category => $category_array){
					//echo $category_array['users_name'];
					$checkGroupName = $category_array['group_name'];

				}
				
			}			
				
			//echo "<br>";
			//echo $checkUsersName ." - ". $checkGroupName; 
			//echo "<br>";
			if($usersname == $checkUsersName && $groupname == $checkGroupName){
				$isCreatorPresent = true;
				//echo "True from first side";
			}
			
			//in order that a creator does not add someone who is already in a group into another group we have to check that the creator is in the group_table
				$sqlCheckCreatorExist = "select users_name from group_table";
				$resultSelectCreator = mysqli_query($con, $sqlCheckCreatorExist);
				$arrayCheckCreatorExist = array();
				$doesCreatorExist = false;
				
				if(mysqli_num_rows($resultSelectCreator) != 0){
					$index = 0;
					while($row = mysqli_fetch_assoc($resultSelectCreator)){
						$arrayCheckCreatorExist[$index] = $row;
					
						$index++;
					}
					
					$checkUsersExist = "";
					foreach($arrayCheckCreatorExist as $category => $category_array){
						//echo $category_array['users_name'];
						$checkCreatorExist = $category_array['users_name'];
						if($checkCreatorExist == $usersname){
							$doesCreatorExist = true;
							break;
						}

					}
				}
			
			
			//if the $creator and $groupname are thesame then you do not need to add them to the group
			if($isCreatorPresent == false && $doesCreatorExist == false){
				$sqlInsertUser = "insert into group_table (group_name, users_name) values('".$groupname."', '".$creator. "')";
				$resultInsertUser = mysqli_query($con, $sqlInsertUser);
				echo "Success";
				echo "<br>";
			}elseif($doesUsersExist == false){
				echo "User is already in a group";
				echo "<br>";
			}
		}
			
	}
	else
	{
		//if this creator creating a group, add the creator to the group_creators table then add the users
		$sqlInsertCreator = "insert into group_creators (group_name, created_by) values('".$groupname."', '".$creator. "')";
		$resultInsertCreator = mysqli_query($con, $sqlInsertCreator);
		
		//then check to see if the creator is in the group_creators table
		$sql = "select * from group_creators where created_by = '$creator'";
		$result = mysqli_query($con, $sql);
		$creatorPresent = false;
		
		
		if(mysqli_num_rows($result) != 0){
			$i = 0;
			while($row = mysqli_fetch_assoc($result)){
				$arrayCreatorPresnt[$i] = $row;
				
				$i++;
			}

			//assigns the select group_name and created_by entities in the group to two strings
			$checkgroupname = "";
			$checkcreatedby = "";
			$checkName ="";
			foreach($arrayCreatorPresnt as $category => $category_array){
				//echo $category_array['users_name'];
				$checkgroupname = $category_array['group_name'];
				$checkcreatedby = $category_array['created_by'];

			}
			
			//if the groupname and created_by entities that are checked are thesame then you can then select the usersname that you want to add to this group
			if($checkcreatedby == $creator && $checkgroupname == $groupname){
				$creatorPresent = true;
				//if $checkcreatedby == True then i can add them to the group table with (group name and users name)
				//echo "<br>";
				//echo "True+"; 
				//echo "<br>";
				
				//select the username that you want to add to the group
				$sqlSelectUsers = "select * from secretesantatable where username = '$usersname'";
				$resultSelectUsers = mysqli_query($con, $sqlSelectUsers);
				$arraySelectUsers = array();
			
				
				if(mysqli_num_rows($resultSelectUsers) != 0){
					$index = 0;
					while($row = mysqli_fetch_assoc($resultSelectUsers)){
						$arraySelectUsers[$index] = $row;
					
						$index++;
					}
					
					//assigns the usersname and status to the two strings
					$checkUsername = "";
					$checkStatus = "";
					foreach($arraySelectUsers as $category => $category_array){
						//echo $category_array['users_name'];
						$checkUsername = $category_array['username'];
						$checkStatus = $category_array['status'];
						

					}
					
					//++++++We are having dublicate usersname entries in group_table. we need to check if it is already in the table
					//we will check to see if usersname is already in group_table
					$sqlCheckUsersName = "select users_name from group_table where group_name = '$groupname'";
					$resultCheckUsersName= mysqli_query($con, $sqlCheckUsersName);
					$arrayCheckUsersName = array();
					$isUsersNamePresent = false;
					
					if(mysqli_num_rows($resultCheckUsersName) != 0){
						$index = 0;
						while($row = mysqli_fetch_assoc($resultCheckUsersName)){
							$arrayCheckUsersName[$index] = $row;
						
							$index++;
						}
						
						$checkUsersName = "";
						foreach($arrayCheckUsersName as $category => $category_array){
							//echo $category_array['users_name'];
							$checkUsersName = $category_array['users_name'];
							if($checkUsersName == $usersname){
								$isUsersNamePresent = true;
								break;
							}
						}
					}
					
					//in order that someone else does not add someone who is already in a group into another group we have to check that the person is in the group_table
					$sqlCheckUsersExist = "select users_name from group_table";
					$resultSelectUsers = mysqli_query($con, $sqlCheckUsersExist);
					$arrayCheckUsersExist = array();
					$doesUsersExist = false;
					
					if(mysqli_num_rows($resultSelectUsers) != 0){
						$index = 0;
						while($row = mysqli_fetch_assoc($resultSelectUsers)){
							$arrayCheckUsersExist[$index] = $row;
						
							$index++;
						}
						
						$checkUsersExist = "";
						foreach($arrayCheckUsersExist as $category => $category_array){
							//echo $category_array['users_name'];
							$checkUsersExist = $category_array['users_name'];
							if($checkUsersExist == $usersname){
								$doesUsersExist = true;
								break;
							}

						}
					}
					
					
					
					if($checkUsername == $usersname && $checkStatus == "Registered" && $isUsersNamePresent == false && $doesUsersExist == false){
						$sqlInsertUser = "insert into group_table (group_name, users_name) values('".$groupname."', '".$usersname. "')";
						$resultInsertUser = mysqli_query($con, $sqlInsertUser);
						//echo "<br>";
						//echo "True"; 
						//echo "<br>";
						echo "Success";
						echo "<br>";
					}elseif($checkStatus != "Registered"){
						echo "Not Registered";
						echo "<br>";
					}elseif($doesUsersExist == false){
						echo "User is already in a group";
						echo "<br>";
					}
					
				}
			
			
			}
			
		
		}
		
		//i can also add the creator to the group he or she is creating if he or she is not already in the group
			//i can also add the creator to the group he or she is creating if he or she is not already in the group
			$sqlCheckCreator = "select users_name from group_table where group_name = '$groupname'";
			$resultCheckCreator = mysqli_query($con, $sqlCheckCreator);
			$arrayCheckCreator = array();
			$isCreatorPresent = false;
			
			$sqlCheckCreator1 = "select group_name from group_table where users_name = '$usersname'";
			$resultCheckCreator1 = mysqli_query($con, $sqlCheckCreator1);
			$arrayCheckCreator1 = array();
			$checkCreatorName = "";
			$checkGroupName = "";
			
			if(mysqli_num_rows($resultCheckCreator) != 0){
				$index = 0;
				while($row = mysqli_fetch_assoc($resultCheckCreator)){
					$arrayCheckCreator[$index] = $row;
				
					$index++;
				}
				
				foreach($arrayCheckCreator as $category => $category_array){
					//echo $category_array['users_name'];
					$checkCreatorName = $category_array['users_name'];

				}
			}
			
			if(mysqli_num_rows($resultCheckCreator1) !=0 ){
				$index = 0;
				while($row1 = mysqli_fetch_assoc($resultCheckCreator1)){
					$arrayCheckCreator1[$index] = $row1;
				
					$index++;
				}
				foreach($arrayCheckCreator1 as $category => $category_array){
					//echo $category_array['users_name'];
					$checkGroupName = $category_array['group_name'];

				}
				
			}			
				
				//echo "<br>";
				//echo $checkCreatorName ." - ". $checkGroupName; 
				//echo "<br>";
			if($usersname == $checkCreatorName && $groupname == $checkGroupName){
				$isCreatorPresent = true;
				//echo "True";
			}
			
			//in order that a creator does not add someone who is already in a group into another group we have to check that the creator is in the group_table
				$sqlCheckCreatorExist = "select users_name from group_table";
				$resultSelectCreator = mysqli_query($con, $sqlCheckCreatorExist);
				$arrayCheckCreatorExist = array();
				$doesCreatorExist = false;
				
				if(mysqli_num_rows($resultSelectCreator) != 0){
					$index = 0;
					while($row = mysqli_fetch_assoc($resultSelectCreator)){
						$arrayCheckCreatorExist[$index] = $row;
					
						$index++;
					}
					
					$checkUsersExist = "";
					foreach($arrayCheckCreatorExist as $category => $category_array){
						//echo $category_array['users_name'];
						$checkCreatorExist = $category_array['users_name'];
						if($checkCreatorExist == $usersname){
							$doesCreatorExist = true;
							break;
						}

					}
				}
			
			
			//if the $creator and $groupname are thesame then you do not need to add them to the group
			if($isCreatorPresent == false && $doesCreatorExist == false){
				$sqlInsertUser = "insert into group_table (group_name, users_name) values('".$groupname."', '".$creator. "')";
				$resultInsertUser = mysqli_query($con, $sqlInsertUser);
				echo "Success";
				echo "<br>";
			}elseif($doesUsersExist == false){
				echo "User is already in a group";
				echo "<br>";
			}

	}
	
	
	
	
	
	
	
}//end of the main if statement
	
	
	
	
	
?>