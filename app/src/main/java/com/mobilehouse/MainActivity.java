package com.mobilehouse;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;
import com.mobilehouse.Fragments.FragmentAgrupados;
import com.mobilehouse.Fragments.FragmentComodos;
import com.mobilehouse.Fragments.FragmentHome;
import com.mobilehouse.Fragments.FragmentVisaoSuperior;

public class MainActivity extends AppCompatActivity {

    public Button menu;

    void criandoOnClickBotoes(){
        //Gerando o botao Gerenciar Receita
        this.menu = (Button) findViewById(R.id.menu);
        //Gerando a acao do botao Gerenciar Receita
        this.menu.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActivityMenu.class);
            startActivity(intent);
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.criandoOnClickBotoes();
    }
}