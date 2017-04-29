package utility.injectors;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Cerjeff Pineda on 3/6/2017.
 */
@IgnoreExtraProperties
public class User {
    public String name;
    public String email;
    public String hasChosen;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String hasChosen) {
        this.name = name;
        this.email = email;
        this.hasChosen = hasChosen;
    }

}
