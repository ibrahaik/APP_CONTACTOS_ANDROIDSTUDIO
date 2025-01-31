package com.example.contactosapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.contactosapp.R;
import com.example.contactosapp.model.Contact;

public class AddContactFragment extends Fragment {
    private EditText etContactName;
    private EditText etContactNumber;
    private Button btnSaveContact;
    private ContactViewModel contactViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        etContactName = view.findViewById(R.id.et_contact_name);
        etContactNumber = view.findViewById(R.id.et_contact_number);
        btnSaveContact = view.findViewById(R.id.btn_save_contact);

        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);

        btnSaveContact.setOnClickListener(v -> {
            String name = etContactName.getText().toString().trim();
            String number = etContactNumber.getText().toString().trim();

            Log.d("ContactApp", "Nuevo contacto: " + name + " Número: " + number );

            if (!name.isEmpty() && !number.isEmpty()) {
                Contact newContact = new Contact(name, number);
                contactViewModel.insert(newContact);

                // Volver a la pantalla principal
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
