package com.example.contactosapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String numero;
    private String imageUri;

    // Constructor
    public Contact(String nombre, String numero, String imageUri) {
        this.nombre = nombre;
        this.numero = numero;
        this.imageUri = imageUri;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public String getNumero() { return numero; }

    public String getImageUri() { return imageUri; }
}

