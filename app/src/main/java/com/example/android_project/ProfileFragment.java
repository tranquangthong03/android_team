package com.example.android_project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {

    public ProfileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // nút địa chỉ trong fragment_profile.xml
        View btnAddress = view.findViewById(R.id.btn_addresses);

        btnAddress.setOnClickListener(v -> {
            FragmentTransaction transaction =
                    requireActivity().getSupportFragmentManager().beginTransaction();

            // thay đúng ID container trong activity_main.xml
            transaction.replace(R.id.fragment_container, new AddressFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
