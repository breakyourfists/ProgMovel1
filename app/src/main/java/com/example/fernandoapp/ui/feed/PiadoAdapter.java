package com.example.fernandoapp.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.model.Piado;

import java.util.List;

public class PiadoAdapter extends RecyclerView.Adapter<PiadoAdapter.PiadoViewHolder> {
    private List<Piado> piados;

    public PiadoAdapter(List<Piado> piados) {
        this.piados = piados;
    }

    public void addPiado(Piado piado) {
        piados.add(0, piado);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return piados.size();
    }

    @Override
    public PiadoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.piado, viewGroup, false);
        return new PiadoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PiadoViewHolder viewHolder, int i) {
        Piado piado = piados.get(i);
        viewHolder.autor.setText(piado.getAutorNome());
        viewHolder.mensagem.setText(piado.getMensagem());
    }

    public static class PiadoViewHolder extends RecyclerView.ViewHolder {
        public TextView autor;
        public TextView mensagem;

        public PiadoViewHolder(View v) {
            super(v);
            autor = v.findViewById(R.id.autor);
            mensagem = v.findViewById(R.id.mensagem);
        }
    }
}
