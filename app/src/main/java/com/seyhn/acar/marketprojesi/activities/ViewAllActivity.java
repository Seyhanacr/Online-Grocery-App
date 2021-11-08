package com.seyhn.acar.marketprojesi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.adapters.ViewAllAdapter;
import com.seyhn.acar.marketprojesi.models.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;

    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        firestore = FirebaseFirestore.getInstance();
        String type= getIntent().getStringExtra("type");
        recyclerView= findViewById(R.id.view_all_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this,viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);


        //mexplore categories(kategorilerikeşfet) meyve almak
        if (type != null && type.equalsIgnoreCase("fruit")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        //explore categories(kategorilerikeşfet) sebze almak
        if (type != null && type.equalsIgnoreCase("vegetable")) {
                firestore.collection("AllProducts").whereEqualTo("type", "vegetable").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                            viewAllModelList.add(viewAllModel);
                            viewAllAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        }
        //explore categories(kategorilerikeşfet) balık almak
        if (type != null && type.equalsIgnoreCase("fish")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fish").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        //explore categories(kategorilerikeşfet) yumurta almak
        if (type != null && type.equalsIgnoreCase("egg")) {
            firestore.collection("AllProducts").whereEqualTo("type", "egg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        //explore categories(kategorilerikeşfet) süt almak
        if (type != null && type.equalsIgnoreCase("milk")) {
            firestore.collection("AllProducts").whereEqualTo("type", "milk").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}