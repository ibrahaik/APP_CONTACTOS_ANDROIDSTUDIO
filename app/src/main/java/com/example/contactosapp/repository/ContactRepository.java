package com.example.contactosapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.contactosapp.data.DataBase;
import com.example.contactosapp.data.ContactDao;
import com.example.contactosapp.model.Contact;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private final ContactDao contactDao;
    private final LiveData<List<Contact>> allContacts;
    private final ExecutorService executorService;

    public ContactRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        contactDao = database.contactDao();
        allContacts = contactDao.getAllContacts();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<Contact> getContactById(int contactId) {
        return contactDao.getContactById(contactId);
    }

    public void insert(Contact contact) {
        executorService.execute(() -> contactDao.insertContact(contact));
    }

    public void update(Contact contact) {
        executorService.execute(() -> contactDao.update(contact));
    }

    public void delete(Contact contact) {
        executorService.execute(() -> contactDao.delete(contact));
    }
}
