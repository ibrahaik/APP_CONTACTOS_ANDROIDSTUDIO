package com.example.contactosapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contactosapp.R;
import com.example.contactosapp.model.Contact;
import com.example.contactosapp.viewmodel.ContactsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ContactsFragment extends Fragment {

    private ContactsViewModel contactsViewModel;
    private ContactsAdapter adapter;
    private SearchView searchView;
    private List<Contact> currentContacts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ContactsAdapter(contact -> {
            Bundle bundle = new Bundle();
            bundle.putInt("contactId", contact.getId());
            Navigation.findNavController(view).navigate(R.id.action_contacts_to_detailFragment, bundle);
        });
        recyclerView.setAdapter(adapter);

        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);

        contactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
            currentContacts = contacts;
            adapter.submitList(contacts);
        });

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.submitList(currentContacts);
                } else {
                    contactsViewModel.getFilteredContacts(newText).observe(getViewLifecycleOwner(), filteredContacts -> {
                        adapter.submitList(filteredContacts);
                    });
                }
                return true;
            }
        });

        searchView.setQuery("", false);
        searchView.setIconified(false);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Contact contact = currentContacts.get(position);

                contactsViewModel.deleteContact(contact);

                Snackbar.make(recyclerView, "Contacto eliminado", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", v -> contactsViewModel.insertContact(contact))
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                contactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
                                    currentContacts = contacts;
                                    adapter.submitList(contacts);
                                });
                            }
                        })
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        view.findViewById(R.id.fab_add_contact).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_contacts_to_addContact);
        });
    }
}