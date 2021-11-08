package com.seyhn.acar.marketprojesi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.adapters.NAvCategoryDetailedAdapter;
import com.seyhn.acar.marketprojesi.models.HomeCategory;
import com.seyhn.acar.marketprojesi.models.NavCategoryDetailedModel;
import com.seyhn.acar.marketprojesi.models.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class NAvCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NavCategoryDetailedModel> list;
    NAvCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        db=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        String type= getIntent().getStringExtra("type");



        recyclerView=findViewById(R.id.nav_cat_det_rec);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NAvCategoryDetailedAdapter(this,list);

        recyclerView.setAdapter(adapter);

        //explore categories(kategorilerikeşfet) içecek almak
        if (type != null && type.equalsIgnoreCase("drink")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "drink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel viewAllModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(viewAllModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}