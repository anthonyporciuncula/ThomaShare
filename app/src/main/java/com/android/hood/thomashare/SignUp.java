package com.android.hood.thomashare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utility.injectors.CustomSignUp;
import utility.injectors.User;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    private String displayName;
    Typeface pacifico;
    Typeface ralewayRegular;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private ProgressBar pbSignup;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        // [START set_font_styles]
        pacifico = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Pacifico-Regular.ttf");
        ralewayRegular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Raleway/Raleway-Regular.ttf");

        TextView tvSignup = (TextView) findViewById(R.id.tvSignup);
        TextView tvOr = (TextView) findViewById(R.id.tvOr);
        TextInputLayout tilFirstname = (TextInputLayout) findViewById(R.id.tilFirstname);
        TextInputLayout tilLastname = (TextInputLayout) findViewById(R.id.tilLastname);
        TextInputLayout tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        TextInputLayout tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        TextInputLayout tilConfirmPassword = (TextInputLayout) findViewById(R.id.tilConfirmPassword);
         pbSignup = (ProgressBar)findViewById(R.id.pb_signup);
            pbSignup.setVisibility(View.INVISIBLE);

        tvSignup.setTypeface(pacifico);
        tvOr.setTypeface(ralewayRegular);
        tilFirstname.setTypeface(ralewayRegular);
        tilLastname.setTypeface(ralewayRegular);
        tilEmail.setTypeface(ralewayRegular);
        tilPassword.setTypeface(ralewayRegular);
        tilConfirmPassword.setTypeface(ralewayRegular);
        // [END set_font_styles]


        //EditText Fields for custom account
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        etFirstName = (EditText)findViewById(R.id.etFirstname);
        etLastName = (EditText)findViewById(R.id.etLastname);


        //to String
        //Button on Click for Sign-up (Custom Account)
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            doCustomSignup();
            }
        });
    }


    public void doCustomSignup(){
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        context = getApplicationContext();
        CustomSignUp csu = new CustomSignUp();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(firstName) ||
                TextUtils.isEmpty(lastName)) {

            Toast.makeText(context, "Please fill out all the fields.", Toast.LENGTH_LONG).show();

        }else {
            if (password.equals(confirmPassword)) {
                pbSignup.setVisibility(View.VISIBLE);
                csu.createCustomAccount(mAuth, mAuthListener, context, email, password, confirmPassword, firstName, lastName);
            }

            else{
                Toast.makeText(context, "Passwords don't match.", Toast.LENGTH_SHORT).show();
            }

        }

        pbSignup.setVisibility(View.INVISIBLE);


    }

}
