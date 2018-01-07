<?php
    if(
        isset($_GET['login']) &&
        isset($_GET['email'])
    ){
        $con=mysqli_connect("localhost","root","","rejestrdlugow");
        if ( !$con ) {
            printf("connect error: %s\n", mysqli_error($con));
        }
        $login = mysqli_real_escape_string($con,$_GET['login']);        
        $email = mysqli_real_escape_string($con,$_GET['email']);
        $result = mysqli_query($con, "select count(Login) from user where user.Login='$login';");
        if ( !$result ) {
            printf("query error: %s\n", mysqli_error($con));
        }
		$q=mysqli_fetch_assoc($result);
		if($q['count(Login)']!=0){
			printf("loginoccupied");
		}
		else{
			printf("loginok");
		}
		        $result = mysqli_query($con, "select count(Email) from user where user.Email='$email';");
        if ( !$result ) {
            printf("query error: %s\n", mysqli_error($con));
        }
		$q=mysqli_fetch_assoc($result);
		if($q['count(Email)']!=0){
			printf("emailoccupied");
		}
		else{
			printf("emailok");
		}
		
    }  
?>
