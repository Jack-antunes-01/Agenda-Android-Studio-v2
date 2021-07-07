package com.example.agenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.ui.adapter.AdapterContatos;
import com.example.agenda.ui.sqliteDb.contatos.ContatosDAO;

public class Home extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments() != null){

            long ra = ((MainActivity) getActivity()).ra;

            configuraRecycler(view, ra);

            view.findViewById(R.id.fab).setOnClickListener(v -> {
                HomeDirections.ActionNavigationHomeToCadastroContato action = HomeDirections.actionNavigationHomeToCadastroContato(
                        "", Long.toString(ra)
                );
                Navigation.findNavController(view).navigate(action);
            });

        }

        view.findViewById(R.id.fab).setOnClickListener(v -> {

            HomeDirections.ActionNavigationHomeToCadastroContato action = HomeDirections.actionNavigationHomeToCadastroContato(
                    "", ""
            );
            Navigation.findNavController(view).navigate(action);
        });

    }


    RecyclerView recyclerView;
    AdapterContatos adapter;
    public void configuraRecycler(View view, long ra){
        recyclerView = view.findViewById(R.id.users_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ContatosDAO dao = new ContatosDAO(this.getContext());
        adapter = new AdapterContatos(dao.buscarTodosOsDados(ra, 0));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Navigation.findNavController(getView()).navigate(HomeDirections.actionNavigationHomeToLoginFragment());
                break;

            case R.id.settingsListDeletedData:
                Navigation.findNavController(getView()).navigate(HomeDirections.actionNavigationHomeToListarDadosExcluidos());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}