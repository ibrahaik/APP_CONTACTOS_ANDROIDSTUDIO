package com.example.contactosapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private static final String PREFIX = "+34";

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

        etNumber.setText(PREFIX);
        etNumber.setSelection(etNumber.getText().length());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String number = etNumber.getText().toString().trim();

            if (validateInput(name, number)) {
                String defaultImageUri = null;
                Contact newContact = new Contact(name, number, defaultImageUri);
                contactsViewModel.insertContact(newContact);
                Log.d("AddContactFragment", "Contacto guardado: " + name + ", " + number);
                Navigation.findNavController(view).navigateUp();
            }
        });
    }

    private boolean validateInput(String name, String number) {
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        String numberWithoutPrefix = number.replace(PREFIX, "");
        if (numberWithoutPrefix.length() < 9 || numberWithoutPrefix.length() > 9) {
            Toast.makeText(getContext(), "El número debe tener  9 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
