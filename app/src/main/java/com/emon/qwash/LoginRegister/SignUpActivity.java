package com.emon.qwash.LoginRegister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import com.emon.qwash.Api.ApiClient;
import com.emon.qwash.Api.ApiService;
import com.emon.qwash.MainActivity;
import com.emon.qwash.ModelClass.RegisterResponse;
import com.emon.qwash.R;
import com.emon.qwash.util.SetGradientText;
import com.emon.qwash.util.WhiteStatusBar;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button btnRegister;
    public TextInputEditText edemail, edpass, edname, ednumber;
    private TextInputLayout edpasslay;
    TextView signupText;
    CheckBox checkbox_term;
    RelativeLayout profile_container;
    SharedPreferences.Editor editor;
    private Uri selectedImageUri;
    private File selectedImageFile;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        init();
        //CheckBox();
        PasswordLayout();
        setupClearFocusListeners();
        ButtonClick();
    }

    private void init() {
        btnRegister = findViewById(R.id.btnRegister);
        edemail = findViewById(R.id.edemail);
        edpass = findViewById(R.id.edpass);
        edpasslay = findViewById(R.id.passwordLayout);
        edname = findViewById(R.id.edname);
        ednumber = findViewById(R.id.ednumber);
        checkbox_term = findViewById(R.id.checkbox_term);
        profile_container = findViewById(R.id.profile_container);
        profile_image = findViewById(R.id.profile_image);
        signupText = findViewById(R.id.signupText);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        WindowCompat.getInsetsController(getWindow(),getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        WhiteStatusBar.applyWhiteStatusBar(this); // Apply it globally



        SetGradientText.applyGradient(signupText);

    }

    private void ButtonClick() {
        btnRegister.setEnabled(false);
        checkbox_term.setOnCheckedChangeListener((buttonView, isChecked) -> btnRegister.setEnabled(isChecked));

        btnRegister.setOnClickListener(view -> {
            boolean isValid = true;

            if (TextUtils.isEmpty(edname.getText().toString())) {
                edname.setError("Input Your Name");
                isValid = false;
            } else {
                edname.setError(null);
            }

            if (TextUtils.isEmpty(edemail.getText().toString())) {
                edemail.setError("Input Your Email");
                isValid = false;
            } else {
                edemail.setError(null);
            }

            if (TextUtils.isEmpty(ednumber.getText().toString())) {
                ednumber.setError("Input Your Number");
                isValid = false;
            } else {
                ednumber.setError(null);
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

                if (selectedImageFile == null) {
                    try {
                        selectedImageFile = createFileFromImageView(profile_image);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, "Error processing default image", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                registerUser(selectedImageFile, edname.getText().toString(), edemail.getText().toString(),
                        edpass.getText().toString(), ednumber.getText().toString());
            }
        });

        profile_container.setOnClickListener(view -> ImagePicker.with(SignUpActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    imagePickerLauncher.launch(intent);
                    return null;
                }));
    }

   public void registerUser(File imageFile, String names, String emails, String passwords, String numbers) {
        ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading... Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody name = RequestBody.create(MultipartBody.FORM, names);
        RequestBody email = RequestBody.create(MultipartBody.FORM, emails);
        RequestBody password = RequestBody.create(MultipartBody.FORM, passwords);
        RequestBody number = RequestBody.create(MultipartBody.FORM, numbers);

        RequestBody reqFile = RequestBody.create(MultipartBody.FORM, imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), reqFile);

        String secretKey = "your_secret_key_123";

        Call<RegisterResponse> call = apiService.registerUser(secretKey, name, email, password, number, imagePart);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();

                    Log.d("SIGNUP_SUCCESS", "Status: " + status + ", Message: " + message);
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();

                    if ("success".equalsIgnoreCase(status)) {
                        progressDialog.dismiss();
                        editor.putString("email", edemail.getText().toString());
                        editor.apply();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    }
                } else {
                    Log.e("SIGNUP_FAILED", "Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("SIGNUP_ERROR", t.getMessage(), t);
            }
        });

    }

    ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        selectedImageUri = uri;
                        selectedImageFile = getFileFromUri(uri);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        profile_image.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

    public File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File file = new File(getCacheDir(), "temp_image.jpg");
        OutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        return file;
    }

    private File createFileFromImageView(CircleImageView imageView) throws IOException {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();

        File file = new File(getCacheDir(), "default_profile_image.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();

        imageView.setDrawingCacheEnabled(false);
        return file;
    }

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

    private void CheckBox() {
        String fullText = checkbox_term.getText().toString();
        SpannableString spannableString = new SpannableString(fullText);

        int termsStart = fullText.indexOf("Terms of Service");
        int termsEnd = termsStart + "Terms of Service".length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                openUrl("https://medldoc.com/terms");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int privacyStart = fullText.indexOf("Privacy Policy");
        int privacyEnd = privacyStart + "Privacy Policy".length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                openUrl("https://medldoc.com/privacy");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        checkbox_term.setText(spannableString);
        checkbox_term.setMovementMethod(LinkMovementMethod.getInstance());

        checkbox_term.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                TextView textView = (TextView) v;
                Layout layout = textView.getLayout();

                int x = (int) event.getX() - textView.getTotalPaddingLeft();
                int y = (int) event.getY() - textView.getTotalPaddingTop();

                x = Math.max(0, x);
                y = Math.max(0, y);

                int line = layout.getLineForVertical(y);
                int offset = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = spannableString.getSpans(offset, offset, ClickableSpan.class);
                if (link.length > 0) {
                    link[0].onClick(textView);
                    return true;
                }
            }
            return false;
        });
    }

    private void setupClearFocusListeners() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        @SuppressLint("ClickableViewAccessibility")
        View.OnTouchListener clearFocusTouchListener = (v, event) -> {
            edname.clearFocus();
            edemail.clearFocus();
            ednumber.clearFocus();
            edpass.clearFocus();
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        };

        RelativeLayout main = findViewById(R.id.main);
        if (main != null) {
            main.setOnTouchListener(clearFocusTouchListener);
        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void GotoLogInClass(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}
