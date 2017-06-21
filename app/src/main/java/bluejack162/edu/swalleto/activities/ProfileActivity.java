package bluejack162.edu.swalleto.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import bluejack162.edu.swalleto.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtName;
    EditText txtPass;
    EditText txtConfirm;
    Button btnUpdate;
    DatabaseReference dbReference;
    Intent i;
    String name, pass;
    boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createComponen();

    }

    private void createComponen(){
        i = getIntent();

        txtName = (EditText) findViewById(R.id.txtName);
        txtPass = (EditText) findViewById(R.id.txtNewPass);
        txtConfirm = (EditText) findViewById(R.id.txtConfPass);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        dbReference = FirebaseDatabase.getInstance().getReference("users");
    }


    @Override
    public void onClick(View view) {
        if (view  == btnUpdate) {
            status = true;
            name = txtName.getText().toString();
            pass = txtPass.getText().toString();
            String confirm = txtConfirm.getText().toString();
            if(pass.equals("")){
                status = false;
            }

            if (name.equals("")) {
                Toast.makeText(this, "Name must be filled", Toast.LENGTH_SHORT).show();
            } else if (pass.equals("") && status) {

            } else if (confirm.equals("") && status) {
                Toast.makeText(this, "Confirm password must be filled", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirm)) {
                Toast.makeText(this, "Confirm password not same", Toast.LENGTH_SHORT).show();
            } else {
                Query databaseRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(i.getStringExtra("id"));

                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String email = userSnapshot.child("userEmail").getValue().toString();
                            if (i.getStringExtra("email").equals(email)) {
                                dbReference.child(i.getStringExtra("id")).child("userName").setValue(name);
                                if(status) {
                                    dbReference.child(i.getStringExtra("id")).child("userPassword").setValue(pass);
                                }
                                i.putExtra("name", name);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
