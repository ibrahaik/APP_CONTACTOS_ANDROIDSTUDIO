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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Infla la vista
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Encuentra la vista del RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configura el adaptador
        adapter = new ContactsAdapter(contact -> {
            Bundle bundle = new Bundle();
            bundle.putInt("contactId", contact.getId());
            Navigation.findNavController(view).navigate(R.id.action_contacts_to_detailFragment, bundle);
        });
        recyclerView.setAdapter(adapter);

        // Configura el ViewModel
        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);

        // Observa todos los contactos al inicio
        contactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
            // Asegúrate de que se actualice la lista completa de contactos
            adapter.submitList(contacts);
        });

        // Configura el SearchView
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Si el texto está vacío, mostramos todos los contactos
                if (newText.isEmpty()) {
                    contactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
                        adapter.submitList(contacts);
                    });
                } else {
                    // Si hay texto, filtramos los contactos según la búsqueda
                    contactsViewModel.getFilteredContacts(newText).observe(getViewLifecycleOwner(), contacts -> {
                        adapter.submitList(contacts);
                    });
                }
                return true;
            }
        });

        // Restablecer el SearchView al regresar al fragmento
        searchView.setQuery("", false);
        searchView.setIconified(false);

        // Agregar swipe para eliminar
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Contact contact = adapter.getCurrentList().get(position);

                // Eliminar el contacto
                contactsViewModel.deleteContact(contact);

                // Mostrar Snackbar para deshacer la eliminación
                Snackbar.make(recyclerView, "Contacto eliminado", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", v -> contactsViewModel.insertContact(contact))
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        // Configurar el listener para el botón flotante
        view.findViewById(R.id.fab_add_contact).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_contacts_to_addContact);
        });
    }
}
