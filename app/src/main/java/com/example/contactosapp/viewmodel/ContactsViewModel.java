package com.example.contactosapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.contactosapp.model.Contact;
import com.example.contactosapp.repository.ContactRepository;
import java.util.List;

public class ContactsViewModel extends AndroidViewModel {
    private final ContactRepository repository;
    private final LiveData<List<Contact>> allContacts;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<Contact> getContactById(int contactId) {
        return repository.getContactById(contactId);
    }

    public void insertContact(Contact contact) {
        repository.insert(contact);
    }

    public void updateContact(Contact contact) {
        repository.update(contact);
    }

    public void deleteContact(Contact contact) {
        repository.delete(contact);
    }
}
