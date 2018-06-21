package com.romarinichgmail.driver.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.romarinichgmail.driver.R;


public class FragmentProfile extends Fragment{
    private static final String TAG = "Mosienko pes";
    private TextView pName;
    private TextView pNum_order;
    private TextView pEmail;
    private TextView pAddress;
    private TextView pRating;
    private TextView pPhone;


    private ImageView mProfileImage;
    private String userID;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FragmentProfile() {

    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_profile, container, false);
        pEmail = (TextView) v.findViewById(R.id.pEmail);
        pName = (TextView) v.findViewById(R.id.pName);
        pNum_order = (TextView) v.findViewById(R.id.pNum_order);
        pAddress = (TextView) v.findViewById(R.id.pAddress);
        pRating = (TextView) v.findViewById(R.id.pRating);
        pPhone = (TextView) v.findViewById(R.id.pPhone);
        mProfileImage=(ImageView)v.findViewById(R.id.profileImage);

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });



        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        loadDataFormFirebase();
        return v;


    }

    public void loadDataFormFirebase() {
        db.collection("driver").document(userID);
        DocumentReference infoDriver= db.collection("driver").document(userID);
        infoDriver.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder dataAddres = new StringBuilder("");
                    dataAddres.append(doc.getString("location"));
                    pAddress.setText(dataAddres.toString());

                    StringBuilder dataEmail = new StringBuilder("");
                    dataEmail.append(doc.getString("email"));
                    pEmail.setText(dataEmail.toString());//

                    StringBuilder dataName = new StringBuilder("");
                    dataName.append(doc.getString("name"));
                    pName.setText(dataName.toString());

                    StringBuilder dataPhone = new StringBuilder("");
                    dataPhone.append(doc.getString("phone"));
                    pPhone.setText(dataPhone.toString());

                    StringBuilder dataRating = new StringBuilder("");
                    dataRating.append(doc.getString("rating"));
                    pRating.setText(dataRating.toString());

                    StringBuilder dataNum_poezdok = new StringBuilder("");
                    dataNum_poezdok.append(doc.getString("num_poezdok"));
                    pNum_order.setText(dataNum_poezdok.toString());
                    
                }
            }
        });
    }





}
