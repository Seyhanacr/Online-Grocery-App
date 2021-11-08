package com.seyhn.acar.marketprojesi.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.adapters.NavCategoryAdapter;
import com.seyhn.acar.marketprojesi.adapters.PopularAdapters;
import com.seyhn.acar.marketprojesi.models.NavCategoryModel;
import com.seyhn.acar.marketprojesi.models.PopularModel;

import java.util.ArrayList;
import java.util.List;
//ana sayfa sol pencere category kısmı

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<NavCategoryModel> categoryModelList;
    NavCategoryAdapter navCategoryAdapter;
    FirebaseFirestore db;
    ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      View root= inflater.inflate(R.layout.fragment_category,container,false);

      db=FirebaseFirestore.getInstance();
      recyclerView= root.findViewById(R.id.cat_rec);

        progressBar=root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        //kategory

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        categoryModelList=new ArrayList<>();
        navCategoryAdapter= new NavCategoryAdapter(getActivity(),categoryModelList);
        recyclerView.setAdapter(navCategoryAdapter);

        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NavCategoryModel navCategoryModel= document.toObject(NavCategoryModel.class);
                                categoryModelList.add(navCategoryModel);
                                navCategoryAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                        }
                    }
                });

        return root;
    }


}