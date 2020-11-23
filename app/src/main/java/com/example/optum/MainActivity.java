package com.example.optum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getZoomLink();
        //setZoomOff();

    }
    private void setZoomOff() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctor");

        // Write from the database
        myRef.child("1").child("Status").setValue("1");
    }
    private void setZoomOn() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctor");

        // Write from the database
        myRef.child("1").child("Status").setValue("0");
    }

    private void getZoomLink() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Doctor");

        // Read from the database
        Log.e("TAG", myRef.child("1").child("Name").toString());
        myRef.orderByChild("Status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e("TAG", "onDataChange: " + snapshot.child("Name").getValue()  + " " + snapshot.child("Status").getValue());
                    if(snapshot.child("Status").getValue().toString().equals("0")){
                        Log.e("TAG", "onDataChange: Found" );
                        Log.e("TAG", "LINK for Zoom: "+ snapshot.child("Zoom").getValue().toString());
                        /* TODO: Show the zoom link on GUI. */
                        break;
                    }else{
                        Log.e("TAG", "onDataChange: Not Found");
                        /* TODO: display no doctors available */
                        break;
                    }
                }

                Log.e("Hello", "------------------------------------");
                String value = dataSnapshot.child("1").child("Name").getValue(String.class);
                Log.e("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read value.");
            }
        });

    }
}