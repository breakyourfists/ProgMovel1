package com.example.fernandoapp.ui.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Usuario usuario = i.getParcelableExtra("usuario");

        Toast.makeText(this, "Bem vindo "+usuario.getNome(), Toast.LENGTH_SHORT).show();

    }
}
