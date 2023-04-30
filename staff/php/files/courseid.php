<?php
if($_SESSION){
    if(isset($_POST['upload'])){
        print_r($_FILES);

    }
    $hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
$id  = $_SESSION['id'];
$sql = "SELECT * FROM `courses_registered` WHERE staffID = $id";
$result = mysqli_query($conn , $sql);
$count = mysqli_num_rows($result);
   
if($count > 0){
    while($row = mysqli_fetch_assoc($result)){
            echo '  
            <option value="'.$row['course_id'].'">'.$row['course_id'].'   '.$row['course_name'].'</option>

            
            ';
           
        }
    }else{
        echo ' <div class="p-2 mb-2 bg-danger text-white"> Error </div> ';
    }
}else{
    header("Location:signin.php");
}
