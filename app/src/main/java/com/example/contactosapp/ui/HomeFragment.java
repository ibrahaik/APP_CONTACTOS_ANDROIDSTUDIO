package com.example.contactosapp.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.contactosapp.R;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.buttonNavigate).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_detailFragment);
        });

        return view;
    }
}