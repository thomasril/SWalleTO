package bluejack162.edu.swalleto.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bluejack162.edu.swalleto.R;
import bluejack162.edu.swalleto.models.User;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText txtName, txtEmail, txtPassword;
    TextView tvGTLogin;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        goToLogin();
        btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addUser();
                }
            });

    }

    private void init () {

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        tvGTLogin = (TextView) findViewById(R.id.tvGoToLogin);

        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    private void goToLogin() {
        tvGTLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addUser() {
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String pass = txtPassword.getText().toString();

        if (name == "")
            Toast.makeText(this, "Name must be filled!", Toast.LENGTH_SHORT).show();
        else if (email == "")
            Toast.makeText(this, "Email must be filled!", Toast.LENGTH_SHORT).show();
        else if (pass == "")
            Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
        else {
            String id = databaseUsers.push().getKey();
            User user = new User(id, name, email, pass);

            databaseUsers.child(id).setValue(user);
            Toast.makeText(this, "Register Sucess!", Toast.LENGTH_SHORT).show();
            txtName.setText("");
            txtEmail.setText("");
            txtPassword.setText("");
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }
}
