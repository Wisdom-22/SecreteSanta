<?php

$price = "";
$description = "";
$pricesArray = array();
$descriotionArray = array();
$conbined = [];
$count = 0;
$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');

#project.py
exec("python ./project.py", $output, $resultcode);
foreach($output as $category => $category_array){
	$output = $category_array;
	#echo $output."<br>";		

	#get the price
	$index1 = strpos($output, ":");
	#echo $index1 ."<br>";
	$price = substr($output, 0, $index1);
	$pricesArray[] = $price;
	#echo $price ."<br>"; 
	
	
	#get the description
	$index2 = strpos($output, ":");
	$length = strlen($output);
	#echo $length ."<br>";
	#echo $index2 ."<br>";
	
	$description = substr($output, $index2+1, $length);
	$descriotionArray[] = $description;
	#echo $description ."<br>";
	
	
}
#echo $output ."<br/>";

#print_r($pricesArray);
#print_r($descriotionArray);
#glpat-xkxqgbko2YBKv5dNJUAa

#combine the two arrays
for($x = 0; $x<sizeof($pricesArray); $x++){
	$conbined[$pricesArray[$x]] = $descriotionArray[$x];
}

$checkMainTable = "select * from mainTable";
$resultMainTable = mysqli_query($con, $checkMainTable);

if(mysqli_num_rows($resultMainTable) != 0){
	$clearTable = "delete from maintable";
	$resultClearTable = mysqli_query($con, $clearTable);

	foreach($conbined as $key => $value){
		$count++;
		$sqlInsert = "insert into mainTable (id, prices, description) values('".$count."','".$key."','" .$value."')";
		$resultInsert = mysqli_query($con, $sqlInsert);
		echo $key."<br>";
		echo $value ."<br>";
		
	}
}
else{
	foreach($conbined as $key => $value){
		$count++;
		$sqlInsert = "insert into mainTable (id, prices, description) values('".$count."','".$key."','" .$value."')";
		$resultInsert = mysqli_query($con, $sqlInsert);
		echo $key."<br>";
		echo $value ."<br>";
		
	}
}


#projectElectronics.py


?>