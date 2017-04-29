package com.android.hood.thomashare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.hood.thomashare.Adapters.FileListAdapter;
import com.android.hood.thomashare.Adapters.FileListRecyclerAdapter;
import com.android.hood.thomashare.Bean.FileList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Discover extends AppCompatActivity {
    ListView lvList;
//    ArrayList<FileList> myList = new ArrayList<FileList>();

    private List<FileList> fileList = new ArrayList<>();
    ArrayList<String> myList = new ArrayList<String>();
    ArrayList<String> myListUri = new ArrayList<String>();
    FileListAdapter fla;
    private DatabaseReference mDatabase;


    RecyclerView rv;
    FileListRecyclerAdapter flradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("files");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ProgressBar pb_Discover = (ProgressBar) findViewById(R.id.pb_discover);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        lvList = (ListView) findViewById(R.id.lvList);

        rv = (RecyclerView) findViewById(R.id.recycler_discover);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this) {
        };
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        Query query = mDatabase.orderByChild("dateUploaded");

        pb_Discover.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    interestList.add(new Interest(postSnapshot.getKey()));

                    createList(postSnapshot.child("displayName").getValue().toString(),
                            postSnapshot.child("downloadUri").getValue().toString(),
                            postSnapshot.child("fileTag").getValue().toString(),
                            postSnapshot.child("uploaderName").getValue().toString(),
                            postSnapshot.child("dateUploaded").getValue().toString());

//                    Toast.makeText(getApplicationContext(), postSnapshot.child("displayName").getKey(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), postSnapshot.child("downloadUri").getKey(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), postSnapshot.child("fileTag").getKey(), Toast.LENGTH_SHORT).show();

//                    myList.add(postSnapshot.child("displayName").getValue().toString());
                }

//                fla = new FileListAdapter(Discover.this, R.layout.row_files, fileList);
//                lvList.setAdapter(fla);
                flradapter = new FileListRecyclerAdapter(Discover.this, fileList);
                rv.setAdapter(flradapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
        pb_Discover.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void createList(String displayName, String downloadUri, String tag, String uploader, String dateTime) {
        fileList.add(new FileList(displayName, downloadUri, tag, uploader, dateTime));
    }
}
