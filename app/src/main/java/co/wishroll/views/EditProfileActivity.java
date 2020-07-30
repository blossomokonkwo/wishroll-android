package co.wishroll.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wishroll.R;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";


    ImageButton backButton, editBannerButton;
    CircularImageView editProfilePicture;
    Button saveButton;
    EditText editEmail, editUsername, editName, editBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backButton = findViewById(R.id.backEditProfileView);
        editBannerButton = findViewById(R.id.editBanneButtonrProfileView);
        editProfilePicture = findViewById(R.id.editPictureProfileView);
        saveButton = findViewById(R.id.bsaveChanges);
        editEmail = findViewById(R.id.emailEdit);
        editUsername = findViewById(R.id.usernameEdit);
        editName = findViewById(R.id.nameEdit);
        editBio = findViewById(R.id.bioEdit);

        final String newEmail = editEmail.getText().toString().trim();
        final String newUsername = formatUsername(editUsername);
        final String newBio = editBio.getText().toString();
        final String newName = editName.getText().toString();





        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(newName) || TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newEmail)){
                    Toast.makeText(EditProfileActivity.this, "You missed a spot", Toast.LENGTH_LONG).show();
                }else if(!usernameIsValid(newUsername)){
                    Toast.makeText(EditProfileActivity.this, "Please enter a valid username", Toast.LENGTH_LONG).show();
                }else if(!emailIsVerified(newEmail)){
                    Toast.makeText(EditProfileActivity.this, "Please enter a valid email", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    public boolean usernameIsValid(String usernameInput){
        //Username Validation: no triple periods or underscores, no longer than 20 characters, no special characters

        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";

        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = usernamePat.matcher(usernameInput);

        return matcher.find();

    }

    public String formatUsername(EditText username){

        return username.getText().toString().toLowerCase().replace(' ', '_');
    }

    public static boolean emailIsVerified(String emailInput){
        //checks if email entry is in correct email form

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }
}