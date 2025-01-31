package com.example.contactosapp.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactosapp.model.Contact;
import com.example.contactosapp.model.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private final ContactRepository repository;
    private final LiveData<List<Contact>> allContacts;

    public ContactViewModel(Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    // Método para obtener todos los contactos
    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    // Método para insertar un contacto
    public void insert(Contact contact) {
        repository.insert(contact);
    }
}
