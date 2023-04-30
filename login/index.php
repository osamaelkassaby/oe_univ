<?php

$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
if(isset($_POST['login'])){
    $data = array();
    $user = strip_tags($_POST['username']);
    $password = strip_tags($_POST['password']);
    $sql = "SELECT studentID , token  FROM `std_login` WHERE active = 1 AND username = '$user' AND password = '$password'";
    $result = mysqli_query($conn , $sql);
    $count = mysqli_num_rows($result);
    if($count > 0){
        while($row = mysqli_fetch_assoc($result)){
            array_push($data , $row);
        }
    }
    echo json_encode($data);

}


?>