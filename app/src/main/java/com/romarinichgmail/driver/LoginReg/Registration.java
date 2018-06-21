package com.romarinichgmail.driver.LoginReg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.romarinichgmail.driver.R;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private EditText mEmail, mPassword,mFio,mPhone,mConfirmPassword;
    private Button mRegistration;
    private static final String TAG = "Mosienko pes";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mDatabase;
    private DatabaseReference mDriverDatabase;

    FirebaseFirestore db = FirebaseFirestore.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Регистрация");
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegistration = (Button) findViewById(R.id.registration);
        mEmail = (EditText) findViewById(R.id.email);
        mFio = (EditText) findViewById(R.id.name);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationReg();
            }
        });
    }
    private void validationReg(){
        final String email = mEmail.getText().toString();
        final String name = mFio.getText().toString();
        final String password = mPassword.getText().toString();
        final String confirmPassword = mConfirmPassword.getText().toString();

        int lenghtEmail= email.length();
        int lenghtName= name.length();
        int lenghtPassword= password.length();

        if(lenghtEmail<4){
            Toast.makeText(Registration.this, "Email слишком короткий", Toast.LENGTH_SHORT).show();
        }
        else {
            if(lenghtName<1){
                Toast.makeText(Registration.this, "Введите имя", Toast.LENGTH_SHORT).show();
            }
            else{
                if(lenghtPassword<6){
                    Toast.makeText(Registration.this, "Пароль слишком короткий", Toast.LENGTH_SHORT).show();

                }
                else{
                    if (password.equals(confirmPassword)){
                        regEmail(email,password);
                    }
                    else {Toast.makeText(Registration.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }


    }

    private void regEmail(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Registration.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                }else{
                    getDataFromEdit();
                }
            }
        });
    }
    private void getDataFromEdit(){
        String fio = mFio.getText().toString();
        String email = mEmail.getText().toString();
        String user_id = mAuth.getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("name", fio);

        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
        mDriverDatabase.updateChildren(data);


        db.collection("driver").document(user_id)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Документ успешно записан!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Ошибка записи документа", e);
                    }
                });

        Intent intent = new Intent(Registration.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
