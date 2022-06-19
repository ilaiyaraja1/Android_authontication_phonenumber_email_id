package com.example.ilaiyaraja.kanimoyi;       ///Mainactivity2

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    EditText email,password;
    Button signup,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loding");
        progressDialog.setMessage("Please wait...");
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email = findViewById(R.id.et_signup_email);
        password = findViewById(R.id.et_signup_password);
        signup = findViewById(R.id.bt_signup_signup);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String Email=email.getText().toString();
                String Password=password.getText().toString();

                final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener((task) ->{
                    progressDialog.hide();
                    if (task.isSuccessful()){

                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"User registered successfully,Please verify your email",Toast.LENGTH_LONG).show();
                                    email.setText("");
                                    password.setText("");
                                }
                            }
                        });

                    }else {
                        Toast.makeText(MainActivity2.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}