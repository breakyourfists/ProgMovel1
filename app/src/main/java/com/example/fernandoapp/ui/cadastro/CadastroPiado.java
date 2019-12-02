package com.example.fernandoapp.ui.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.dao.PiadoDAO;
import com.example.fernandoapp.data.model.Piado;
import com.example.fernandoapp.data.model.Usuario;

public class CadastroPiado extends AppCompatActivity {
    PiadoDAO piadoDAO;
    Usuario usuario;

    EditText editTextPiado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_piado);

        piadoDAO = new PiadoDAO();
        Intent i = getIntent();
        usuario = i.getParcelableExtra("usuario");

        editTextPiado = findViewById(R.id.editTextPiado);


    }

    public void enviarPiado(View view) {
        piadoDAO.addPiado(new Piado(usuario, editTextPiado.getText().toString()));
    }
}
