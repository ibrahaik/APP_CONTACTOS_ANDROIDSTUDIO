package com.example.contactosapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.contactosapp.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);
        @Query("SELECT * FROM contact")
        LiveData<List<Contact>> getAllContacts();
    }



