package com.example.contactosapp.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.contactosapp.data.ContactDao;
import com.example.contactosapp.data.DataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private final ContactDao contactDao;
    private final LiveData<List<Contact>> allContacts;
    private final ExecutorService executorService;

    public ContactRepository(Application application) {
        DataBase db = DataBase.getDatabase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) {
        executorService.execute(() -> contactDao.insertContact(contact));
    }
}
