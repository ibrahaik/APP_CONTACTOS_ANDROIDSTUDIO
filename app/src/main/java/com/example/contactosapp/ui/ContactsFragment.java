package com.example.contactosapp.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contactosapp.R;

import java.util.ArrayList;
import java.util.List;

import com.example.contactosapp.databinding.FragmentContactsBinding;

public class ContactsFragment extends Fragment {

    private FragmentContactsBinding binding;
    private ContactViewModel contactViewModel;
    private ContactsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new ContactsAdapter(new ArrayList<>());
        binding.recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewContacts.setAdapter(adapter);

        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
            adapter.updateContacts(contacts); // Asegúrate de tener un método para actualizar la lista en el adaptador
        });

        binding.fabAddContact.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_contacts_to_addContact);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



