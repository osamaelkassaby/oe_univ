<?php
$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
if(isset($_POST['submit'])){
    $token = $_SESSION['token'];
    $id = $_SESSION['staffID'];
    $title = $_POST['title'];
    $message = $_POST['message'];
    $url  = $_POST['url'];
    $sql = "INSERT INTO `notification`(`title` , `url` , `message` , `staffID`) VALUES('$title' , '$url' , '$message' , $id)";
    $result = mysqli_query($conn , $sql);
    if($result){
        echo ' <div class="p-2 mb-2 bg-success text-white"> success </div> ';
    }else{
        echo ' <div class="p-2 mb-2 bg-danger text-white"> Error </div> ';
    }
}

?>