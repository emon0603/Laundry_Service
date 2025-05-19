package com.emon.qwash.LoginRegister;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import com.emon.qwash.Api.ApiClient;
import com.emon.qwash.Api.ApiService;
import com.emon.qwash.MainActivity;
import com.emon.qwash.ModelClass.LoginResponse;
import com.emon.qwash.R;
import com.emon.qwash.util.SetGradientText;
import com.emon.qwash.util.WhiteStatusBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    MaterialButton btnGoogle;
    TextInputEditText edemail, edpass;
    TextView forgetbt,signupText;
    TextInputLayout edpasslay;
    private SharedPreferences.Editor editor;
   /* CredentialManager credentialManager;
    GetCredentialRequest getCredentialRequest;
    FirebaseAuth firebaseAuth;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        init();
        //FireBaseMethod();
        PasswordLayout();
        SetGradientText.applyGradient(forgetbt);
        SetGradientText.applyGradient(signupText);
        setupClearFocusListeners();
        ButtonClick();

        WindowCompat.getInsetsController(getWindow(),getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        WhiteStatusBar.applyWhiteStatusBar(this); // Apply it globally



    }

    private void init() {
        btnLogin = findViewById(R.id.btnLogin);
        edemail = findViewById(R.id.edemail);
        edpass = findViewById(R.id.edpass);
        edpasslay = findViewById(R.id.passwordLayout);
        forgetbt = findViewById(R.id.forgotPassword);
        signupText = findViewById(R.id.signupText);
        btnGoogle = findViewById(R.id.btnGoogle);

        //firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // 🔥 Add this line


    }

    private void ButtonClick() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (TextUtils.isEmpty(edemail.getText().toString())) {
                    edemail.setError("Input Your Email");
                    isValid = false;
                } else {
                    edemail.setError(null);
                }

                if (TextUtils.isEmpty(edpass.getText().toString().trim())) {
                    edpasslay.setEndIconVisible(false);
                    edpass.setError("Input Your Password");
                    isValid = false;
                } else {
                    edpass.setError(null);
                    edpasslay.setEndIconVisible(true);
                }

                if (isValid) {
                    // Call login request
                    loginUser();



                }

            }
        });


       /* btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credentialManager.getCredentialAsync(LoginActivity.this, getCredentialRequest, null, Runnable::run,
                        new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                            @Override
                            public void onResult(GetCredentialResponse getCredentialResponse) {
                                Credential credential = getCredentialResponse.getCredential();

                                try {
                                    GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
                                    String tokenID = googleIdTokenCredential.getIdToken();

                                    if (tokenID == null) {
                                        Toast.makeText(LoginActivity.this, "ID Token not found", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);
                                    firebaseAuth.signInWithCredential(authCredential)
                                            .addOnSuccessListener(authResult -> {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(LoginActivity.this, "Firebase Login Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                Log.e("FirebaseLogin", "Error: ", e);
                                            });

                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "Credential parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e("CredentialParsing", "Error parsing credential", e);
                                }
                            }

                            @Override
                            public void onError(@NonNull GetCredentialException e) {
                                if (e instanceof GetCredentialCancellationException) {
                                    Toast.makeText(LoginActivity.this, "Login cancelled", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e("GoogleLogin", "Error during login", e);
                                }
                            }
                        });
            }
        });
*/
    }

    private void loginUser() {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging in... Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody email = RequestBody.create(MultipartBody.FORM, edemail.getText().toString());
        RequestBody password = RequestBody.create(MultipartBody.FORM, edpass.getText().toString());

        String secretKey = "your_secret_key_123";

        Call<LoginResponse> call = apiService.loginUser(secretKey, email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();

                    Log.d("LOGIN_SUCCESS", "Status: " + status + ", Message: " + message);
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                    if ("success".equalsIgnoreCase(status)) {
                        // Save session or user info as needed
                        editor.putString("email", response.body().getUser().getEmail());
                        editor.apply();

                        // Go to Home or Dashboard or next step
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    Log.e("LOGIN_FAILED", "Response Error: " + response.code());
                    Toast.makeText(LoginActivity.this, "Login failed! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("LOGIN_ERROR", t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }/*

    private void RequestGoogleLogin(){

        credentialManager.getCredentialAsync(this, getCredentialRequest, null, Runnable::run, new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
            @Override
            public void onResult(GetCredentialResponse getCredentialResponse) {

                Credential credential = getCredentialResponse.getCredential();
                GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());

                String idToken = googleIdTokenCredential.getIdToken();


              *//*  ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Logging in... Please wait");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();*//*
                Toast.makeText(LoginActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, idToken, Toast.LENGTH_SHORT).show();

                AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);

                firebaseAuth.signInWithCredential(authCredential)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                //progressDialog.dismiss();
                                // Go to Home or Dashboard or next step
                                startActivity(new Intent(LoginActivity.this, EducationInfo.class));
                                finish();

                            }
                        });


            }

            @Override
            public void onError(@NonNull GetCredentialException e) {

                Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("GoogleLogin", "Error during login", e);

            }
        });


    }

    private void FireBaseMethod(){

        firebaseAuth = FirebaseAuth.getInstance();

        credentialManager = CredentialManager.create(this);


        GetSignInWithGoogleOption googleoption = new GetSignInWithGoogleOption.Builder(getString(R.string.Google_Login_Client_ID))
                .setNonce(java.util.UUID.randomUUID().toString())
                .build();

        getCredentialRequest = new GetCredentialRequest.Builder()
                .addCredentialOption(googleoption)
                .build();


    }*/



    private void PasswordLayout() {

        edpass.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!TextUtils.isEmpty(charSequence)) {
                    edpass.setError(null);
                    edpasslay.setEndIconVisible(true);
                } else {
                    edpasslay.setEndIconVisible(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence)) {
                    edpass.setError(null);
                    edpasslay.setEndIconVisible(true);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });


    }

    private void setupClearFocusListeners() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        @SuppressLint("ClickableViewAccessibility") View.OnTouchListener clearFocusTouchListener = (v, event) -> {

            edemail.clearFocus();
            edpass.clearFocus();

            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            return false;
        };

        RelativeLayout main = findViewById(R.id.main);
        ConstraintLayout main2 = findViewById(R.id.main2);


        if (main != null) {
            main.setOnTouchListener(clearFocusTouchListener);
        }
        if (main2 != null) {
            main2.setOnTouchListener(clearFocusTouchListener);
        }


    }


    public void GotoRegisterClass(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }








}