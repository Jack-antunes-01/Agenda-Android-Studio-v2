package com.example.agenda;

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
import android.widget.Toast;

import com.example.agenda.ui.sqliteDb.usuarios.Usuarios;
import com.example.agenda.ui.sqliteDb.usuarios.UsuariosDAO;

import java.util.Arrays;

public class LoginFragment extends Fragment {

    private EditText eUsername, ePassword;
    private Button btnLogin, btnRegister;

    boolean isValid = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UsuariosDAO dao = new UsuariosDAO(view.getContext());

        eUsername = view.findViewById(R.id.eEmail);
        ePassword = view.findViewById(R.id.ePassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_cadastroUsuarioFragment);
        });

        btnLogin.setOnClickListener(v -> {
            String inputName = eUsername.getText().toString();
            String inputPassword = ePassword.getText().toString();

            if (inputName.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(view.getContext(), "Preencha os campos", Toast.LENGTH_SHORT).show();
            } else {

                Usuarios user = dao.logar(inputName, inputPassword);

                isValid = false;

                if (user != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    View viewActivity = getActivity().getCurrentFocus();
                    if (viewActivity == null) {
                        viewActivity = new View(getActivity());
                    }
                    imm.hideSoftInputFromWindow(viewActivity.getWindowToken(), 0);

                    ((MainActivity)getActivity()).setData(user.getId(), user.getRa());

                    LoginFragmentDirections.ActionLoginFragmentToNavigationHome action = LoginFragmentDirections.actionLoginFragmentToNavigationHome(
                            user.getRa(), user.getId()
                    );

                    Navigation.findNavController(view).navigate(action);

                } else {
                    Toast.makeText(view.getContext(), "Usuário e/ou senha inválidos", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
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
}