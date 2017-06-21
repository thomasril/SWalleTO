package bluejack162.edu.swalleto.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import bluejack162.edu.swalleto.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    EditText txtEmail, txtPassword;
    TextView tvStatus, tvGTRegister;
    Button btnLogin, btnLogout;
    LoginButton btnFbLogin;
    SignInButton btnGmailLogin;
    CallbackManager callbackManager;
    GoogleApiClient googleApiClient;
    static final int Req_Code = 9001;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        init();
        loginwithFacebook();
        goToRegister();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutwithGmail();
            }
        });
    }
    String google_name, google_email, facebook_name, facebook_email, firebase_name, firebase_email, firebase_id, firebase_pass;
    String textbox_email, textbox_password;
    String email, birthday, picture;


    private void init() {
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        tvGTRegister = (TextView) findViewById(R.id.tvGoToSignUp);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setVisibility(View.GONE);
        btnFbLogin = (LoginButton) findViewById(R.id.btnFBLogin);
        btnGmailLogin = (SignInButton) findViewById(R.id.btnGmailLogin);

        btnLogin.setOnClickListener(this);
        btnGmailLogin.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    private void loginwithFirebase () {
        textbox_email = txtEmail.getText().toString();
        textbox_password = txtPassword.getText().toString();

        if(textbox_email.equals("")){
            Toast.makeText(this, "Email must be filled", Toast.LENGTH_SHORT).show();
            return;
        }else if(textbox_password.equals("")){
            Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Query databaseRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userEmail").equalTo(textbox_email);

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    firebase_name = userSnapshot.child("userName").getValue().toString();
                    firebase_email = userSnapshot.child("userEmail").getValue().toString();
                    firebase_pass = userSnapshot.child("userPassword").getValue().toString();
                    firebase_id = userSnapshot.child("userId").getValue().toString();

                    if (firebase_pass.equals(textbox_password)) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("id", firebase_id);
                        intent.putExtra("name", firebase_name);
                        intent.putExtra("email", firebase_email);
                        Toast.makeText(LoginActivity.this, "Welcome, " + firebase_name, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loginwithFacebook() {
        btnFbLogin.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        btnFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                // Application code
                                try {
                                    JSONObject data = response.getJSONObject();
                                    facebook_name = object.getString("name").toString();
                                    facebook_email = object.getString("email").toString();
                                    birthday = object.getString("birthday"); // 01/31/1980 format
                                    //picture = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(LoginActivity.this, facebook_name, Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, facebook_email, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(LoginActivity.this, picture.toString(), Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                                intent.putExtra("emailFB", email);
//                                intent.putExtra("nameFB", name);
//                                intent.putExtra("urlFB", picture);
//                                startActivity(intent);

                                //Toast.makeText(LoginActivity.this, picture.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }

    private void loginwithGmail () {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, Req_Code);
    }

    private void logoutwithGmail () {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Req_Code) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private void handleResult (GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String profile_img = account.getPhotoUrl().toString();

            Toast.makeText(this, "Name: " + name + "| Email: " + email, Toast.LENGTH_SHORT).show();
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void updateUI (boolean isLogin) {
        if (isLogin) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogout.setVisibility(View.GONE);
        }

    }

    private void goToRegister () {
        tvGTRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
       if (view == btnLogin)
           loginwithFirebase();
        else if (view == btnGmailLogin)
            loginwithGmail();
    }
}
