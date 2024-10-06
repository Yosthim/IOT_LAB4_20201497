package com.example.iot_lab4_20201497;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(v -> {
            if (isInternetAvailable()) {
                startActivity(new Intent(MainActivity.this, AppActivity.class));
            } else {
                showNoInternetDialog();
            }
        });

    }

    //Funciones para validar la conexión a internet
    //Código hecho con ayuda de Claude
    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo(); //Para esta parte es necesario el permiso de acceso al estado de red
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No hay conexión a internet")
                .setMessage("Por favor revisa tu conexión a internet y vueve a intentar.")
                .setPositiveButton("Configuración", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}