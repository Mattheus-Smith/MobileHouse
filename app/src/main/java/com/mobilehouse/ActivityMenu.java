package com.mobilehouse;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilehouse.Fragments.FragmentAgrupados;
import com.mobilehouse.Fragments.FragmentComodos;
import com.mobilehouse.Fragments.FragmentHome;
import com.mobilehouse.Fragments.FragmentVisaoSuperior;

public class ActivityMenu extends AppCompatActivity {

    //FIREBASE
    private FirebaseDatabase database;
    private DatabaseReference ref_databaseStatus;
    private ValueEventListener valueEventListenerStatus;
    private DatabaseReference ref_databaseIOT;
    private ValueEventListener valueEventListenerIOT;
    private DatabaseReference ref_databaseConfiguracoes;
    private ValueEventListener valueEventListenerConfiguracoes;

    //barra de status
    private FloatingActionButton floatingWifi;
    private FloatingActionButton floatingPower;

    //Barra de Navageção
    BottomNavigationView bottomNavigationView;//Barra de Navageção
    FragmentHome fragmentHome = new FragmentHome();
    FragmentVisaoSuperior fragmentVisaoSuperior = new FragmentVisaoSuperior();
    FragmentAgrupados fragmentAgrupados = new FragmentAgrupados();
    FragmentComodos fragmentComodos = new FragmentComodos();

    @RequiresApi(api = Build.VERSION_CODES.M)
    void mudarStatusWifi(int wifi){
        this.floatingWifi = findViewById(R.id.floatingWifi);
        if (wifi == 1){
            this.floatingWifi.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wifi_on));
            this.floatingWifi.setColorFilter(ContextCompat.getColor(this, R.color.on));
        }else{
            this.floatingWifi.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_wifi_off));
            this.floatingWifi.setColorFilter(ContextCompat.getColor(this, R.color.off));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void mudarStatusPower(int power){
        this.floatingPower = findViewById(R.id.floatingPower);
        if (power == 1){
            this.floatingPower.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_power_on));
            this.floatingPower.setColorFilter(ContextCompat.getColor(this, R.color.on));
        }else{
            this.floatingPower.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_power_off));
            this.floatingPower.setColorFilter(ContextCompat.getColor(this, R.color.off));
        }
    }

    void criandoOnClickBotoes(){
        //Gerando o Bottom Navigation
        this.bottomNavigationView = findViewById(R.id.BottomNavigation);
        replaceFragmnet(this.fragmentHome);
        //Gerando a acao do Bottom Navigation
        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentHome = new FragmentHome();
                        replaceFragmnet(fragmentHome);
                        return true;

                    case R.id.visaoSuperior:
                        fragmentVisaoSuperior = new FragmentVisaoSuperior();
                        replaceFragmnet(fragmentVisaoSuperior);
                        return true;

                    case R.id.comodos:
                        fragmentComodos = new FragmentComodos();
                        replaceFragmnet(fragmentComodos);
                        return true;

                    case R.id.agrupados:
                        fragmentAgrupados = new FragmentAgrupados();
                        replaceFragmnet(fragmentAgrupados);
                        return true;
                }
                return false;
            }
        });
    }

    private void replaceFragmnet(Fragment frag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutMenu,frag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.database = FirebaseDatabase.getInstance();
        this.ouvinteConfiguracoes();

        this.criandoOnClickBotoes();
    }

    private void ouvinteStatus(){
        this.ref_databaseStatus = this.database.getReference().child("status");
        this.valueEventListenerStatus = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int wifi = snapshot.child("wifi").getValue(int.class);
                int power = snapshot.child("power").getValue(int.class);

                mudarStatusWifi(wifi);
                mudarStatusPower(power);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        this.ref_databaseStatus.addValueEventListener(this.valueEventListenerStatus);
    }

    private void ouvinteConfiguracoes(){
        Log.i("ciclo","ouvinteConfiguracoes - Menu");
        this.ref_databaseConfiguracoes = this.database.getReference().child("configurações");
        this.valueEventListenerConfiguracoes = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tema = snapshot.child("tema").getValue(String.class);
                if( tema.equals("Claro" )){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }else if( tema.equals("Escuro" )){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        this.ref_databaseConfiguracoes.addValueEventListener(this.valueEventListenerConfiguracoes);
    }

    private void ouvinteBCK(){
        DatabaseReference Quarto = database.getReference().child("quarto");
        Quarto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int Luzes = snapshot.child("luzes").getValue(int.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.ouvinteStatus();
        Log.i("ciclo","Start - Menu1");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ciclo","Restart - Menu");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ciclo","Resume - Menu");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("ciclo","Pause - Menu");
    }
    @Override
    protected void onStop() {
        super.onStop();

        if(this.valueEventListenerStatus  != null){
            this.ref_databaseStatus.removeEventListener(this.valueEventListenerStatus);
        }

        Log.i("ciclo","Stop - Menu");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ciclo","Destroy - Menu1");
    }

}