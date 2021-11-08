package com.seyhn.acar.marketprojesi.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seyhn.acar.marketprojesi.R;
import com.seyhn.acar.marketprojesi.models.UserModel;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

//anasayfa sol pencere profil kısmının yapılması
    CircleImageView profileImg;
    EditText name,email,number, address;
    Button update;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile,container,false);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage =FirebaseStorage.getInstance();

        profileImg=root.findViewById(R.id.profile_img);
        name=root.findViewById(R.id.profie_name);
        email=root.findViewById(R.id.profie_email);
        number=root.findViewById(R.id.profie_number);
        address=root.findViewById(R.id.profie_address);
        update=root.findViewById(R.id.update);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    //profil rsmi güncelleme
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        //resim güncelleme işlemi
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });
        return root;
    }

    private void updateUserProfile() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data.getData() != null){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);

            //veri tabanında profile_picture adında  prfil resmi için dosya oluşturma
            final StorageReference reference=storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //profil resmi yüklendi bildirim mesajı
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //profil resmi güncellendi bildirim mesajı
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(), "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }


}
