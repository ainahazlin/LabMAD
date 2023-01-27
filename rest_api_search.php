<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "dbstudent";

// connect with database demo
$conn = new mysqli($servername, $username, $password, $dbname);

// an array to display response
$response = array();
// on below line we are checking if the parameter send is id or not.
If(isset($_POST['colID'])){
  // if the parameter send from the user id then
  // we will search the item for specific id.
  $studID = $_POST['colID'];
    //on below line we are selecting the course detail with below id.
  $stmt = $conn->prepare("SELECT colID,colName,colEmail,colDOB,colGender,colState FROM student WHERE colID =?");
  $stmt->bind_param("s",$studID);
  $result = $stmt->execute();
// on below line we are checking if our
// table is having data with specific id.
if($result == TRUE){
    // if we get the response then we are displaying it below.
    $response['error'] = false;
    $response['message'] = "Retrieval Successful!";
    // on below line we are getting our result.
    $stmt->store_result();
    // on below line we are passing parameters which we want to get.
    $stmt->bind_result($colID, $colName,$colEmail,$colDOB,$colGender,$colState);
    // on below line we are fetching the data.
    $stmt->fetch();
    // after getting all data we are passing this data in our array.
    $response['colID'] = $colID;
    $response['colName'] = $colName;
    $response['colEmail'] = $colEmail;
    $response['colDOB'] = $colDOB;
    $response['colGender'] = $colGender;
    $response['colState'] = $colState;
  } else{
    // if the id entered by user donot exist then
    // we are displaying the error message
    $response['error'] = true;
    $response['message'] = "Incorrect id";
  }
} else{
  // if the user donot adds any parameter while making request
  // then we are displaying the error as insufficient parameters.
  $response['error'] = true;
  $response['message'] = "Insufficient Parameters";
}
// at last we are printing
// all the data on below line.
echo json_encode($response);
?>