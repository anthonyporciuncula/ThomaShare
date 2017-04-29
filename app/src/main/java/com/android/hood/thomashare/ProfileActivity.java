package com.android.hood.thomashare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hood.thomashare.Adapters.InterestAdapter;
import com.android.hood.thomashare.Bean.Interest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;
    private List<Interest> interestList = new ArrayList<>();
    RecyclerView rv;
    TextView tvEmail;
    TextView tvName;
    InterestAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(ProfileActivity.this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName = (TextView) findViewById(R.id.tvName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        tvName.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());


        rv = (RecyclerView) findViewById(R.id.recycler_interest);
        rv.setHasFixedSize(true);
//        rv.setNestedScrollingEnabled(false);
        LinearLayoutManager llm = new LinearLayoutManager(this) {
        };
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
//


        Query query = mDatabase.child("interest").orderByChild(user.getUid()).equalTo("true");


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    interestList.add(new Interest(postSnapshot.getKey()));
                    createList(postSnapshot.getKey());
//                    Toast.makeText(getApplicationContext(), postSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                }
                ca = new InterestAdapter(interestList);
                rv.setAdapter(ca);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }


    private void createList(String interest) {
        interestList.add(new Interest(interest));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = new Intent();

        if (id == R.id.nav_discover) {
//            Toast.makeText(this, "DISCOVER PAGE", Toast.LENGTH_SHORT).show();

            intent = new Intent(this, Discover.class);
        } else if (id == R.id.nav_interests) {
//            Toast.makeText(this, "INTEREST PAGE", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, AddInterest.class);
        } else if (id == R.id.nav_explore) {
//            Toast.makeText(this, "UPLOAD", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, Explore.class);
//
        } else if (id == R.id.nav_upload) {
//            Toast.makeText(this, "UPLOAD", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, Upload.class);
        } else if (id == R.id.nav_logout) {
//            Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            intent = new Intent(this, LoginActivity.class);
        }



        startActivity(intent);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
