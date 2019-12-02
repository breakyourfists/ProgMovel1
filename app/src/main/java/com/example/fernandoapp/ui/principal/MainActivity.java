package com.example.fernandoapp.ui.principal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.model.Usuario;
import com.example.fernandoapp.ui.cadastro.CadastroPiado;
import com.example.fernandoapp.ui.feed.FeedActivity;
import com.example.fernandoapp.ui.mapa.MapsActivity;
import com.google.android.libraries.places.api.Places;

public class MainActivity extends AppCompatActivity {
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        usuario = i.getParcelableExtra("usuario");
        Log.i("usr","no main id é "+usuario.getId());
        Toast.makeText(this, "Bem vindo " + usuario.getNome(), Toast.LENGTH_SHORT).show();

        String apiKey = getString(R.string.google_maps_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

    }

    public void abrirMapaButton(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("usuario",usuario);
        startActivity(intent);
    }

    public void abrirFeedButton(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }

    public void cadastrarPiadoButton(View view) {
        Intent intent = new Intent(this, CadastroPiado.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }

    public void abrirTelaSeguirUsuarioButton(View view) {
        Intent intent = new Intent(this, Seguir.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }


    private void setLaunchActivityClickListener(
            int onClickResId, Class<? extends AppCompatActivity> activityClassToLaunch) {
        findViewById(onClickResId)
                .setOnClickListener(
                        v -> startActivity(new Intent(MainActivity.this, activityClassToLaunch)));
    }


}
