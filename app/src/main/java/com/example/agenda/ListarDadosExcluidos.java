package com.example.agenda;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agenda.ui.adapter.AdapterContatosExcluidos;
import com.example.agenda.ui.sqliteDb.contatos.ContatosDAO;

import java.util.ArrayList;

public class ListarDadosExcluidos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar_dados_excluidos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).disableBackButton();

        long ra = ((MainActivity)getActivity()).ra;

        configuraRecycler(view, ra);

        view.findViewById(R.id.fabBackExcludedPeople).setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

    }

    RecyclerView recyclerView;
    AdapterContatosExcluidos adapter;
    public void configuraRecycler(View view, long ra){
        recyclerView = view.findViewById(R.id.excludedPeopleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ContatosDAO dao = new ContatosDAO(this.getContext());
        adapter = new AdapterContatosExcluidos(dao.buscarTodosOsDados(ra, 1));
        TextView batata = view.findViewById(R.id.noExcludedContacts);
        if(dao.buscarTodosOsDados(ra, 1).isEmpty()){
            batata.setVisibility(View.VISIBLE);
        } else {
            batata.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
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
}