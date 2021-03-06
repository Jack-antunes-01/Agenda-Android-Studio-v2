package com.example.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.agenda.R;
import com.example.agenda.ui.sqliteDb.usuarios.Usuarios;
import com.example.agenda.ui.sqliteDb.usuarios.UsuariosDAO;

public class PerfilUsuario extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int id = ((MainActivity)getActivity()).id;

        UsuariosDAO userDao = new UsuariosDAO(view.getContext());
        Usuarios dadosUsuario = userDao.buscarDadosUsuarioById(id);

        TextView profileName = view.findViewById(R.id.profileName);
        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        TextView profileBirthDate = view.findViewById(R.id.profileBirthDate);
        TextView profileEmail = view.findViewById(R.id.profileEmail);
        TextView profilePhone = view.findViewById(R.id.profilePhone);
        TextView profileWhatsapp = view.findViewById(R.id.profileWhatsapp);
        TextView profileTelegram = view.findViewById(R.id.profileTelegram);
        TextView profileFacebook = view.findViewById(R.id.profileFacebook);
        TextView profileTwitter = view.findViewById(R.id.profileTwitter);
        TextView profileInstagram = view.findViewById(R.id.profileInstagram);

        if(dadosUsuario.getApelido() != null && dadosUsuario.getApelido().length() > 0){
            profileName.setText(dadosUsuario.getNome() + " (" + dadosUsuario.getApelido() + ")");
        } else {
            profileName.setText(dadosUsuario.getNome());
        }

        if (dadosUsuario.getFoto() != null && dadosUsuario.getFoto().length > 0) {
            Bitmap btm = BitmapFactory.decodeByteArray(dadosUsuario.getFoto(), 0, dadosUsuario.getFoto().length);
            profileImageView.setImageBitmap(btm);
        } else {
            profileImageView.setImageResource(R.mipmap.ic_user);
        }

        if (dadosUsuario.getNascimento() != null && dadosUsuario.getNascimento().length() > 0) {
            profileBirthDate.setText(dadosUsuario.getNascimento());
        } else {
            profileBirthDate.setText("N??o cadastrado");
        }

        if (dadosUsuario.getEmail() != null && dadosUsuario.getEmail().length() > 0) {
            profileEmail.setText(dadosUsuario.getEmail());
        } else {
            profileEmail.setText("N??o cadastrado");
        }

        if (Long.toString(dadosUsuario.getCelular()).length() > 1) {
            profilePhone.setText(Long.toString(dadosUsuario.getCelular()));
        } else {
            profilePhone.setText("N??o cadastrado");
        }

        if (Long.toString(dadosUsuario.getWhatsapp()).length() > 1) {
            profileWhatsapp.setText(Long.toString(dadosUsuario.getWhatsapp()));
        } else {
            profileWhatsapp.setText("N??o cadastrado");
        }

        if (Long.toString(dadosUsuario.getTelegram()).length() > 1) {
            profileTelegram.setText(Long.toString(dadosUsuario.getTelegram()));
        } else {
            profileTelegram.setText("N??o cadastrado");
        }

        if (dadosUsuario.getFacebook() != null && dadosUsuario.getFacebook().length() > 0) {
            profileFacebook.setText(dadosUsuario.getFacebook());
        } else {
            profileFacebook.setText("N??o cadastrado");
        }

        if (dadosUsuario.getTwitter() != null && dadosUsuario.getTwitter().length() > 0) {
            profileTwitter.setText(dadosUsuario.getTwitter());
        } else {
            profileTwitter.setText("N??o cadastrado");
        }

        if (dadosUsuario.getInstagram() != null && dadosUsuario.getInstagram().length() > 0) {
            profileInstagram.setText(dadosUsuario.getInstagram());
        } else {
            profileInstagram.setText("N??o cadastrado");
        }

        view.findViewById(R.id.fabEditarPerfil).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(PerfilUsuarioDirections.actionNavigationProfileToEditarPerfil());
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showBottomTabNavigation();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).showBottomTabNavigation();
    }


}