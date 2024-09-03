package com.example.orderfood.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private static final Pattern pass_pattern=
            Pattern.compile("^" +
                    "(?=.*[0-9])" + //it nhat 1 chu so
                    "(?=.*[a-z])" + //it nhat 1 chu thuong
                    "(?=.*[A-Z])" + //it nhat 1 chu hoa
                    "(?=.*[!@#$%^&*])" + //it nhat 1 ki tu dac biet
                    "(?=\\S+$)" + //k chua khoang trang
                    ".{6,}" + //it nhat 6 ki tu
                    "$"); //ket thuc chuoi
    private static final Pattern user_pattern=
            Pattern.compile("^" +
//                    "(?=.*[a-zA-Z])" + //bat ki chu cai nao
//                    "(?=.*[A-Z])" + //it nhat 1 chu hoa
                    "(?=\\S+$)" + //k chua khoang trang
                    ".{6,}" +
                    "$");
    EditText fullname, username, password,sdt ;
    Button signup;
    TextView login;
    DBHelper DB;
    private TextInputLayout textinputUser;
    private TextInputLayout textinputpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        TextInputEditText editText=findViewById(R.id.password);
//        TextInputLayout pwwrapper=findViewById(R.id.passwordWrapper);
//        TextInputLayout userwrapper=findViewById(R.id.usernameWrapper);
//        TextInputEditText edtuser=findViewById(R.id.username);
//
//        edtuser.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!user_pattern.matcher(charSequence).matches()){
//                    userwrapper.setError("user phải chứa chữ hoa chữ thường k chứa khoang trắng ");
//                }
//                else{
//                    userwrapper.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(charSequence.length()<5){
//                    pwwrapper.setError("Mat khau phai tren 5 ki tu");
//
////                if(!pass_pattern.matcher(charSequence).matches()){
////                    pwwrapper.setError("Password is too weak");
//                }
//                else{
//                    pwwrapper.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        textinputUser=findViewById(R.id.usernameWrapper);
        textinputpassword=findViewById(R.id.passwordWrapper);

        fullname=(EditText) findViewById(R.id.fullname);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        sdt=(EditText) findViewById(R.id.phonenumber);
        signup=(Button) findViewById(R.id.signupBtn);
        login=(TextView) findViewById(R.id.loginTxt);
        DB=new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String name = fullname.getText().toString();
                String phone = sdt.getText().toString();

                if (user.equals("") | pass.equals("")|name.equals("")|phone.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else if(!validateUsername() | !validatePassword()){
                    return;
                }
                else {
                    Boolean checkuser = DB.checkusername(user);
                    if (checkuser == false) {
                        Boolean insert = DB.insertData(user, pass,name,phone);
                        if (insert == true) {
                            Toast.makeText(SignUpActivity.this, "Signup successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        }else{
                            Toast.makeText(SignUpActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "User already exists! please login", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
    private boolean validateUsername() {
        String usernameInput = textinputUser.getEditText().getText().toString().trim();

//        if (usernameInput.isEmpty()) {
//            textinputUser.setError("Field can't be empty");
//            return false;
//        }else
            if(usernameInput.length()<6){
            textinputUser.setError("Username too short");
            return false;
        }
        else if (usernameInput.length() > 15) {
            textinputUser.setError("Username too long");
            return false;}
        else if(!user_pattern.matcher(usernameInput).matches()) {
            textinputUser.setError("must not contain spaces");
            return false;
        }
        else {
            textinputUser.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = textinputpassword.getEditText().getText().toString().trim();
//        if (passwordInput.isEmpty()) {
//            textinputpassword.setError("Field can't be empty");
//            return false;
//        } else
            if (!pass_pattern.matcher(passwordInput).matches()) {
            textinputpassword.setError("Password too weak");
            return false;
        } else {
            textinputpassword.setError(null);
            return true;
        }
    }
}