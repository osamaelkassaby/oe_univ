<?php
$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
$token = $_SESSION['token'];
$sql = "SELECT img FROM `staff` WHERE token = '$token'";
$result = mysqli_query($conn , $sql);
$count = mysqli_num_rows($result);
$total = "";
if($count > 0){
    while($row = mysqli_fetch_assoc($result)){
        echo '   <img class="rounded-circle" src="'.$row['img'].'" alt="" style="width: 40px; height: 40px;">';
       
    }
}else{
    echo '<img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">';
}