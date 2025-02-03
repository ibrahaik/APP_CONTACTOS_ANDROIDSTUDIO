package com.example.contactosapp.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.contactosapp.model.Contact;

@Database(entities = {Contact.class}, version = 2, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;

    public abstract ContactDao contactDao();

    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "contact_database")
                    .fallbackToDestructiveMigration() // Maneja cambios en la versión
                    .build();
        }
        return instance;
    }
}
