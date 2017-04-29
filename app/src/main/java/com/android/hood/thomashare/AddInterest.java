package com.android.hood.thomashare;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hood.thomashare.Adapters.InterestAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddInterest extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Typeface ralewayLight;
    Typeface ralewayRegular;
    private CheckBox cbIT, cbCfad, cbEng, cbMed, cbHrm, cbEduc, cbArchi, cbAb, cbPhilo, cbLaw, cbMusic;
    private DatabaseReference mDatabase;
    String interestSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest);

        TextView tvMark = (TextView) findViewById(R.id.tvMark);
        final CheckBox cbAb = (CheckBox) findViewById(R.id.cbAb);
        final CheckBox cbArchi = (CheckBox) findViewById(R.id.cbArchi);
        final CheckBox cbCfad = (CheckBox) findViewById(R.id.cbCfad);
        final CheckBox cbEduc = (CheckBox) findViewById(R.id.cbEduc);
        final CheckBox cbEng = (CheckBox) findViewById(R.id.cbEng);
        final CheckBox cbHrm = (CheckBox) findViewById(R.id.cbHrm);
        final CheckBox cbIT = (CheckBox) findViewById(R.id.cbIT);
        final CheckBox cbLaw = (CheckBox) findViewById(R.id.cbLaw);
        final CheckBox cbMed = (CheckBox) findViewById(R.id.cbMed);
        final CheckBox cbMusic = (CheckBox) findViewById(R.id.cbMusic);
        final CheckBox cbPhilo = (CheckBox) findViewById(R.id.cbPhilo);
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Import font helper class
        ralewayLight = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Raleway/Raleway-Light.ttf");
        ralewayRegular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Raleway/Raleway-Regular.ttf");

        tvMark.setTypeface(ralewayRegular);
        cbAb.setTypeface(ralewayLight);
        cbArchi.setTypeface(ralewayLight);
        cbCfad.setTypeface(ralewayLight);
        cbEduc.setTypeface(ralewayLight);
        cbEng.setTypeface(ralewayLight);
        cbHrm.setTypeface(ralewayLight);
        cbIT.setTypeface(ralewayLight);
        cbLaw.setTypeface(ralewayLight);
        cbMed.setTypeface(ralewayLight);
        cbMusic.setTypeface(ralewayLight);
        cbPhilo.setTypeface(ralewayLight);

        btnSubmit.setTypeface(ralewayLight);


        Query query = mDatabase.child("interest").orderByChild(user.getUid()).equalTo("true");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    interestList.add(new Interest(postSnapshot.getKey()));
                    String currentInterest = postSnapshot.getKey();

//                    Toast.makeText(getApplicationContext(), postSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                  if(currentInterest!=null){
                    switch (currentInterest){
                        case "Arts and Letters" :
                            cbAb.setChecked(true);
                            break;
                        case "Architecture":
                            cbArchi.setChecked(true);
                            break;
                        case "Fine Arts and Design":
                            cbCfad.setChecked(true);
                            break;
                        case "Education":
                            cbEduc.setChecked(true);
                            break;
                        case "Engineering":
                            cbEng.setChecked(true);
                            break;
                        case "Hotel and Restaurant Management":
                            cbHrm.setChecked(true);
                            break;
                        case "Information Technology":
                            cbIT.setChecked(true);
                            break;
                        case "Law":
                            cbLaw.setChecked(true);
                            break;
                        case "Medicine":
                            cbMed.setChecked(true);
                            break;
                        case "Music":
                            cbMusic.setChecked(true);
                            break;
                        case "Philosophy":
                            cbPhilo.setChecked(true);
                            break;
                    }
                  }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                if(cbAb.isChecked()){
                    mDatabase.child("interest").child("Arts and Letters").child(user.getUid()).setValue("true");
                }
                if(!cbAb.isChecked()){
                    mDatabase.child("interest").child("Arts and Letters").child(user.getUid()).setValue("false");
                }

                if(cbArchi.isChecked()){
                    mDatabase.child("interest").child("Architecture").child(user.getUid()).setValue("true");
                }
                if(!cbArchi.isChecked()){
                    mDatabase.child("interest").child("Architecture").child(user.getUid()).setValue("false");
                }

                if(cbCfad.isChecked()){
                    mDatabase.child("interest").child("Fine Arts and Design").child(user.getUid()).setValue("true");
                }
                if(!cbCfad.isChecked()){
                    mDatabase.child("interest").child("Fine Arts and Design").child(user.getUid()).setValue("false");
                }

                if(cbEduc.isChecked()){
                    mDatabase.child("interest").child("Education").child(user.getUid()).setValue("true");
                }
                if(!cbEduc.isChecked()){
                    mDatabase.child("interest").child("Education").child(user.getUid()).setValue("false");
                }

                if(cbEng.isChecked()){
                    mDatabase.child("interest").child("Engineering").child(user.getUid()).setValue("true");
                }
                if(!cbEng.isChecked()){
                    mDatabase.child("interest").child("Engineering").child(user.getUid()).setValue("false");
                }

                if(cbHrm.isChecked()){
                    mDatabase.child("interest").child("Hotel and Restaurant Management").child(user.getUid()).setValue("true");
                }
                if(!cbHrm.isChecked()){
                    mDatabase.child("interest").child("Hotel and Restaurant Management").child(user.getUid()).setValue("false");
                }

                if(cbIT.isChecked()){
                    mDatabase.child("interest").child("Information Technology").child(user.getUid()).setValue("true");
                }
                if(!cbIT.isChecked()){
                    mDatabase.child("interest").child("Information Technology").child(user.getUid()).setValue("false");
                }

                if(cbLaw.isChecked()){
                    mDatabase.child("interest").child("Law").child(user.getUid()).setValue("true");
                }
                if(!cbLaw.isChecked()){
                    mDatabase.child("interest").child("Law").child(user.getUid()).setValue("false");
                }

                if(cbMed.isChecked()){
                    mDatabase.child("interest").child("Medicine").child(user.getUid()).setValue("true");
                }
                if(!cbMed.isChecked()){
                    mDatabase.child("interest").child("Medicine").child(user.getUid()).setValue("false");
                }

                if(cbMusic.isChecked()){
                    mDatabase.child("interest").child("Music").child(user.getUid()).setValue("true");
                }
                if(!cbMusic.isChecked()){
                    mDatabase.child("interest").child("Music").child(user.getUid()).setValue("false");
                }

                if(cbPhilo.isChecked()){
                    mDatabase.child("interest").child("Philosophy").child(user.getUid()).setValue("true");
                }
                if(!cbPhilo.isChecked()){
                    mDatabase.child("interest").child("Philosophy").child(user.getUid()).setValue("false");
                }
                mDatabase.child("user").child(user.getUid()).child("hasChosen").setValue("true");
                Intent intent = new Intent(AddInterest.this, ProfileActivity.class);
                startActivity(intent);
            }
        });



    }


}
