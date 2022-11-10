package com.mobilehouse.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilehouse.Activity.ActivityConfiguracoes;
import com.mobilehouse.R;

public class FragmentHome extends Fragment {

    View view;
    private CardView adicionar, configuracoes;

    void inicializarCardView(){
        this.adicionar = (CardView) view.findViewById(R.id.Adicionar);
        this.adicionar.setOnClickListener(view -> {
//            Intent i = new Intent(getActivity(), opcao1.class);
//            startActivity(i);
        });

        this.configuracoes = (CardView) view.findViewById(R.id.Configuracoes);
        this.configuracoes.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), ActivityConfiguracoes.class);
            startActivity(i);
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        this.inicializarCardView();

        return view;
    }
}