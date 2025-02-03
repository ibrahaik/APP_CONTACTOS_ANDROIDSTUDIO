package com.example.contactosapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> adapter.submitList(contacts));

        // 🚀 Agregar swipe para eliminar
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No permite reordenar
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Contact contact = adapter.getCurrentList().get(position);

                // Eliminar contacto
                contactsViewModel.deleteContact(contact);

                // Mostrar Snackbar para deshacer la eliminación
                Snackbar.make(recyclerView, "Contacto eliminado", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", v -> contactsViewModel.insertContact(contact))
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        // 🚀 Agregar el listener para el botón flotante
        view.findViewById(R.id.fab_add_contact).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_contacts_to_addContact);
        });
    }
}
