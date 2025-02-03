package com.example.contactosapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactosapp.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);


    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contacts")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    LiveData<Contact> getContactById(int contactId);
}



