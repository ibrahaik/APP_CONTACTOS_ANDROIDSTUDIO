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

        recyclerView = view.findViewById(R.id.recyclerViewContacts);
        welcomeMessage = view.findViewById(R.id.tvWelcomeMessage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configura el adapter
        adapter = new ContactsAdapter(contact -> {
            // Implementa la acción cuando se haga click en un contacto
        });
        recyclerView.setAdapter(adapter);

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        // Observa la lista de contactos
        contactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
            adapter.submitList(contacts);
            updateWelcomeMessage(contacts);
        });

        // Configura el SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No se necesita acción aquí
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra los contactos según el texto ingresado
                filterContacts(newText);
                return true;
            }
        });
    }

    // Filtra los contactos según la búsqueda
    private void filterContacts(String query) {
        if (query.isEmpty()) {
            adapter.submitList(contactsViewModel.getAllContacts().getValue());
            welcomeMessage.setVisibility(View.VISIBLE);  // Muestra el mensaje de bienvenida
        } else {
            // Filtra los contactos que coinciden con la consulta
            contactsViewModel.getFilteredContacts(query).observe(getViewLifecycleOwner(), filteredContacts -> {
                adapter.submitList(filteredContacts);
                welcomeMessage.setVisibility(View.GONE);  // Oculta el mensaje de bienvenida
            });
        }
    }

    // Actualiza el mensaje de bienvenida
    private void updateWelcomeMessage(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            welcomeMessage.setVisibility(View.VISIBLE);  // Si no hay contactos, muestra el mensaje de bienvenida
        }
    }
}
