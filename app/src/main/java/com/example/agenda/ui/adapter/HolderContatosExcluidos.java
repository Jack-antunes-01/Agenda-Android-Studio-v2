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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;

import java.util.List;

public class HolderContatosExcluidos extends RecyclerView.ViewHolder {
    public LinearLayout linhaContato;
    public TextView nomeRecuperarContato;
    public ImageView imagemRecuperarContato;

    public HolderContatosExcluidos(View itemView){
        super(itemView);
        linhaContato = itemView.findViewById(R.id.linhaContato);
        nomeRecuperarContato = itemView.findViewById(R.id.nomeRecuperarContato);
        imagemRecuperarContato = itemView.findViewById(R.id.imagemRecuperarContato);
    }
}

