package com.seyhn.acar.marketprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.seyhn.acar.marketprojesi.activities.PlacedOrderActivity;
import com.seyhn.acar.marketprojesi.adapters.MyCartAdapter;
import com.seyhn.acar.marketprojesi.models.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    ProgressBar progressBar;
    Button buyNow;
    int totalBill;


    public  MyCartFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View root= inflater.inflate(R.layout.fragment_my_carts, container, false);
       db=FirebaseFirestore.getInstance();
       auth=FirebaseAuth.getInstance();

        progressBar=root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);


        overTotalAmount =root.findViewById(R.id.textView7);

        recyclerView=root.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        buyNow=root.findViewById(R.id.buy_now);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       cartModelList= new ArrayList<>();
       cartAdapter=new MyCartAdapter(getActivity(),cartModelList);
       recyclerView.setAdapter(cartAdapter);

       //sepete ekleme işlemi
       db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
               .collection("AddToCart")
               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {

               if (task.isSuccessful()){
                   for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                       String documentId=documentSnapshot.getId();
                       MyCartModel cartModel= documentSnapshot.toObject(MyCartModel.class);

                       cartModel.setDocumentId(documentId);
                       cartModelList.add(cartModel);
                       cartAdapter.notifyDataSetChanged();
                       progressBar.setVisibility(View.GONE);
                       recyclerView.setVisibility(View.VISIBLE);

                   }
                   calculateTotalAmount(cartModelList);
               }
           }
       });

       //siparişleri listeleme işlemi
       buyNow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getContext(), PlacedOrderActivity.class);
               intent.putExtra("itemList", (Serializable) cartModelList);
               startActivity(intent);
           }
       });
       return  root;

    }

    //sepetim kısmında toplam fiyatı gösterme
    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
        double totalAmount=0.0;
        for (MyCartModel myCartModel : cartModelList){
            totalAmount+= myCartModel.getTotalPrice();
        }
        overTotalAmount.setText("Total Amount :"+totalAmount);
    }
}