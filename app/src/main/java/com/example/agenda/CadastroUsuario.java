package com.example.agenda;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.agenda.ui.sqliteDb.usuarios.UsuariosDAO;

public class CadastroUsuario extends Fragment {

    EditText registerName, registerEmail, registerPhone, registerPassword, registerConfirmPassword, registerRa;
    Button registerBtnRegister;
    ImageButton registerBtnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro_usuario, container, false);
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((MainActivity)getActivity()).hideBottomTabNavigation();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((MainActivity)getActivity()).hideBottomTabNavigation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerName = view.findViewById(R.id.registerName);
        registerRa = view.findViewById(R.id.registerRa);
        registerEmail = view.findViewById(R.id.registerEmail);
        registerPhone = view.findViewById(R.id.registerPhone);
        registerPassword = view.findViewById(R.id.registerPassword);
        registerConfirmPassword = view.findViewById(R.id.registerConfirmPassword);
        registerBtnRegister = view.findViewById(R.id.registerBtnRegister);
        registerBtnBack = view.findViewById(R.id.backButtonCadastrar);

        registerBtnBack.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });


        registerBtnRegister.setOnClickListener(v -> {

            UsuariosDAO dao = new UsuariosDAO(view.getContext());

            if(registerName.getText().toString().length() < 3 || registerName.getText().toString().length() > 50){
                Toast.makeText(view.getContext(), "Nome inv??lido.\nMin: 3\nMax: 50", Toast.LENGTH_LONG).show();
                registerName.requestFocus();
                return;
            }

            if(registerEmail.getText().toString().length() < 10 || registerEmail.getText().toString().length() > 100){
                Toast.makeText(view.getContext(), "Email inv??lido.\nMin: 10\nMax: 100", Toast.LENGTH_LONG).show();
                registerEmail.requestFocus();
                return;
            }

            boolean emailExist = dao.verificaEmailRegistro(registerEmail.getText().toString());
            if(emailExist){
                Toast.makeText(view.getContext(), "Email j?? cadastrado", Toast.LENGTH_LONG).show();
                registerEmail.requestFocus();
                return;
            }

            if(registerRa.getText().toString().length() != 7){
                Toast.makeText(view.getContext(), "RA inv??lido.\nMin: 7 \nMax: 7", Toast.LENGTH_LONG).show();
                registerRa.requestFocus();
                return;
            }

            boolean raExist = dao.verificaRaRegistro(registerRa.getText().toString());
            if(raExist){
                Toast.makeText(view.getContext(), "RA j?? cadastrado", Toast.LENGTH_LONG).show();
                registerRa.requestFocus();
                return;
            }

            if(registerPhone.getText().toString().length() != 11){
                Toast.makeText(view.getContext(), "Telefone inv??lido. Ex: 34992209387", Toast.LENGTH_LONG).show();
                registerPhone.requestFocus();
                return;
            }

            if(registerPassword.getText().toString().length() < 7 || registerPassword.getText().toString().length() > 30){
                Toast.makeText(view.getContext(), "A senha deve conter pelo menos 8 d??gitos", Toast.LENGTH_LONG).show();
                registerPassword.requestFocus();
                return;
            }

            if(registerConfirmPassword.getText().toString().length() < 8 || registerConfirmPassword.getText().toString().length() > 50){
                Toast.makeText(view.getContext(), "A confirma????o de senha deve conter pelo menos 8 d??gitos", Toast.LENGTH_LONG).show();
                registerConfirmPassword.requestFocus();
                return;
            }

            if(!registerPassword.getText().toString().equals(registerConfirmPassword.getText().toString())){
                Toast.makeText(view.getContext(), "As senhas digitadas n??o s??o iguais", Toast.LENGTH_LONG).show();
                registerPassword.requestFocus();
                return;
            }

            boolean sucesso = dao.gravar(registerName.getText().toString(),
                    registerEmail.getText().toString(),
                    Long.parseLong(registerRa.getText().toString()),
                    registerPhone.getText().toString(),
                    registerPassword.getText().toString());

            if(sucesso){

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
                        .setMessage("Cadastro realizado")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            Navigation.findNavController(view).popBackStack();
                        })
                        .create()
                        .show();
            } else {
                Toast.makeText(view.getContext(), "Falha ao cadastrar, tente novamente mais tarde", Toast.LENGTH_LONG);
            }

        });

    }
}