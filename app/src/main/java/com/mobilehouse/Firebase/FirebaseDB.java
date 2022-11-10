package com.mobilehouse.Firebase;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.mobilehouse.ActivityMenu;

public class FirebaseDB {
    private FirebaseDatabase database;

    private static DatabaseReference ref_database;
    private static ValueEventListener valueEventListener;

    public void FirebaseDB(){
        this.database = FirebaseDatabase.getInstance();
        this.ref_database = null;
        this.valueEventListener = null;

    }
    private void ouvinteStatus() {
        this.ref_database = this.database.getReference().child("status");

        this.valueEventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int wifi = snapshot.child("wifi").getValue(int.class);
                int power = snapshot.child("power").getValue(int.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        this.ref_database.addValueEventListener(this.valueEventListener);
    }
}
