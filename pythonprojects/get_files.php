<?php
//$result = exec("C:\Users\Dell 15\AppData\Local\Programs\Python\Python311 get_files.py ");
//$result_array = json_decode($result);

//if(is_array($result_array) || is_object($result_array)){
//foreach($result_array as $row){
	//echo $row . "<BR>";
//}
//}

exec("python ./web_scrapping3.py", $output, $resultcode);
foreach($output as $category => $category_array){
	$output = $category_array;
					
}
echo $output;


?>