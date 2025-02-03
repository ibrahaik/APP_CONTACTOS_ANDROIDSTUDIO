package com.example.contactosapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactosapp.R;
import com.example.contactosapp.model.Contact;
import com.example.contactosapp.viewmodel.ContactsViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private ContactsViewModel contactsViewModel;
    private RecyclerView recyclerView;
    private TextView welcomeMessage;
    private ContactsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        welcomeMessage = view.findViewById(R.id.tvWelcomeMessage);

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);


    }

}
