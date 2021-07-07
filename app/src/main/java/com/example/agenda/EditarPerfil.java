package com.example.agenda;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.agenda.ui.sqliteDb.usuarios.Usuarios;
import com.example.agenda.ui.sqliteDb.usuarios.UsuariosDAO;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditarPerfil extends Fragment {

    private static final int GET_FROM_GALLERY = 1;
    private static final int TAKE_PHOTO = 2;
    byte[] img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).disableBackButton();

        int id = ((MainActivity) getActivity()).id;

        UsuariosDAO userDao = new UsuariosDAO(view.getContext());
        Usuarios dadosUsuario = userDao.buscarDadosUsuarioById(id);

        EditText profileName = view.findViewById(R.id.cadNameEditarProfile);
        ImageView profileImageView = view.findViewById(R.id.fotoPerfilUsuario);
        ImageView profileImageViewFake = view.findViewById(R.id.fotoPerfilUsuarioFake);
        EditText profileBirthDate = view.findViewById(R.id.cadDtNascEditarProfile);
        EditText profilePhone = view.findViewById(R.id.cadPhoneEditarProfile);
        EditText profileWhatsapp = view.findViewById(R.id.cadWhatsappEditarProfile);
        EditText profileTelegram = view.findViewById(R.id.cadTelegramEditarProfile);
        EditText profileFacebook = view.findViewById(R.id.cadFacebookEditarProfile);
        EditText profileTwitter = view.findViewById(R.id.cadTwitterEditarProfile);
        EditText profileInstagram = view.findViewById(R.id.cadInstagramEditarProfile);

        if(dadosUsuario.getNome() != null && dadosUsuario.getNome().length() > 0){
            profileName.setText(dadosUsuario.getNome());
        }

        if (dadosUsuario.getFoto() != null && dadosUsuario.getFoto().length > 0) {
            Bitmap btm = BitmapFactory.decodeByteArray(dadosUsuario.getFoto(), 0, dadosUsuario.getFoto().length);
            profileImageViewFake.setImageBitmap(btm);
        } else {
            profileImageViewFake.setImageResource(R.mipmap.ic_user);
        }


        if(dadosUsuario.getApelido() != null && dadosUsuario.getApelido().length() > 0){
            profileName.setText(dadosUsuario.getApelido());
        }

        if (dadosUsuario.getNascimento() != null && dadosUsuario.getNascimento().length() > 0) {
            profileBirthDate.setText(dadosUsuario.getNascimento());
        }

        if (Long.toString(dadosUsuario.getCelular()).length() > 1) {
            profilePhone.setText(Long.toString(dadosUsuario.getCelular()));
        }

        if (Long.toString(dadosUsuario.getWhatsapp()).length() > 1) {
            profileWhatsapp.setText(Long.toString(dadosUsuario.getWhatsapp()));
        }

        if (Long.toString(dadosUsuario.getTelegram()).length() > 1) {
            profileTelegram.setText(Long.toString(dadosUsuario.getTelegram()));
        }

        if (dadosUsuario.getFacebook() != null && dadosUsuario.getFacebook().length() > 0) {
            profileFacebook.setText(dadosUsuario.getFacebook());
        }

        if (dadosUsuario.getTwitter() != null && dadosUsuario.getTwitter().length() > 0) {
            profileTwitter.setText(dadosUsuario.getTwitter());
        }

        if (dadosUsuario.getInstagram() != null && dadosUsuario.getInstagram().length() > 0) {
            profileInstagram.setText(dadosUsuario.getInstagram());
        }

        view.findViewById(R.id.alterarFotoPerfilUsuario).setOnClickListener(v -> {
            selectImage();
        });

        view.findViewById(R.id.cadBtnRegisterEditarProfile).setOnClickListener(v -> {
            gravar(view, id);
        });

        view.findViewById(R.id.fabBackEditarProfile).setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideBottomTabNavigation();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).hideBottomTabNavigation();
    }

    protected void gravar(View view, int id){
        EditText name = view.findViewById(R.id.cadNameEditarProfile);
        EditText apelido = view.findViewById(R.id.cadNicknameEditarProfile);
        EditText phone = view.findViewById(R.id.cadPhoneEditarProfile);
        EditText whatsapp = view.findViewById(R.id.cadWhatsappEditarProfile);
        EditText telegram = view.findViewById(R.id.cadTelegramEditarProfile);
        EditText nascimento = view.findViewById(R.id.cadDtNascEditarProfile);
        EditText facebook = view.findViewById(R.id.cadFacebookEditarProfile);
        EditText twitter = view.findViewById(R.id.cadTwitterEditarProfile);
        EditText instagram = view.findViewById(R.id.cadInstagramEditarProfile);

        ImageView fotoPerfilUsuario = view.findViewById(R.id.fotoPerfilUsuario);

        String nomeValue = name.getText().toString();
        String apelidoValue = apelido.getText().toString();
        String celularValue = phone.getText().toString();
        String whatsappValue = whatsapp.getText().toString();
        String telegramValue = telegram.getText().toString();
        String nascimentoValue = nascimento.getText().toString();
        String facebookValue = facebook.getText().toString();
        String twitterValue = twitter.getText().toString();
        String instagramValue = instagram.getText().toString();
        byte[] fotoPerfilUsuarioValue;

        if(fotoPerfilUsuario.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable) fotoPerfilUsuario.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            fotoPerfilUsuarioValue = baos.toByteArray();
        } else {
            fotoPerfilUsuarioValue = new byte[0];
        }


        boolean haveMinRequirements = validaMinRequirements(view);

        if(!haveMinRequirements) {
            Snackbar.make(view, "Preencha pelo menos os dados obrigatórios", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }

        UsuariosDAO userDao = new UsuariosDAO(view.getContext());

        // Update
        boolean sucesso = userDao.update(id, nomeValue, apelidoValue,
                celularValue, whatsappValue, telegramValue, nascimentoValue,
                facebookValue, twitterValue, instagramValue, fotoPerfilUsuarioValue);

        if (sucesso) {

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View viewActivity = getActivity().getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (viewActivity == null) {
                viewActivity = new View(getActivity());
            }
            imm.hideSoftInputFromWindow(viewActivity.getWindowToken(), 0);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("")
                    .setMessage("Dados alterados com sucesso!")
                    .setPositiveButton("Confirmar", (dialog, which) -> {
                        Navigation.findNavController(view).popBackStack();
                    })
                    .create()
                    .show();
        } else {
            Snackbar.make(view, "Houve falha na edição dos dados do '" + nomeValue + "'.", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    protected boolean validaMinRequirements(View view){
        EditText name = view.findViewById(R.id.cadNameEditarProfile);
        EditText phone = view.findViewById(R.id.cadPhoneEditarProfile);

        String nome = name.getText().toString();
        String celular = phone.getText().toString();

        if(nome.isEmpty() || celular.isEmpty()) return false;

        return true;
    }

    private void selectImage() {
        final CharSequence[] options = { "Tirar foto", "Abrir galeria","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Alterar foto");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Tirar foto"))
            {
                if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},TAKE_PHOTO);
                } else {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera_intent, TAKE_PHOTO);
                }
            }
            else if (options[item].equals("Abrir galeria"))
            {
                Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
            }
            else if (options[item].equals("Cancelar")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView image = getView().findViewById(R.id.fotoPerfilUsuario);
        ImageView imageFake  = getView().findViewById(R.id.fotoPerfilUsuarioFake);
        Bitmap bitmap = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    img = bos.toByteArray();
                    image.setImageBitmap(bitmap);
                    imageFake.setImageBitmap(bitmap);

                }
                break;
            case GET_FROM_GALLERY:
                if(resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImage);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        img = bos.toByteArray();

                        image.setImageBitmap(bitmap);
                        imageFake.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }
}