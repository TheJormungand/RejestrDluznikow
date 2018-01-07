<?php
    if(
        isset($_GET['login']) &&
        isset($_GET['password'])
        ){
        $con=mysqli_connect("localhost","root","","rejestrdlugow");
        if ( !$con ) {
            printf("connect error: %s\n", mysqli_error($con));
        }
        $login = mysqli_real_escape_string($con, $_GET['login']);
        $password = mysqli_real_escape_string($con, $_GET['password']);
        $result = mysqli_query($con, "select IDuser from user where Login = '$login' and Password = '$password';");
        if ( !$result ) {
            printf("error with query: %s\n", mysqli_error($con));
        }
        $q = mysqli_fetch_assoc($result);
        if ( !$result ) {
            printf("error with assoc: %s\n", mysqli_error($con));
        }
        printf("%s",$q['IDuser']);
    }
?>
