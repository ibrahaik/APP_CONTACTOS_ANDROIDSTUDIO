package com.example.contactosapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Toolbar como ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtener BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Obtener NavHostFragment de forma segura
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Configurar BottomNavigationView con NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // Agregar listener para depuración
            bottomNavigationView.setOnItemSelectedListener(item -> {
                Log.d("BottomNav", "Selected item ID: " + item.getItemId());
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                Log.d("BottomNav", "Navigation handled: " + handled);
                return handled;
            });

        } else {
            Toast.makeText(this, "Error: No se encontró NavHostFragment", Toast.LENGTH_SHORT).show();
        }
    }
}
