package com.example.agenda;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agenda.ui.sqliteDb.contatos.Contatos;
import com.example.agenda.ui.sqliteDb.contatos.ContatosDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CadastroContato extends Fragment {

    private static final int TAKE_PHOTO = 1;
    private static final int GET_FROM_GALLERY = 2;
    CadastroContatoArgs args;
    byte[] img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro_contato, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity) getActivity()).disableBackButton();

        if (getArguments() != null) {
            args = CadastroContatoArgs.fromBundle(getArguments());
            String id = args.getId();
            long ra = ((MainActivity)getActivity()).ra;

            if (id != "" && id != null) {
                ((MainActivity) getActivity()).setActionBarTitle("Editar contato");

                ContatosDAO contactsDao = new ContatosDAO(view.getContext());
                Contatos dadosContato = contactsDao.buscarDadosContatoById(Long.parseLong(id));

                TextView cadName = view.findViewById(R.id.cadName);
                TextView cadNickname = view.findViewById(R.id.cadNickname);
                ImageView profileImageView = view.findViewById(R.id.fotoPerfilContato);
                ImageView profileImageViewFake = view.findViewById(R.id.fotoPerfilContatoFake);
                TextView cadDtNasc = view.findViewById(R.id.cadDtNasc);
                TextView cadEmail = view.findViewById(R.id.cadEmail);
                TextView cadPhone = view.findViewById(R.id.cadPhone);
                TextView cadWhatsapp = view.findViewById(R.id.cadWhatsapp);
                TextView cadTelegram = view.findViewById(R.id.cadTelegram);
                TextView cadFacebook = view.findViewById(R.id.cadFacebook);
                TextView cadTwitter = view.findViewById(R.id.cadTwitter);
                TextView cadInstagram = view.findViewById(R.id.cadInstagram);

                Button cadButton = view.findViewById(R.id.cadBtnRegister);
                cadButton.setText("SALVAR");

                if (dadosContato.getNome() != null && dadosContato.getNome().length() > 0) {
                    cadName.setText(dadosContato.getNome());
                }

                if (dadosContato.getFoto() != null && dadosContato.getFoto().length > 0) {
                    Bitmap btm = BitmapFactory.decodeByteArray(dadosContato.getFoto(), 0, dadosContato.getFoto().length);

                    profileImageViewFake.setImageBitmap(btm);
                } else {
                    profileImageViewFake.setImageResource(R.mipmap.ic_user);
                }

                if (dadosContato.getApelido() != null && dadosContato.getApelido().length() > 0) {
                    cadNickname.setText(dadosContato.getApelido());
                }

                if (dadosContato.getNascimento() != null && dadosContato.getNascimento().length() > 0) {
                    cadDtNasc.setText(dadosContato.getNascimento());
                }

                if (dadosContato.getEmail() != null && dadosContato.getEmail().length() > 0) {
                    cadEmail.setText(dadosContato.getEmail());
                }

                if (Long.toString(dadosContato.getCelular()).length() > 1) {
                    cadPhone.setText(Long.toString(dadosContato.getCelular()));
                }

                if (Long.toString(dadosContato.getWhatsapp()).length() > 1) {
                    cadWhatsapp.setText(Long.toString(dadosContato.getWhatsapp()));
                }

                if (Long.toString(dadosContato.getTelegram()).length() > 1) {
                    cadTelegram.setText(Long.toString(dadosContato.getTelegram()));
                }

                if (dadosContato.getFacebook() != null && dadosContato.getFacebook().length() > 0) {
                    cadFacebook.setText(dadosContato.getFacebook());
                }

                if (dadosContato.getTwitter() != null && dadosContato.getTwitter().length() > 0) {
                    cadTwitter.setText(dadosContato.getTwitter());
                }

                if (dadosContato.getInstagram() != null && dadosContato.getInstagram().length() > 0) {
                    cadInstagram.setText(dadosContato.getInstagram());
                }
            } else {
                ((MainActivity) getActivity()).setActionBarTitle("Cadastrar contato");
                Button cadButton = view.findViewById(R.id.cadBtnRegister);
                cadButton.setText("CADASTRAR");
            }

            view.findViewById(R.id.alterarFotoPerfilContato).setOnClickListener(v -> {
                selectImage();
            });

            FloatingActionButton fabBack = view.findViewById(R.id.fabBack);
            fabBack.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());

            Button cadButton = view.findViewById(R.id.cadBtnRegister);
            cadButton.setOnClickListener(v -> {
                if (id != null && id.length() > 0) {
                    gravar(view, true, Long.parseLong(id), ra);
                } else {
                    gravar(view, false, 0, ra);
                }
            });

        }
    }

    protected void gravar(View view, boolean saveOrUpdate, long id, long raUsuario){
        EditText name = view.findViewById(R.id.cadName);
        EditText apelido = view.findViewById(R.id.cadNickname);
        EditText phone = view.findViewById(R.id.cadPhone);
        EditText whatsapp = view.findViewById(R.id.cadWhatsapp);
        EditText telegram = view.findViewById(R.id.cadTelegram);
        EditText nascimento = view.findViewById(R.id.cadDtNasc);
        EditText facebook = view.findViewById(R.id.cadFacebook);
        EditText twitter = view.findViewById(R.id.cadTwitter);
        EditText instagram = view.findViewById(R.id.cadInstagram);
        EditText email = view.findViewById(R.id.cadEmail);
        ImageView fotoPerfilContato = view.findViewById(R.id.fotoPerfilContato);

        String nomeValue = name.getText().toString();
        String apelidoValue = apelido.getText().toString();
        String celularValue = phone.getText().toString();
        String whatsappValue = whatsapp.getText().toString();
        String telegramValue = telegram.getText().toString();
        String nascimentoValue = nascimento.getText().toString();
        String facebookValue = facebook.getText().toString();
        String twitterValue = twitter.getText().toString();
        String instagramValue = instagram.getText().toString();
        String emailValue = email.getText().toString();

        byte[] fotoPerfilContatoValue;

        System.out.println(fotoPerfilContato.getDrawable());

        if(fotoPerfilContato.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable) fotoPerfilContato.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            fotoPerfilContatoValue = baos.toByteArray();
        } else {
            fotoPerfilContatoValue = new byte[0];
        }


        boolean haveMinRequirements = validaMinRequirements(view);

        if(!haveMinRequirements) {
            Snackbar.make(view, "Preencha os dados obrigat??rios", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }

        ContatosDAO dao = new ContatosDAO(view.getContext());

        // Update
        if(saveOrUpdate) {
            boolean sucesso = dao.update(id, nomeValue, apelidoValue,
                    celularValue, whatsappValue, telegramValue, nascimentoValue,
                    facebookValue, twitterValue, instagramValue, emailValue, fotoPerfilContatoValue);

            if (sucesso) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                View viewActivity = getActivity().getCurrentFocus();
                if (viewActivity == null) {
                    viewActivity = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(viewActivity.getWindowToken(), 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("")
                        .setMessage("Informa????es alteradas")
                        .setPositiveButton("Confirmar", (dialog, which) -> {
                            Navigation.findNavController(view).popBackStack();
                        })
                        .create()
                        .show();
            } else {
                Snackbar.make(view, "Houve falha na edi????o dos dados do '" + nomeValue + "'.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        } else {
            //Save
            boolean sucesso = dao.gravar(nomeValue, Long.toString(raUsuario), apelidoValue,
                    celularValue, whatsappValue, telegramValue, nascimentoValue,
                    facebookValue, twitterValue, instagramValue, emailValue, img);

            if (sucesso) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                View viewActivity = getActivity().getCurrentFocus();
                if (viewActivity == null) {
                    viewActivity = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(viewActivity.getWindowToken(), 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("")
                        .setMessage("Contato adicionado")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            Navigation.findNavController(view).popBackStack();
                        })
                        .create()
                        .show();
            } else {
                Snackbar.make(view, "Houve falha na grava????o dos dados do '" + nomeValue + "'.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
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

    protected boolean validaMinRequirements(View view){
        EditText name = view.findViewById(R.id.cadName);
        EditText phone = view.findViewById(R.id.cadPhone);

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
        ImageView image = getView().findViewById(R.id.fotoPerfilContato);
        ImageView imageFake  = getView().findViewById(R.id.fotoPerfilContatoFake);
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