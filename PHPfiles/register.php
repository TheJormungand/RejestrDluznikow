<?php
    if(
        isset($_GET['login']) &&
        isset($_GET['password']) &&
        isset($_GET['email'])
    ){
        $con=mysqli_connect("localhost","root","root","rejestr");
        if ( !$con ) {
            printf("connect error: %s\n", mysqli_error($con));
        }
        $login = mysqli_real_escape_string($con,$_GET['login']);        
        $password = mysqli_real_escape_string($con,$_GET['password']);
        $email = mysqli_real_escape_string($con,$_GET['email']);
        $result = mysqli_query($con, "insert into user (Login, Password, Email) values ('$login','$password','$email')");
        if ( !$result ) {
            printf("query error: %s\n", mysqli_error($con));
        }
    }  
?>
