<?php
$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
$id = $_SESSION['id'];
$sql = "SELECT COUNT(course_id) FROM `courses` WHERE staffID = $id";
$result = mysqli_query($conn , $sql);
$count = mysqli_num_rows($result);
$total = "";
if($count > 0){
    while($row = mysqli_fetch_assoc($result)){
        echo '    <h6 class="mb-0">'.$row['COUNT(course_id)'].'</h6>     ';
       
    }
}