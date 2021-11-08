package com.seyhn.acar.marketprojesi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.models.UserModel;
//kayıt oluşturma kısmı
public class RegistrationActivity extends AppCompatActivity {
    Button signUp;
    EditText name,email,password;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        signUp =findViewById(R.id.reg_btn);
        name =findViewById(R.id.name);
        email =findViewById(R.id.email_reg);
        password =findViewById(R.id.password_reg);
        signIn = findViewById(R.id.sing_in);

        //giriş sayfasına yönlendirme
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        //kayıt yapmada progressbar çubuğunun yüklenmesi
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }

    //kayıt yaparken eksik veya hatalı giriş yapmada uyarılma
    private void createUser() {
        String userName= name.getText().toString();
        String userEmail= email.getText().toString();
        String userPassword= password.getText().toString();
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length()<6){
            Toast.makeText(this, "Password length must be great then 6 letter ", Toast.LENGTH_SHORT).show();
            return;
        }

        //kayıt oluşturmada onayı almak veya hata almak
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel userModel= new UserModel(userName,userEmail,userPassword);
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("users").child(id).setValue(userModel);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}