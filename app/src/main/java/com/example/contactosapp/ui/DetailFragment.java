package com.example.contactosapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.contactosapp.R;
import com.example.contactosapp.model.Contact;
import com.example.contactosapp.viewmodel.ContactsViewModel;

public class DetailFragment extends Fragment {
    private TextView textViewName, textViewNumber;
    private ImageView profileImage;
    private ImageButton btnCall, btnMessage;
    private ContactsViewModel contactsViewModel;
    private Contact currentContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        textViewName = view.findViewById(R.id.tv_contact_name);
        textViewNumber = view.findViewById(R.id.tv_contact_number);
        profileImage = view.findViewById(R.id.iv_profile_image);
        btnCall = view.findViewById(R.id.btn_call);
        btnMessage = view.findViewById(R.id.btn_message);

        int contactId = getArguments().getInt("contactId");

        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.getContactById(contactId).observe(getViewLifecycleOwner(), contact -> {
            if (contact != null) {
                currentContact = contact;
                textViewName.setText(contact.getNombre());
                textViewNumber.setText(contact.getNumero());

                if (contact.getImageUri() != null && !contact.getImageUri().isEmpty()) {
                    Glide.with(this).load(contact.getImageUri()).into(profileImage);
                }
            }
        });

        btnCall.setOnClickListener(v -> makeCall());

        btnMessage.setOnClickListener(v -> sendMessage());
    }

    private void makeCall() {
        if (currentContact != null && currentContact.getNumero() != null) {
            Uri callUri = Uri.parse("tel:" + currentContact.getNumero());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, callUri); // Abre la app de llamadas
            startActivity(callIntent);
        }
    }

    private void sendMessage() {
        if (currentContact != null && currentContact.getNumero() != null) {
            Uri smsUri = Uri.parse("smsto:" + currentContact.getNumero());
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
            smsIntent.putExtra("sms_body", "¡Hola " + currentContact.getNombre() + "!"); // Mensaje predeterminado
            startActivity(smsIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).navigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
