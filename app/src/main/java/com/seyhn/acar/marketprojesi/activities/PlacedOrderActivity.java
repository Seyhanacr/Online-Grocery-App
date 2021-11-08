package com.seyhn.acar.marketprojesi.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.models.MyCartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlacedOrderActivity extends AppCompatActivity {

    //satıln alma işleminin gerçkleştirilmesi
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        //sepetimdeki ürün sayısı en bir ve daha fazla ise satın alma işlemi gerçekleştirilebilir.
        List<MyCartModel> list= (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");
        if (list != null && list.size() > 0){

            for(MyCartModel model:list){
                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("productName",model.getProductName());
                cartMap.put("productPrice",model.getProductPrice());
                cartMap.put("currentData",model.getCurrentDate());
                cartMap.put("currentTime",model.getCurrentTime());
                cartMap.put("totalQuantity",model.getTotalQuantity());
                cartMap.put("totalPrice",model.getTotalPrice());

                //veritabanında CurrentUser altında MyOrder(siparişlerim) listesi oluşur
                firestore.collection("CurrentUser").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .collection("MyOrder")
                        .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PlacedOrderActivity.this, "Your order has been received (Siparişiniz Alındı)", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }
}