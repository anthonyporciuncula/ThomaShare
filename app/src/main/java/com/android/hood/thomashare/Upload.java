package com.android.hood.thomashare;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Date;

import utility.injectors.FileToDb;

public class Upload extends AppCompatActivity {

    private Button btn_search, btn_upload, btn_cancel, btn_selectTags;
    private TextView tv_fileName;
    private EditText et_displayName;
    private ProgressBar progress_Upload;

    private Uri uri;
    private StorageReference mStorage;
    private String uid, displayName, userName, fileTag, dateUploaded;
    private Intent intent;
    private static final int INTENT = 2;
    private DatabaseReference mDatabase;
    private LinearLayout tags_holder;
    private ProgressDialog progressDoalog;
    private double progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uid = user.getUid();
        userName = user.getDisplayName();

        mStorage = FirebaseStorage.getInstance().getReference();
        Log.d("firebase reference: ", mStorage.toString());
        Toast.makeText(this, mStorage.toString(), Toast.LENGTH_LONG).show();

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_selectTags = (Button) findViewById(R.id.btn_selectTags);
        tv_fileName = (TextView) findViewById(R.id.tv_fileName);
        et_displayName = (EditText) findViewById(R.id.et_displayName);
        tags_holder = (LinearLayout) findViewById(R.id.tags_container);

        final TextView txtTag = (TextView) View.inflate(this, R.layout.tags_layout, null);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, INTENT);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayName = et_displayName.getText().toString();

                if (TextUtils.isEmpty(displayName)) {
                    Toast.makeText(getApplicationContext(), "Please fill out all the fields.", Toast.LENGTH_LONG).show();
                } else {
                    doUpload(uri, uid);

                    progressDoalog = new ProgressDialog(Upload.this);
                    progressDoalog.setMax(100);
                    progressDoalog.setMessage("File Upload Progress");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDoalog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (progressDoalog.getProgress() <= progressDoalog
                                        .getMax()) {
                                    Thread.sleep(200);
                                    progressDoalog.setProgress((int)progress);
                                    if (progressDoalog.getProgress() == progressDoalog
                                            .getMax()) {
                                        progressDoalog.dismiss();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }

            }
        });


        btn_selectTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = mDatabase.child("interest");


                AlertDialog.Builder builderSingle = new AlertDialog.Builder(Upload.this);

                builderSingle.setTitle("Select One Name:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Upload.this, android.R.layout.select_dialog_singlechoice);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            arrayAdapter.add(postSnapshot.getKey());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                query.addValueEventListener(valueEventListener);


                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        fileTag = strName;
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(Upload.this);
                        txtTag.setVisibility(View.VISIBLE);
                        txtTag.setText(strName);

                    }
                });
                builderSingle.show();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent = new Intent(Upload.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        tags_holder.addView(txtTag);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
            tv_fileName.setText(uri.getLastPathSegment().toString());
        }
    }


    public void doUpload(final Uri uri, final String uid) {

        StorageReference filepath = mStorage.child(uid).child(uri.getLastPathSegment());
        filepath.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        dateUploaded = DateFormat.getDateTimeInstance().format(new Date());
                        writeFileToDb(displayName, userName, fileTag, downloadUrl.toString(), dateUploaded);
                        Toast.makeText(Upload.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent = new Intent(Upload.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        Log.d("On Failure: ", "TRAYDOR");
                    }
                })


                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    @SuppressWarnings("VisibleForTests")
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();


                    }
                });




    }



    public void writeFileToDb(String displayName, String uploaderName, String fileTag, String downloadUri, String dateUploaded) {
        FileToDb file = new FileToDb(displayName, uploaderName, fileTag, downloadUri, dateUploaded);
        mDatabase.child("files").child(displayName).setValue(file);
//        Toast.makeText(Upload.this, "Upload Success. DB write success.", Toast.LENGTH_LONG).show();
    }
}
