package com.example.fernandoapp.ui.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.dao.UsuarioDAO;
import com.example.fernandoapp.data.model.Usuario;

import java.util.ArrayList;

public class Seguir extends AppCompatActivity {
    private Spinner spinnerUsuarios;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguir);
        usuarioDAO = new UsuarioDAO(getApplicationContext());
        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra("usuario");
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios();
        int removerID = -1;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuario.getId() == usuarios.get(i).getId())
                removerID = i;
        }
        usuarios.remove(removerID);
        ArrayAdapter<Usuario> dataAdapter = new ArrayAdapter<Usuario>(this, android.R.layout.simple_spinner_item, usuarios);

        spinnerUsuarios.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void seguirButtonAction(View view) {
        usuarioDAO.inserirSeguida(usuario.getId(), ((Usuario) spinnerUsuarios.getSelectedItem()).getId());
    }
}
