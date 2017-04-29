package utility.injectors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Cerjeff Pineda on 3/8/2017.
 */

public class CustomSignUp {
    public DatabaseReference mDatabase;

    //CREATE NON-GMAIL ACCOUNT
    public void createCustomAccount(FirebaseAuth mAuth, FirebaseAuth.AuthStateListener mAuthListener,
                                    final Context context, String email, String password,
                                    String confirmPassword, String firstName, String lastName)
    {
        final String displayName = firstName + " " + lastName;

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(firstName) ||
                TextUtils.isEmpty(lastName)) {

            Toast.makeText(context, "Please fill out all the fields.", Toast.LENGTH_LONG).show();

        } else {
            if (password.equals(confirmPassword)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
//                                    pbSignUp.setVisibility(View.GONE);
                                    Toast.makeText(context, "Sign up failed.", Toast.LENGTH_SHORT).show();
                                } else if (task.isSuccessful()) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    writeNewUser(user.getUid(), displayName, user.getEmail(), "false");
                                    customAccountInformation(user, displayName, context);
//                                    pbSignUp.setVisibility(View.GONE);

                                }
                            }
                        });
            } else {
                Toast.makeText(context, "Passwords don't match.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void customAccountInformation(FirebaseUser user, String displayName, final Context context) {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("E", "User activity_profile updated.");

                        }
                    }
                });
    }

    //Inject user data to firebase DB
    private void writeNewUser(String userId, String name, String email, String hasChosen) {

        User user = new User(name, email, hasChosen);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(userId).setValue(user);
    }
}