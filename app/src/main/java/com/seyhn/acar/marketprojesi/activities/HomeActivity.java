package com.seyhn.acar.marketprojesi.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.seyhn.acar.marketprojesi.MainActivity;
import com.seyhn.acar.marketprojesi.R;

public class HomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth =FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        //kullanıcı önceden giriş yapmışsa uygulamaya tekrar girdiğinde bilgilendirme mesajı alır
        if(auth.getCurrentUser() != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            Toast.makeText(this, "Lütfen bekleyin zaten giriş yaptınız", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //giriş sayfasına geçiş
    public void login(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    //kayıt sayfasına geçiş
    public void registration(View view) {
        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
    }


}