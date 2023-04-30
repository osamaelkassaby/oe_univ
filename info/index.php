<?php

$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
if(isset($_POST['info'])){
    $data = array();
    $id = strip_tags($_POST['id']);
    $token = strip_tags($_POST['token']);
    $sql = "SELECT *
    FROM students
    INNER JOIN faculties 
    ON students.id = faculties.student_id WHERE students.token = '$token' AND id = $id";
    $result = mysqli_query($conn , $sql);
    $count = mysqli_num_rows($result);
    if($count > 0){
        while($row = mysqli_fetch_assoc($result)){
          //  echo base64_decode($row["img"]);
            array_push($data , $row);
        }
    }
   echo json_encode($data);

}


?>