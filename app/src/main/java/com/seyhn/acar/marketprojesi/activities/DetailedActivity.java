package com.seyhn.acar.marketprojesi.activities;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.models.ViewAllModel;

import java.util.Calendar;
import java.util.HashMap;
//ürünlerin detay bölümünü görme
public class DetailedActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity=1;
    int totalPrice=0;
    ImageView detailedImg;
    TextView price,rating,description;
    Button addToCart;
    ImageView addItem,removeItem;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;

    FirebaseFirestore firestore;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel){

            viewAllModel= (ViewAllModel) object;
        }

        quantity =findViewById(R.id.quantity);
        detailedImg =findViewById(R.id.detailed_img);
        addItem =findViewById(R.id.add_item);
        removeItem =findViewById(R.id.remove_item);
        price =findViewById(R.id.detailed_price);
        rating =findViewById(R.id.detailed_rating);
        description =findViewById(R.id.detailed_dec);

        //ürünlerin türünü belirleme kg,litre,adet...
        if (viewAllModel!=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("price :$"+viewAllModel.getPrice()+"/kg");
            totalPrice=viewAllModel.getPrice() * totalQuantity;

            if (viewAllModel.getType().equals("egg")){
                price.setText("price :$"+viewAllModel.getPrice()+"/dozen");
                totalPrice=viewAllModel.getPrice() * totalQuantity;
            }
            if (viewAllModel.getType().equals("milk")){
                price.setText("price :$"+viewAllModel.getPrice()+"/litre");
                totalPrice=viewAllModel.getPrice() * totalQuantity;
            }

        }


        addToCart =findViewById(R.id.add_to_cart );
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });


        //ürün satın alma kısmında ürün sayısını artırma
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity< 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=viewAllModel.getPrice() * totalQuantity;
                }

            }
        });
        //ürün satın alma kısmında ürün sayısını azaltma
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=viewAllModel.getPrice() * totalQuantity;
                }

            }
        });

    }

    @SuppressLint("NewApi")
    private void addedToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        //ürünün sepete eklenme tarihi
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate= currentDate.format(calForDate.getTime());

        //ürünün sepete eklenme  saati
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime= currentTime.format(calForDate.getTime());
        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentData",saveCurrentDate);
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        //Veri tabanında CurrentUser altında AddToCart (sepete eklenen) bölümü
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart")
                .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}