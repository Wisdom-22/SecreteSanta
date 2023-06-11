<?php
header('Content-Type: application/json');

$price = "";
$description = "";
$pricesArray = array();
$descriptionArray = array();
$combined = [];
$imagesUrl = array();
$count = 0;
$con = mysqli_connect('localhost', 'root', '', 'secretesantadatabase');
$data = array();


exec("python ./projectGame.py", $output, $resultcode);
foreach($output as $category => $category_array){
	$output = $category_array;
	#echo $output . "<br>";
	
	#get the description
	$index1 = strpos($output, ":");
	#echo $index1 ."<br>";
	$description = substr($output, 0, $index1);
	$descriptionArray[] = $description;
	#echo $description ."<br>"; 
	
	#get the price
	$index2 = strpos($output, ":");
	$limit = strpos($output, '&&');
	#echo $length ."<br>";
	#echo $index2 ."<br>";
	
	$price = substr($output, $index2+1, 7);
	$pricesArray[] = $price;
	#echo $price ."<br>";
	
	#get url
	$index3 = strpos($output, '&&');
	$urlLength = strlen($output);
	#echo $urlLength ."<br>";
	#echo $index3 ."<br>";
	
	$url = substr($output, $index3+2, $urlLength);
	$imagesUrl[] = $url;
	#echo $url ."<br>";
	
	#echo "<br>";
	
}

#combine the two arrays
for($x = 0; $x<sizeof($descriptionArray); $x++){
	$combined[$descriptionArray[$x]] = $pricesArray[$x];
}

$checkMainTable = "select * from gamingtable";
$resultMainTable = mysqli_query($con, $checkMainTable);

if(mysqli_num_rows($resultMainTable) != 0){
	$clearTable = "delete from gamingtable";
	$resultClearTable = mysqli_query($con, $clearTable);

	foreach($combined as $key => $value){
		$url = $imagesUrl[$count];
		$count++;
		#echo $key."<br>";
		#echo $value ."<br>";
		$sqlInsert = "insert into gamingtable (id, description, prices, url) values('".$count."','".$key."','" .$value."','" .$url."')";
		$resultInsert = mysqli_query($con, $sqlInsert);
		
	}
	
	$i = 0;
	while($row = mysqli_fetch_assoc($resultMainTable)){
		$data[$i] = $row;
		$i++;
	}
	echo json_encode($data, JSON_PRETTY_PRINT);
}else{
	foreach($combined as $key => $value){
		$url = $imagesUrl[$count];
		$count++;
		#echo $key."<br>";
		#echo $value ."<br>";
		$sqlInsert = "insert into gamingtable (id, description, prices, url) values('".$count."','".$key."','" .$value."','" .$url."')";
		$resultInsert = mysqli_query($con, $sqlInsert);
		
	}
	
	$i = 0;
	while($row = mysqli_fetch_assoc($resultMainTable)){
		$data[$i] = $row;
		$i++;
	}
	echo json_encode($data, JSON_PRETTY_PRINT);
}


?>