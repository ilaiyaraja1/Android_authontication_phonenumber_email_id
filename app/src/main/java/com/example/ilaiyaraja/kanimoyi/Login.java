package com.example.ilaiyaraja.kanimoyi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
Button verify;
EditText Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Email=findViewById(R.id.et_signup_email);
        Password=findViewById(R.id.et_signup_password);
        verify=findViewById(R.id.verify);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loding");
        progressDialog.setMessage("Please Waiting...");
verify.setOnClickListener((view) ->{
    progressDialog.show();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    String email=Email.getText().toString();
    String password=Password.getText().toString();

    if (email.length()==0 || password.length()==0){
        Toast.makeText(Login.this,"enter email id (or) password",Toast.LENGTH_LONG).show();
        return;
    }
firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((task) ->{
    progressDialog.hide();
    if (task.isSuccessful()){
        if (firebaseAuth.getCurrentUser().isEmailVerified()){
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }else {
            Toast.makeText(Login.this,"plese verify your email",Toast.LENGTH_LONG).show();
        }

    }else {
        Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
    }
});

});


    }
}