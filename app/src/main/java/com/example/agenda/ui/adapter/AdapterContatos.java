package com.example.agenda.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.HomeDirections;
import com.example.agenda.R;
import com.example.agenda.ui.sqliteDb.contatos.Contatos;

import java.util.List;

public class AdapterContatos extends RecyclerView.Adapter<HolderContatos> {

    private final List<Contatos> contatosList;

    @Override
    public HolderContatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_contatos, parent, false);
        final HolderContatos holder = new HolderContatos(view);

        holder.lineData.setOnClickListener(v -> {
            Contatos contato = contatosList.get(holder.getAdapterPosition());
            long id = contato.getId();

            HomeDirections.ActionNavigationHomeToPerfilContato action = HomeDirections.actionNavigationHomeToPerfilContato(
                    id
            );
            Navigation.findNavController(view).navigate(action);
        });

        return holder;
    }

    public AdapterContatos(List<Contatos> contatos){
        this.contatosList = contatos;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderContatos holder, int position) {

        if(contatosList.get(position).getFoto() != null && contatosList.get(position).getFoto().length > 0){
            byte[] foto = contatosList.get(position).getFoto();
            Bitmap btm = BitmapFactory.decodeByteArray(foto , 0, foto.length);

            holder.list_item_image.setImageBitmap(btm);
        } else {
            holder.list_item_image.setImageResource(R.mipmap.ic_user);
        }

        holder.list_item_text.setText(contatosList.get(position).getNome());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return contatosList != null ? contatosList.size() : 0;
    }
}
