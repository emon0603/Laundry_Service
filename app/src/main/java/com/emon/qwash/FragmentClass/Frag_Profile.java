package com.emon.qwash.FragmentClass;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emon.qwash.Adapter.Address_Adapter;
import com.emon.qwash.Api.ApiClient;
import com.emon.qwash.Api.ApiService;
import com.emon.qwash.LoginRegister.SignUpActivity;
import com.emon.qwash.MainActivity;
import com.emon.qwash.ModelClass.AddressItem;
import com.emon.qwash.ModelClass.ApiResponse;
import com.emon.qwash.ModelClass.ProfileResponse;
import com.emon.qwash.ModelClass.RegisterResponse;
import com.emon.qwash.ModelClass.Response.UserAddressResponse;
import com.emon.qwash.R;
import com.emon.qwash.Room.AppDatabase;
import com.emon.qwash.Room.EntityClass.ProfileEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_Profile extends Fragment {

    Button btnLogout;
    RecyclerView address_recyclerView, save_card_recyclerView;
    TextView nametv;
    ImageView btn_Add_addrss;
    CircleImageView profile_image;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    AppDatabase db;
    ExecutorService executor;
    String email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Profileview = inflater.inflate(R.layout.fragment_frag__profile, container, false);


        address_recyclerView = Profileview.findViewById(R.id.recyclerView);
        save_card_recyclerView = Profileview.findViewById(R.id.save_card_recyclerView);
        btnLogout = Profileview.findViewById(R.id.btnLogout);
        nametv = Profileview.findViewById(R.id.nametv);
        btn_Add_addrss = Profileview.findViewById(R.id.btn_Add_addrss);
        profile_image = Profileview.findViewById(R.id.profile_image);


        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor= prefs.edit();
        email=prefs.getString("email", "");

        db = AppDatabase.getInstance(getContext());
        executor = Executors.newSingleThreadExecutor();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("email", "");
                editor.apply();
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });

        btn_Add_addrss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddAddressDialog(email);
            }
        });

        syncDataData(email);
        Fetch_Address();


        return Profileview;

    }


    private void syncDataData(String email) {
        executor.execute(() -> {
            ProfileEntity localData = db.profileDao().getProfileByEmail(email);

            requireActivity().runOnUiThread(() -> {
                if (localData != null) {
                    nametv.setText(localData.name);
                    Glide.with(getContext())
                            .load(localData.imageUrl)
                            .placeholder(R.drawable.ic_profile)
                            .into(profile_image);
                    //stopShimmer();
                }
            });

            if (localData == null) {
                fetchFromServerAndSave(email);
            } else {
                long localLastUpdate = prefs.getLong("profile_last_update", 0);
                checkServerAndSync(email, localData, localLastUpdate);
            }
        });
    }

    private void fetchFromServerAndSave(String email) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProfileResponse> call = apiService.getProfile(email);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    ProfileResponse.Data serverData = response.body().getData();

                    ProfileEntity newEntity = new ProfileEntity(
                            email,
                            serverData.getName(),
                            serverData.getNumber(),
                            serverData.getImage(),
                            serverData.getLastUpdated()
                    );

                    executor.execute(() -> db.profileDao().insertProfile(newEntity));
                    prefs.edit().putLong("profile_last_update", serverData.getLastUpdated()).apply();

                    requireActivity().runOnUiThread(() -> {
                        nametv.setText(newEntity.name);
                        Glide.with(getContext())
                                .load(newEntity.imageUrl)
                                .placeholder(R.drawable.ic_profile)
                                .into(profile_image);
                       // stopShimmer();
                    });
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if (!isAdded()) return;
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void checkServerAndSync(String email, ProfileEntity localData, long localLastUpdate) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProfileResponse> call = apiService.getProfile(email);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    ProfileResponse.Data serverData = response.body().getData();
                    long serverLastUpdate = serverData.getLastUpdated();

                    if (serverLastUpdate > localLastUpdate) {
                        localData.name = serverData.getName();
                        localData.number = serverData.getNumber();
                        localData.imageUrl = serverData.getImage();
                        localData.lastUpdated = serverLastUpdate;

                        executor.execute(() -> db.profileDao().updateProfile(localData));
                        prefs.edit().putLong("profile_last_update", serverLastUpdate).apply();

                        requireActivity().runOnUiThread(() -> {
                            nametv.setText(localData.name);
                            Glide.with(getContext())
                                    .load(localData.imageUrl)
                                    .placeholder(R.drawable.ic_profile)
                                    .into(profile_image);
                           // stopShimmer();
                        });
                    } else if (serverLastUpdate < localLastUpdate) {
                        uploadToServer(localData);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if (!isAdded()) return;
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Sync Error: " + t.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void uploadToServer(ProfileEntity data) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.updateProfile(data.email, data.name, data.number);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("DataSync", "Uploaded local data to server.");
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                if (!isAdded()) return;
                Log.e("DataSync", "Upload failed: " + t.getMessage());
            }
        });
    }

    private void showAddressBottomSheet(String email) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.bottom_address_dialog, null);
        dialog.setContentView(view);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroupType);
        TextInputEditText editTextAddress = view.findViewById(R.id.editTextAddress);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            String address = editTextAddress.getText().toString().trim();
            String type = "";

            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.radioHome) {
                type = "home";
            } else if (selectedId == R.id.radioOffice) {
                type = "office";
            }

            if (address.isEmpty() || type.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare JSON body
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("type", type);
                jsonObject.put("address", address);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    jsonObject.toString()
            );


        });

        dialog.show();
    }

    private void showAddAddressDialog(String email) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.bottom_address_dialog, null);
        dialog.setContentView(view);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroupType);
        EditText editTextAddress = view.findViewById(R.id.editTextAddress);
        Button buttonAdd = view.findViewById(R.id.buttonSave);

        buttonAdd.setOnClickListener(v -> {
            String address = editTextAddress.getText().toString().trim();


            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                // কিছু select করা হয়নি
                Toast.makeText(getContext(), "Please select Home or Office", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadio = view.findViewById(selectedId);
            String type = selectedRadio.getText().toString().toLowerCase(); // "home" বা "office"

            if (address.isEmpty() || type.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Address_Add(type, address, dialog);


        });

        dialog.show();


    }


    private void Address_Add(String type, String address, BottomSheetDialog dialog) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading... Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        email = prefs.getString("email", "");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ApiResponse> call = apiService.addAddress(
                email,
                type,
                address

        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    progressDialog.dismiss();

                    Log.d("LOGIN_SUCCESS", "Status: " + status + ", Message: " + message);
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Fetch_Address();

                } else {
                    progressDialog.dismiss();
                    Log.e("LOGIN_FAILED", "Response Error: " + response.code()+response.errorBody());
                    Toast.makeText(getContext(), "Login failed! Please try again.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });


    }

    private void Fetch_Address() {



        email = prefs.getString("email", "");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<UserAddressResponse> call = apiService.getUserAddresses(
                email);

        call.enqueue(new Callback<UserAddressResponse>() {
            @Override
            public void onResponse(Call<UserAddressResponse> call, Response<UserAddressResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().status)) {
                    List<AddressItem> addressList = response.body().data;
                    Address_Adapter adapter = new Address_Adapter(addressList, R.drawable.ic_location);
                    address_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    address_recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "No address found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAddressResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }







}