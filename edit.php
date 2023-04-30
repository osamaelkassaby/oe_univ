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
    $sql = "SELECT firstname,lastname,birth,gender,img,phone,address,email,username,password FROM students INNER JOIN std_login ON students.id = std_login.studentID WHERE students.token = '$token' AND students.id = $id";
    $result = mysqli_query($conn , $sql);
    $count = mysqli_num_rows($result);
    if($count > 0){
        while($row = mysqli_fetch_assoc($result)){
          //  echo base64_decode($row["img"]);
            array_push($data , $row);
        }
    }
   echo json_encode($data);

}else if(isset($_POST['edit'])){
  $img =  $_POST['img'];
  $filename="IMG".rand().".jpg";
  $email = strip_tags($_POST['email']);
  $phone = strip_tags($_POST['phone']);
  $address = strip_tags($_POST['address']);
  $token = strip_tags($_POST['token']);
  $id = strip_tags($_POST['id']);
  if(empty($img)){
    $sqlUpdate_WithOutImage = "UPDATE `students` SET `email` = '$email' ,  `phone`= '$phone' ,  `address` = '$address' WHERE token = '$token' AND id= $id";
    $result = mysqli_query($conn , $sqlUpdate_WithOutImage);
    if($result){
      echo"done";
      }else{
        echo "error";
      }
  }else{
    $imgFilePath = "http://10.10.1.64/oe_univ/files/images/".$filename;
    $sqlUpdate = "UPDATE `students` SET `email` = '$email' ,  `phone`= '$phone' ,  `address` = '$address' ,  `img` = '$imgFilePath' WHERE token = '$token' AND id= $id";
    file_put_contents("files/images/".$filename,base64_decode($img));
    $result = mysqli_query($conn , $sqlUpdate);
    if($result){
      echo"done";
      }else{
        echo "error";
      }
  }
  
}


?>