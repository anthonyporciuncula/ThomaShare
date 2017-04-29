package com.android.hood.thomashare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class Explore_fileList extends AppCompatActivity {

    ListView lvList;
    private List<FileList> fileList = new ArrayList<>();
    ArrayList<String> myList = new ArrayList<String>();
    FileListAdapter listAdapter;
    private DatabaseReference mDatabase;
    private ProgressBar pb_Explore;

    private TextView tv_explore_course;
    RecyclerView rv;
    FileListRecyclerAdapter flradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_file_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_explore_course = (TextView) findViewById(R.id.tv_explore_course);

        pb_Explore = (ProgressBar)findViewById(R.id.pb_Explore);
        rv = (RecyclerView) findViewById(R.id.recycler_explore_course);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this) {
        };
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        String course = getIntent().getStringExtra("course");

        switch (course){
            case "IT":
                tv_explore_course.setText("Information Technology");
                course = "Information Technology";
                break;
            case "CFAD":
                tv_explore_course.setText("Fine Arts and Design");
                course = "Fine Arts and Design";
                break;
            case "ENG":
                tv_explore_course.setText("Engineering");
                course = "Engineering";
                break;
            case "MED":
                tv_explore_course.setText("Medicine");
                course = "Medicine";
                break;
            case "HRM":
                tv_explore_course.setText("Hotel and Restaurant Management");
                course = "Hotel and Restaurant Management";
                break;
            case "EDUC":
                tv_explore_course.setText("Education");
                course = "Education";
                break;
            case "ARCHI":
                tv_explore_course.setText("Architecture");
                course = "Architecture";
                break;
            case "AB":
                tv_explore_course.setText("Arts and Letters");
                course = "Arts and Letters";
                break;
            case "PHIL":
                tv_explore_course.setText("Philosophy");
                course = "Philosophy";
                break;
            case "LAW":
                tv_explore_course.setText("Law");
                course = "Law";
                break;
            case "MSC":
                tv_explore_course.setText("Music");
                course = "Music";
                break;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("files");

//        Query query = mDatabase.orderByChild("displayName");
//        Query query2 = mDatabase.orderByChild("downloadUri");
        Query query3 = mDatabase.orderByChild("fileTag").equalTo(course);
        pb_Explore.setVisibility(View.VISIBLE);
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

//                    Toast.makeText(getApplicationContext(), postSnapshot.child("displayName").getValue().toString(), Toast.LENGTH_SHORT).show();
                }
                flradapter = new FileListRecyclerAdapter(Explore_fileList.this,fileList);
                rv.setAdapter(flradapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query3.addValueEventListener(valueEventListener);
        pb_Explore.setVisibility(View.INVISIBLE);
    }


    private void createList(String displayName, String downloadUri, String tag, String uploader, String dateTime) {
        fileList.add(new FileList(displayName, downloadUri, tag, uploader, dateTime));
    }
    //TEST DATA FOR LIST
    public void generateListContent(){
        for(int i = 0; i<10;i++){
            myList.add("This is row number " + i);
        }
    }
}
