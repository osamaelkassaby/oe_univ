<?php
if($_SESSION){
    $hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
if(isset($_GET['id'])){
    $id  = $_GET['id'];
    $sql = "SELECT firstname , lastname , gpa , level , faculty_name ,
     student_id FROM faculties INNER JOIN students ON students.id = faculties.student_id WHERE student_id = $id;
    ";
    $result = mysqli_query($conn , $sql);
    $count = mysqli_num_rows($result);
   
    if($count > 0){
        while($row = mysqli_fetch_assoc($result)){
            echo '
            
            
            <tr>
            <th scope="row"> '.$row['student_id'].' </th>
            <td>'.$row['firstname'].'</td>
            <td>'.$row['lastname'].'</td>
            <td>'.$row['faculty_name'].'</td>
            <td>'.$row['gpa'].'</td>
        </tr>
            
            
            ';
           
        }
    }else{
        echo '<img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">';
    }
}
}else{
    header("Location:signin.php");
}
