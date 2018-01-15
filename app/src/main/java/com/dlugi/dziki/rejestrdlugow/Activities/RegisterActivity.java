package com.dlugi.dziki.rejestrdlugow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dlugi.dziki.rejestrdlugow.JSON.RegisterDataCheckJSON;
import com.dlugi.dziki.rejestrdlugow.JSON.RegisterJSON;
import com.dlugi.dziki.rejestrdlugow.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Integer LoginMinLength = 6;
    private Integer LoginMaxLength = 12;
    private Integer PasswordMinLength=6;
    private Integer PasswordMaxLength=20;
    private EditText loginField,passwordField,confirmField,emailField;
    //private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginField = findViewById(R.id.Login);
        passwordField = findViewById(R.id.Haslo);
        emailField = findViewById(R.id.Email);
        confirmField = findViewById(R.id.Confirm);


    }
    public int getSpecialCharacterCount(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 2;//is empty or only whitespaces
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b)
            return 1;//has special char
        else
            return 0;//no special char
    }

    public boolean hasWhitespaces(String s){
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(s);
        boolean found = matcher.find();
        // boolean b = m.matches();
        if (found)
            return true;//has whitespace
        else
            return false;//no whitespaces
    }

    public int checkUsername(String s){
        if(s.length()<LoginMinLength||getSpecialCharacterCount(s)==2){
            Toast.makeText(this, "Enter a longer username!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(s.length()>LoginMaxLength){
            Toast.makeText(this, "Enter a shorter username!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(getSpecialCharacterCount(s)==1){
            Toast.makeText(this, "Username cannot contain special chars!", Toast.LENGTH_SHORT).show();
            return 1;
        }else return 0;
    }

    public int checkPassword(String pass1,String pass2){
        if(pass1.length()<PasswordMinLength||getSpecialCharacterCount(pass1)==2){
            Toast.makeText(this, "Enter a longer password!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(pass1.length()>PasswordMaxLength){
            Toast.makeText(this, "Enter a shorter password!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(getSpecialCharacterCount(pass1)==0){
            Toast.makeText(this, "Password must contain at least one special char!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(hasWhitespaces(pass1)){
            Toast.makeText(this, "Password cannot contain whitespaces!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(!pass1.equals(pass2)){
            Toast.makeText(this, "Password confirmation doesn't match!", Toast.LENGTH_SHORT).show();
            return 1;
        }else
            return 0;
    }

    public int checkEmail(String email){
        Pattern pattern = Pattern.compile("[.]");
        Matcher matcher = pattern.matcher(email);
        boolean founddot = matcher.find();
        pattern = Pattern.compile("[@]");
        matcher = pattern.matcher(email);
        boolean foundmonkey = matcher.find();
        // boolean b = m.matches();
        if (!founddot||!foundmonkey){
            Toast.makeText(this, "Enter a valid email!", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(getSpecialCharacterCount(email)==2){
            Toast.makeText(this, "Enter a valid email!", Toast.LENGTH_SHORT).show();
            return 1;
        }else return 0;
    }

    public void registerClick(View view){
        String username = loginField.getText().toString();
        String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        String confirmPassword = confirmField.getText().toString();
        boolean UserFlag=false;
        boolean EmailFlag=false;
        if(checkUsername(username)==0&&checkPassword(password,confirmPassword)==0&&checkEmail(email)==0) {
            new RegisterDataCheckJSON(this,username,password,email).execute(username,email);

        }

    }

}
