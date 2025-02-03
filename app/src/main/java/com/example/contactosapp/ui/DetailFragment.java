package com.example.contactosapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.contactosapp.R;
import com.example.contactosapp.model.Contact;
import com.example.contactosapp.viewmodel.ContactsViewModel;

public class DetailFragment extends Fragment {
    private TextView textViewName, textViewNumber;
    private ImageView profileImage;
    private ContactsViewModel contactsViewModel;
    private Contact currentContact;

    // Lanzador para seleccionar imagen
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        profileImage.setImageURI(imageUri);

                        // Guardar la URI en la base de datos
                        if (currentContact != null) {
                            currentContact.setImageUri(imageUri.toString());
                            contactsViewModel.updateContact(currentContact);
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // **1. Configurar la Toolbar**
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);  // Habilitar el botón de retroceso

        textViewName = view.findViewById(R.id.tv_contact_name);
        textViewNumber = view.findViewById(R.id.tv_contact_number);
        profileImage = view.findViewById(R.id.iv_profile_image);

        int contactId = getArguments().getInt("contactId");

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.getContactById(contactId).observe(getViewLifecycleOwner(), contact -> {
            if (contact != null) {
                currentContact = contact;
                textViewName.setText(contact.getNombre());
                textViewNumber.setText(contact.getNumero());

                if (contact.getImageUri() != null && !contact.getImageUri().isEmpty()) {
                    profileImage.setImageURI(Uri.parse(contact.getImageUri()));
                }
            }
        });

        profileImage.setOnClickListener(v -> checkPermissionsAndOpenGallery());
    }

    // **2. Pedir permisos antes de abrir la galería**
    private void checkPermissionsAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    // **3. Lanzador para pedir permisos en Android 6.0+**
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openGallery();
                } else {
                    Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });

    // **4. Abrir la galería**
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    // **5. Manejar el botón de retroceso en la Toolbar**
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).navigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
