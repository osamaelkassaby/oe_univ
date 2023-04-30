<?php
if($_SESSION){
$hostname = "localhost";
$username = "root";
$passwd  = "";
$dbname  = "oe_univ";
$conn = mysqli_connect($hostname , $username , $passwd , $dbname);
$id  = $_SESSION['id'];
if(isset($_POST['upload'])){
    $courseID = $_POST['courseID'];
    $fileName = $_FILES['file']['name'];
    $fileSize = $_FILES['file']['size'];
    $fileTmpName  = $_FILES['file']['tmp_name'];
    $fileType = $_FILES['file']['type'];
    $didUpload = move_uploaded_file($fileTmpName, 'I:\api\oe_univ\staff\php\files\files\\'.$fileName);
        if($didUpload){
            $sql = "INSERT INTO `files`(`file_name` , `file_type` , `path` , `staffID` , `courseID`)
            VALUES('$fileName' , '$fileType' , 'http://10.10.1.64/oe_univ/staff/php/files/files/$fileName' , $id , '$courseID')";
            $result = mysqli_query($conn , $sql);
            if($result){
                echo '<div class="p-2 mb-2 bg-success text-white"> success </div> ';
            }else{
                echo ' <div class="p-2 mb-2 bg-danger text-white"> Error </div> ';
            }
           
        }else{
            echo ' <div class="p-2 mb-2 bg-danger text-white"> Error </div> ';

        }
    
  

}
    
}else{
    header("Location:signin.php");
}
?>