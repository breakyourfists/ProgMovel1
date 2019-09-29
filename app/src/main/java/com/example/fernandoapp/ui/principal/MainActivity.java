package com.example.fernandoapp.ui.principal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.model.Usuario;
import com.example.fernandoapp.ui.login.LoginActivity;
import com.example.fernandoapp.ui.mapa.MapsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Usuario usuario = i.getParcelableExtra("usuario");

        Toast.makeText(this, "Bem vindo "+usuario.getNome(), Toast.LENGTH_SHORT).show();


    }

    public void abrirMapaButton(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
