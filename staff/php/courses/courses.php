<?php
$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
$id = $_SESSION['id'];
$sql = "SELECT * FROM `courses_registered` WHERE staffID = $id";
$result = mysqli_query($conn , $sql);
$count = mysqli_num_rows($result);
if($count > 0){
    while($row = mysqli_fetch_assoc($result)){
       
            echo ' <tr>
            <td>'.$row['course_id'].'</td>
            <td>'.$row['course_name'].'</td>
            <td>'.$row['department'].'</td>
            <td>'.$row['studentID'].'</td>
            <td><a class="btn btn-sm btn-primary" href="details.php?id='.$row['studentID'].'">Detail</a></td>
        </tr>';
       
    }
}