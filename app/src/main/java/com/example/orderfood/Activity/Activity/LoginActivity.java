package com.example.orderfood.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText password;
    AutoCompleteTextView txAutoComplete;
    private ArrayList<String> ls;
    Button loginBtn;
    TextView signup;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        password=(EditText) findViewById(R.id.password1);
        loginBtn=(Button) findViewById(R.id.loginBtn);
        signup=(TextView)findViewById(R.id.signupTxt);
        DB = new DBHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=txAutoComplete.getText().toString();
                String pass = password.getText().toString();
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
      //  tao ds so xuong cho username
          txAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
          DB=new DBHelper(this);
          ls=DB.getUserName();
          ArrayAdapter<String> autocomplete=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ls);
          txAutoComplete.setAdapter(autocomplete);
    }

}