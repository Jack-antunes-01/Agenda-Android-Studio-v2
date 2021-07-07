package com.example.agenda.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.ui.sqliteDb.contatos.Contatos;
import com.example.agenda.ui.sqliteDb.contatos.ContatosDAO;

import java.util.List;

public class AdapterContatosExcluidos extends RecyclerView.Adapter<HolderContatosExcluidos> {

    Activity context;
    private final List<Contatos> contatosList;

    @Override
    public HolderContatosExcluidos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_contatos_excluidos, parent, false);
        final HolderContatosExcluidos holder = new HolderContatosExcluidos(view);

        holder.linhaContato.setOnClickListener(v -> {
            Contatos c = contatosList.get(holder.getAdapterPosition());
            long id = c.getId();

            ContatosDAO dao = new ContatosDAO(holder.linhaContato.getContext());

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Recuperar contato")
                    .setMessage("Deseja recuperar este contato?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        boolean successo = dao.recuperarContato(id);

                        if(successo){
                            Toast.makeText(holder.linhaContato.getContext(), "Contato recuperado", Toast.LENGTH_SHORT).show();
                            removerAgenda(c);
                        } else {
                            Toast.makeText(holder.linhaContato.getContext(), "Falha ao recuperar o contato", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .create()
                    .show();
        });

        return holder;
    }

    public AdapterContatosExcluidos(List<Contatos> contatos){
        this.contatosList = contatos;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderContatosExcluidos holder, int position) {

        if(contatosList.get(position).getFoto() != null && contatosList.get(position).getFoto().length > 0){
            byte[] foto = contatosList.get(position).getFoto();
            Bitmap btm = BitmapFactory.decodeByteArray(foto , 0, foto.length);

            holder.imagemRecuperarContato.setImageBitmap(btm);
        } else {
            holder.imagemRecuperarContato.setImageResource(R.mipmap.ic_user);
        }
        holder.nomeRecuperarContato.setText(contatosList.get(position).getNome());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return contatosList != null ? contatosList.size() : 0;
    }

    public void inserirUltimo(Contatos contatos) {
        contatosList.add(contatos);
        notifyItemInserted(getItemCount());
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) (ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public void atualizaAgenda(Contatos contatos) {
        contatosList.set(contatosList.indexOf(contatos), contatos);
        notifyItemInserted(contatosList.indexOf(contatosList));
    }

    public void removerAgenda(Contatos agenda) {
        int position = contatosList.indexOf(agenda);
        contatosList.remove(position);
        notifyItemRemoved(position);
    }

}
