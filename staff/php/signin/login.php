<?php
$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
if(isset($_POST['login'])){
  
    $email = strip_tags($_POST['email']);
    $password = strip_tags($_POST['password']);
    $sql = "SELECT * FROM `staff_login` WHERE username = '$email' AND password = '$password'";
    $result = mysqli_query($conn , $sql);
    $count = mysqli_num_rows($result);
    if($count > 0){
        while($row = mysqli_fetch_assoc($result)){
            if($row['active'] == 1){
                $_SESSION['id']       = $row['id'];
                $_SESSION['username'] = $row["username"];
                $_SESSION['staffID']  = $row['staffID'];
                $_SESSION['token']    = $row["token"];
                header("Location:index.php");
            }else{
                echo ' <div class="p-2 mb-2 bg-danger text-white"> your Acount is not Active </div> ';
            }
        }
    }else{
        echo ' <div class="p-2 mb-2 bg-danger text-white"> username or password wrong </div> ';

    }

}



?>