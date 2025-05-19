package com.emon.qwash.FragmentClass;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emon.qwash.Adapter.Address_Adapter;
import com.emon.qwash.Api.ApiClient;
import com.emon.qwash.Api.ApiService;
import com.emon.qwash.MainActivity;
import com.emon.qwash.ModelClass.AddressItem;
import com.emon.qwash.ModelClass.ApiResponse;
import com.emon.qwash.ModelClass.ProfileResponse;
import com.emon.qwash.R;
import com.emon.qwash.Room.AppDatabase;
import com.emon.qwash.Room.EntityClass.ProfileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_Profile extends Fragment {

    Button btnLogout;

    TextView nametv;
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
        View Profimeview = inflater.inflate(R.layout.fragment_frag__profile, container, false);


        RecyclerView address_recyclerView = Profimeview.findViewById(R.id.recyclerView);
        RecyclerView save_card_recyclerView = Profimeview.findViewById(R.id.save_card_recyclerView);
        btnLogout = Profimeview.findViewById(R.id.btnLogout);
        nametv = Profimeview.findViewById(R.id.nametv);
        profile_image = Profimeview.findViewById(R.id.profile_image);


        List<String> addressList = Arrays.asList("Home", "Office");
        List<String> paymentList = Arrays.asList("Visa", "Mastercard");

        Address_Adapter addressAdapter = new Address_Adapter(addressList, R.drawable.ic_location);
        Address_Adapter paymentAdapter = new Address_Adapter(paymentList, R.drawable.ic_payment_card);

        address_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        address_recyclerView.setAdapter(addressAdapter);

        save_card_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        save_card_recyclerView.setAdapter(paymentAdapter);

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

        syncDataData(email);


        return Profimeview;

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
}