package com.example.contactosapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.contactosapp.R;
import com.example.contactosapp.model.Contact;
import com.example.contactosapp.viewmodel.ContactsViewModel;

public class AddContactFragment extends Fragment {
    private ContactsViewModel contactsViewModel;

    public AddContactFragment() {
        super(R.layout.fragment_add_contact);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        EditText etName = view.findViewById(R.id.et_contact_name);
        EditText etNumber = view.findViewById(R.id.et_contact_number);
        Button btnSave = view.findViewById(R.id.btn_save_contact);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String number = etNumber.getText().toString().trim();

            if (!name.isEmpty() && !number.isEmpty()) {
                String defaultImageUri = null; // Puedes cambiar esto por una URL o recurso predeterminado

                Contact newContact = new Contact(name, number, defaultImageUri);
                contactsViewModel.insertContact(newContact);

                // Mensaje de depuración para verificar que se está guardando
                Log.d("AddContactFragment", "Contacto guardado: " + name + ", " + number + ", " + defaultImageUri);

                // Regresar a la lista de contactos después de guardar
                Navigation.findNavController(view).navigateUp();
            }
        });
    }
}
