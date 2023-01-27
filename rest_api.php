<?php
if(isset($_REQUEST["colID"]) && isset($_REQUEST["colName"]) && isset($_REQUEST["colEmail"]) && isset($_REQUEST["colDOB"]) && isset($_REQUEST["colGender"]) && isset($_REQUEST["colState"])) {

 $studnum = $_REQUEST["colID"];
 $studname = $_REQUEST["colName"];
 $studemail = $_REQUEST["colEmail"];
 $studdob = $_REQUEST["colDOB"];
 $studgen = $_REQUEST["colGender"];
 $studstate = $_REQUEST["colState"];

 $user = "root";
 $password = "";
 $host = "localhost";
 $db_name = "dbstudent";

 $con = mysqli_connect($host,$user,$password,$db_name);

 $sql = "INSERT INTO student VALUES('$studnum','$studname','$studemail','$studdob','$studgen','$studstate')";

 if(mysqli_query($con,$sql))
 {
  echo "Data insertion into remote database success";
 }
 else
 {
  echo "Error while insertion into remote database";
 }
 mysqli_close($con);
}
?>