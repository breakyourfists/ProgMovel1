package com.example.fernandoapp.ui.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.dao.PiadoDAO;
import com.example.fernandoapp.data.model.Usuario;

public class FeedActivity extends AppCompatActivity {
    PiadoDAO piadoDAO;
    Usuario usuario;
    private RecyclerView.LayoutManager lManager;
    private PiadoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        piadoDAO = new PiadoDAO();
        Intent i = getIntent();
        usuario = i.getParcelableExtra("usuario");

        // Get the RecyclerView
        RecyclerView recycler = findViewById(R.id.rv_id);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        PiadoAdapter adapter = new PiadoAdapter(piadoDAO.getPiadosPorUsuario(usuario.getId()));

        recycler.setAdapter(adapter);

    }
}
