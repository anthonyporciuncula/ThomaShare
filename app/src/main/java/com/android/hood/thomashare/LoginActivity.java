package com.android.hood.thomashare;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;



//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.OptionalPendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView appName;
    private TextView lblUsername;
    private TextView tvPassword;
    private TextView tvForgotPassword;
    private EditText etEmail;
    private  EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    private ProgressBar pb_login;
    public  String hasChosen;
    Typeface pacifico;
    Typeface ralewayRegular;
    private DatabaseReference mDatabase, mChosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         appName = (TextView) findViewById(R.id.appName);
         lblUsername = (TextView) findViewById(R.id.tvUsername);
         tvPassword = (TextView) findViewById(R.id.tvPassword);
         tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
         etEmail = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
         btnLogin = (Button)findViewById(R.id.btnLogin);
         btnSignup = (Button)findViewById(R.id.btnSignup);
        pb_login = (ProgressBar)findViewById(R.id.pb_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {
                    pb_login.setVisibility(View.VISIBLE);
                    lblUsername.setVisibility(View.INVISIBLE);
                    tvPassword.setVisibility(View.INVISIBLE);
                    tvForgotPassword.setVisibility(View.INVISIBLE);
                    etEmail.setVisibility(View.INVISIBLE);
                    etPassword.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                    btnSignup.setVisibility(View.INVISIBLE);

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    if(firebaseAuth.getCurrentUser().getUid() != null) {
                        mChosen = mDatabase.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("hasChosen");
                    }
                    else{
                        mChosen = mDatabase.child("user").child(user.getUid()).child("hasChosen");
                    }

                    mChosen.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if ( dataSnapshot.getValue(String.class).equals("true")) {
                                pb_login.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                            } else {
                                pb_login.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                startActivity(new Intent(LoginActivity.this, AddInterest.class));

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                else{
                    pb_login.setVisibility(View.GONE);
                }
            }
        };
//         Set the dimensions of the sign-in button.
//        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);

//        font
        pacifico = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Pacifico-Regular.ttf");
        ralewayRegular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Raleway/Raleway-Regular.ttf");

        appName.setTypeface(pacifico);
        lblUsername.setTypeface(ralewayRegular);
        tvPassword.setTypeface(ralewayRegular);
        btnLogin.setTypeface(ralewayRegular);
        btnSignup.setTypeface(ralewayRegular);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Login();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void Login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Sign in failed. Check your username/password.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    public void SignUpPage (View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}
