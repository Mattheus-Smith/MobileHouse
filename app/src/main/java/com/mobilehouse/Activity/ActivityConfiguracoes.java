package com.mobilehouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilehouse.ActivityMenu;
import com.mobilehouse.MainActivity;
import com.mobilehouse.R;

public class ActivityConfiguracoes extends AppCompatActivity {

    //FIREBASE public static final
    private FirebaseDatabase database;
    private DatabaseReference reference_database;
    private ValueEventListener valueEventListener;

    public Spinner spinnerTema;
    public Button Menu;

    void CriandoSpinner(){
        Log.i("tema", "criando spinner");
        this.spinnerTema = findViewById(R.id.spinner);
        this.spinnerTema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //int currentMode =  AppCompatDelegate.getDefaultNightMode();
                //currentMode == AppCompatDelegate.MODE_NIGHT_YES
                String tema = (String) spinnerTema.getSelectedItem();
                if( tema.equals("Escuro") ){
                    spinnerTema.setSelection(1);
                    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //salva no firebase como Escuro
                    reference_database = database.getReference().child("configurações");
                    //DatabaseReference configurações = database.getReference().child("configurações");
                    reference_database.child("tema").setValue(tema);
                }else{
                    spinnerTema.setSelection(0);
                    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //salva no firebase como Claro
                    reference_database = database.getReference().child("configurações");
                    reference_database.child("tema").setValue(tema);
                }
//                Object item = parent.getItemAtPosition(pos);  -> nao sei oq isso retorna

//                String tema = (String) spinnerTema.getSelectedItem();
//                DatabaseReference configurações = database.getReference().child("configurações");
//                configurações.child("tema").setValue(tema);
//                mudarTema(tema);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    void criandoOnClickBotoes(){
        //Gerando o botao menu
        this.Menu = (Button) findViewById(R.id.button2);
        //Gerando a acao do botao menu
        this.Menu.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityConfiguracoes.this, ActivityMenu.class);
            startActivity(intent);
        });
    }

    private void ouvinteConfiguracoes(){
        Log.i("tema", "ouvinte firebare");
        this.spinnerTema = findViewById(R.id.spinner);
        this.reference_database = this.database.getReference().child("configurações");
        this.valueEventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tema = snapshot.child("tema").getValue(String.class);
                if( tema.equals("Claro" )){
                    Log.i("tema", "         tema claro");
                    spinnerTema.setSelection(0);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }else if( tema.equals("Escuro" )){
                    Log.i("tema", "         tema escuro");
                    spinnerTema.setSelection(1);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        this.reference_database.addValueEventListener(this.valueEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        this.database = FirebaseDatabase.getInstance();

        this.ouvinteConfiguracoes();
        this.criandoOnClickBotoes();
        this.CriandoSpinner();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ciclo","Start - Configurações");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ciclo","Restart - Configurações");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ciclo","Resume - Configurações");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("ciclo","Pause - Configurações");
    }
    @Override
    protected void onStop() {
        super.onStop();

        Log.i("ciclo","Stop - Configurações");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ciclo","Destroy - Configurações");
    }

}